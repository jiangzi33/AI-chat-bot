package com.example.ai_chatbot.integration.vo;

import com.example.ai_chatbot.controller.vo.BaseVO;

import java.util.List;

public class MultiPlayRecordVO {
    private BaseVO baseVO;
    private List<PlayRecordVO> playRecordVOList;

    public BaseVO getBaseVO() {
        return baseVO;
    }

    public void setBaseVO(BaseVO baseVO) {
        this.baseVO = baseVO;
    }

    public List<PlayRecordVO> getPlayRecordVOList() {
        return playRecordVOList;
    }

    public void setPlayRecordVOList(List<PlayRecordVO> playRecordVOList) {
        this.playRecordVOList = playRecordVOList;
    }
}

