package com.example.ai_chatbot.mapper;

import com.example.ai_chatbot.entity.ChatMemory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMemoryMapper {
    void addMemory(ChatMemory chatMemory);
    void deleteMemory(int id);
    List<ChatMemory> queryBySessionId(String sessionId);
}
