package com.example.ai_chatbot.controller;

import com.example.ai_chatbot.controller.cmd.ChatMemoryCmd;
import com.example.ai_chatbot.controller.converter.ChatMemoryConverter;
import com.example.ai_chatbot.controller.vo.BaseVO;
import com.example.ai_chatbot.controller.vo.ChatMemoryVO;
import com.example.ai_chatbot.controller.vo.MultiChatMemoryVO;
import com.example.ai_chatbot.entity.ChatMemory;
import com.example.ai_chatbot.service.ChatMemoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/chat-memory")
@Slf4j
public class ChatMemoryController {
    @Autowired
    private ChatMemoryService chatMemoryService;

    @PostMapping("/add")
    public BaseVO addMemory(ChatMemoryCmd cmd){
        long startTime = System.currentTimeMillis();
        long endTime;
        try{
            chatMemoryService.addMemory(cmd);
            endTime = System.currentTimeMillis();
            return BaseVO.buildBaseVO(200, true, endTime - startTime, null);
        } catch (Exception e) {
            endTime = System.currentTimeMillis();
            log.error(e.getMessage());
            return BaseVO.buildBaseVO(500, false, endTime - startTime, "其他异常");
        }
    }

    @DeleteMapping("/delete")
    public BaseVO deleteMemory(int id){
        long startTime = System.currentTimeMillis();
        long endTime;
        try{
            chatMemoryService.deleteMemory(id);
            endTime = System.currentTimeMillis();
            return BaseVO.buildBaseVO(200, true, endTime - startTime, null);
        } catch (Exception e) {
            endTime = System.currentTimeMillis();
            log.error(e.getMessage());
            return BaseVO.buildBaseVO(500, false, endTime - startTime, "其他异常");
        }
    }

    @GetMapping("/query-session-id")
    public MultiChatMemoryVO queryBySessionId(String sessionId){
        long startTime = System.currentTimeMillis();
        long endTime;
        MultiChatMemoryVO multiChatMemoryVO = new MultiChatMemoryVO();
        try {
            List<ChatMemory> chatMemories = chatMemoryService.queryBySessionId(sessionId);
            List<ChatMemoryVO> chatMemoryVOList = ChatMemoryConverter.convertList(chatMemories);
            endTime = System.currentTimeMillis();
            BaseVO baseVO = BaseVO.buildBaseVO(200, true, endTime - startTime, null);
            multiChatMemoryVO.setMultiChatMemoryVO(multiChatMemoryVO);
            multiChatMemoryVO.setBaseVO(baseVO);
            return multiChatMemoryVO;
        } catch (Exception e) {
            endTime = System.currentTimeMillis();
            log.error(e.getMessage());
            BaseVO baseVO = BaseVO.buildBaseVO(500, false, endTime - startTime, "其他未知异常");
            multiChatMemoryVO.setBaseVO(baseVO);
            return multiChatMemoryVO;
        }
    }
}
