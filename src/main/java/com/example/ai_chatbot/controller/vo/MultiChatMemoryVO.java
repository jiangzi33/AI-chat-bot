package com.example.ai_chatbot.controller.vo;

import java.util.List;

public class MultiChatMemoryVO {
    private BaseVO baseVO;
    private List<ChatMemoryVO> chatMemoryVOList;

    public BaseVO getBaseVO() {
        return baseVO;
    }

    public void setBaseVO(BaseVO baseVO) {
        this.baseVO = baseVO;
    }

    public List<ChatMemoryVO> getChatMemoryVOList() {
        return chatMemoryVOList;
    }

    public void setChatMemoryVOList(List<ChatMemoryVO> chatMemoryVOList) {
        this.chatMemoryVOList = chatMemoryVOList;
    }
}
