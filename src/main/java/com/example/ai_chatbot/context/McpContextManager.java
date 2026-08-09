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
        String prompt = """
                你是业务查询参数抽取器，只输出JSON，禁止多余文字、禁止markdown。
                
                可选意图：
                
                PLAY_RECORD：查询播放记录
                
                AWARD_RECORD：查询发奖记录
                
                CHAT：普通闲聊，不需要查询业务
                
                
                
                抽取规则：
                
                1. targetUserId: null
                
                2. startTime、endTime：用户提到时间段就提取，格式yyyy-MM-dd HH:mm:ss；没有提供默认取最近30天 ,用户如果指定当天则取当天的0点到当天的23点59分59秒
                
                3. startIndex、pageSize 用户没说填空，startIndex取0，pageSize取10000
                4.playDate默认传当天 字符串类型 yyyy-MM-dd格式\s
                5. 如果是闲聊，intentCode固定为CHAT，其他字段留空
                
                
                
                输出固定JSON结构示例：
                
                {
                
                "intentCode":"AWARD_RECORD",
                
                "targetUserId":10001,
                
                "startTime":"2026-08-09 00：00：00",
                
                "endTime":"2026-08-09 12：00：00",
                
                "startIndex":1,
                
                "pageSize":10，
                "playDate":"2026-08-09"
                }
                """;
        return sessionContextMap.computeIfAbsent(sessionId, k -> {
            List<Message> list = new ArrayList<>();
            // System全局角色设定，可以自定义机器人人设
            list.add(Message.builder()
                    .role("system")
                    .content(prompt)
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
