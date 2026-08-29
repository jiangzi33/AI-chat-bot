package com.example.ai_chatbot.controller.converter;

import com.example.ai_chatbot.controller.vo.ChatMemoryVO;
import com.example.ai_chatbot.entity.ChatMemory;

import java.util.List;

public class ChatMemoryConverter {

    public static ChatMemoryVO convert(ChatMemory chatMemory) {
        if (chatMemory == null) {
            return null;
        }

        ChatMemoryVO chatMemoryVO = new ChatMemoryVO();

        chatMemoryVO.setId(chatMemory.getId());
        chatMemoryVO.setSessionId(chatMemory.getSessionId());
        chatMemoryVO.setFromUserId(chatMemory.getFromUserId());
        chatMemoryVO.setToUserId(chatMemory.getToUserId());
        chatMemoryVO.setContent(chatMemory.getContent());
        chatMemoryVO.setChatTime(chatMemory.getChatTime());

        return chatMemoryVO;
    }

    public static List<ChatMemoryVO> convertList(List<ChatMemory> chatMemoryList) {
        if (chatMemoryList == null) {
            return null;
        }

        return chatMemoryList.stream()
                .map(ChatMemoryConverter::convert)
                .toList();
    }
}
