package com.example.ai_chatbot.integration.vo;

import com.example.ai_chatbot.controller.vo.BaseVO;

public class SingleSystemConfigVO {
    private BaseVO baseVO;
    private SystemConfigVO systemConfigVO;

    public BaseVO getBaseVO() {
        return baseVO;
    }

    public void setBaseVO(BaseVO baseVO) {
        this.baseVO = baseVO;
    }

    public SystemConfigVO getSystemConfigVO() {
        return systemConfigVO;
    }

    public void setSystemConfigVO(SystemConfigVO systemConfigVO) {
        this.systemConfigVO = systemConfigVO;
    }
}
