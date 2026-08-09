package com.example.ai_chatbot.integration.vo;

import com.example.ai_chatbot.controller.vo.BaseVO;

import java.util.List;

public class MultiPrizeRecordVO {
    private BaseVO baseVO;
    private List<PrizeRecordVO> prizeRecordVOList;

    public BaseVO getBaseVO() {
        return baseVO;
    }

    public void setBaseVO(BaseVO baseVO) {
        this.baseVO = baseVO;
    }

    public List<PrizeRecordVO> getPrizeRecordVOList() {
        return prizeRecordVOList;
    }

    public void setPrizeRecordVOList(List<PrizeRecordVO> prizeRecordVOList) {
        this.prizeRecordVOList = prizeRecordVOList;
    }
}

