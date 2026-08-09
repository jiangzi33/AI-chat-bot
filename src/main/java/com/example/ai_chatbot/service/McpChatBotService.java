package com.example.ai_chatbot.service;

import com.example.ai_chatbot.context.McpContextManager;
import com.example.ai_chatbot.entity.BizCondition;
import com.example.ai_chatbot.entity.Message;
import com.example.ai_chatbot.integration.AIChatBotIntegration;
import com.example.ai_chatbot.integration.vo.PlayRecordVO;
import com.example.ai_chatbot.integration.vo.PrizeRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class McpChatBotService {
    @Autowired
    private McpContextManager contextManager;
    @Autowired
    private OpenAIClientService openAiClientService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AIChatBotIntegration aiChatBotIntegration;
    @Autowired
    private RecognizeConverterService recognizeConverterService;

    /**
     * MCP多轮对话
     */
    public String chat(String sessionId, String question,int userId) throws Exception {
        // 1. 组装用户消息存入上下文
        Message userMsg = Message.builder()
                .role("user")
                .content(question)
                .build();
        contextManager.appendMessage(sessionId, userMsg);

        // 2. 获取完整上下文调用OpenAI
        var context = contextManager.getContext(sessionId);
        String answer = openAiClientService.chatCompletion(context);


        // 3. 将AI回答写入上下文（MCP：保存assistant回复，支持下一轮理解历史）
        Message assistantMsg = Message.builder()
                .role("assistant")
                .content(answer)
                .build();
        contextManager.appendMessage(sessionId, assistantMsg);
        BizCondition bizCondition = objectMapper.readValue(answer, BizCondition.class);
        bizCondition.setTargetUserId(userId);
        if(bizCondition.getIntentCode().equals("PLAY_RECORD")){
            List<PlayRecordVO> playRecordVOS = aiChatBotIntegration.queryPlayRecordList(bizCondition.getTargetUserId(), bizCondition.getStartTime(), bizCondition.getEndTime(), bizCondition.getStartIndex(), bizCondition.getPageSize());
            String jsonResult = objectMapper.writeValueAsString(playRecordVOS);
            return recognizeConverterService.converter(question,jsonResult);
        } else if(bizCondition.getIntentCode().equals("AWARD_RECORD")){
            List<PrizeRecordVO> prizeRecordVOS = aiChatBotIntegration.queryPrizeRecordList(bizCondition.getTargetUserId(), bizCondition.getStartTime(), bizCondition.getEndTime());
            String jsonResult = objectMapper.writeValueAsString(prizeRecordVOS);
            return recognizeConverterService.converter(question,jsonResult);
        } else {
            return answer;
        }
    }

    /**
     * 清空当前会话上下文
     */
    public void resetSession(String sessionId) {
        contextManager.clearSession(sessionId);
    }
}