package com.example.ai_chatbot.service;

import com.example.ai_chatbot.context.McpContextManager;
import com.example.ai_chatbot.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class McpChatBotService {
    @Autowired
    private McpContextManager contextManager;
    @Autowired
    private OpenAIClientService openAiClientService;

    /**
     * MCP多轮对话
     */
    public String chat(String sessionId, String question) throws Exception {
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

        return answer;
    }

    /**
     * 清空当前会话上下文
     */
    public void resetSession(String sessionId) {
        contextManager.clearSession(sessionId);
    }
}