package com.example.ai_chatbot.context;

import com.example.ai_chatbot.entity.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class McpContextManager {

    @Value("${spring.ai.openai.max-message-size}")
    private Integer maxMessageSize;

    // 内存存储：sessionId -> 对话消息列表
    private final Map<String, List<Message>> sessionContextMap = new ConcurrentHashMap<>();

    /**
     * 获取会话上下文，不存在则初始化（可以设置system角色提示词）
     */
    public List<Message> getContext(String sessionId) {
        return sessionContextMap.computeIfAbsent(sessionId, k -> {
            List<Message> list = new ArrayList<>();
            // System全局角色设定，可以自定义机器人人设
            list.add(Message.builder()
                    .role("system")
                    .content("你是一个专业问答机器人，回答简洁清晰，逻辑严谨。")
                    .build());
            return list;
        });
    }

    /**
     * 追加消息并裁剪上下文（防止token溢出，MCP标准策略）
     */
    public void appendMessage(String sessionId, Message message) {
        List<Message> context = getContext(sessionId);
        context.add(message);
        trimContext(context);
    }

    /**
     * 上下文裁剪策略：保留system消息，截断最早的user/assistant消息
     */
    private void trimContext(List<Message> context) {
        while (context.size() > maxMessageSize) {
            // 跳过第一条system指令，删除第二条（最早一轮对话）
            if (context.size() >= 2) {
                context.remove(1);
            } else {
                break;
            }
        }
    }

    /**
     * 清空会话上下文，重置对话
     */
    public void clearSession(String sessionId) {
        sessionContextMap.remove(sessionId);
    }
}
