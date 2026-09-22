package com.example.ai_chatbot.controller;

import com.example.ai_chatbot.agent.MusicAgent;
import com.example.ai_chatbot.controller.vo.Result;
import com.example.ai_chatbot.service.McpChatBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mcp/chat")
public class McpChatController {
    @Autowired
    private McpChatBotService chatBotService;
    @Autowired
    private MusicAgent musicAgent;

    /**
     * MCP问答接口
     */
    @PostMapping("/ask")
    public Result<String> ask(String sessionId, String question, int userId) {
        try {
            String reply = musicAgent.chat(sessionId, question, userId);
            return Result.success(reply);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("问答异常：" + e.getMessage());
        }
    }

    /**
     * 重置对话上下文，开启新一轮对话
     */
    @PostMapping("/reset")
    public Result<String> reset(String sessionId) {
        chatBotService.resetSession(sessionId);
        return Result.success("会话已重置");
    }
}