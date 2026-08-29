package com.example.ai_chatbot.service;

import com.example.ai_chatbot.controller.cmd.ChatMemoryCmd;
import com.example.ai_chatbot.entity.ChatMemory;
import com.example.ai_chatbot.mapper.ChatMemoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMemoryService {
    @Autowired
    private ChatMemoryMapper chatMemoryMapper;

    public void addMemory(ChatMemoryCmd cmd){
        ChatMemory chatMemory = new ChatMemory();
        chatMemory.setContent(cmd.getContent());
        chatMemory.setFromUserId(cmd.getFromUserId());
        chatMemory.setSessionId(cmd.getSessionId());
        chatMemory.setToUserId(cmd.getToUserId());

        chatMemoryMapper.addMemory(chatMemory);

    }
    public void deleteMemory(int id){
        chatMemoryMapper.deleteMemory(id);
    }
    public List<ChatMemory> queryBySessionId(String sessionId){
       return chatMemoryMapper.queryBySessionId(sessionId);
    }
}
