package com.example.ai_chatbot.integration.vo;

import com.example.ai_chatbot.controller.vo.BaseVO;

import java.util.List;

public class MultiCoinRecordVO {
    private BaseVO baseVO;
    List<CoinRecordVO> coinRecordVOList;

    public BaseVO getBaseVO() {
        return baseVO;
    }

    public void setBaseVO(BaseVO baseVO) {
        this.baseVO = baseVO;
    }

    public List<CoinRecordVO> getCoinRecordVOList() {
        return coinRecordVOList;
    }

    public void setCoinRecordVOList(List<CoinRecordVO> coinRecordVOList) {
        this.coinRecordVOList = coinRecordVOList;
    }
}