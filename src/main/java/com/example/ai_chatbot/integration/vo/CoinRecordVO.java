package com.example.ai_chatbot.integration.vo;

import java.util.Date;

public class CoinRecordVO {
    private int id;
    private String code;
    private String outBizNo;
    private Date PrizeTime;
    private int amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOutBizNo() {
        return outBizNo;
    }

    public void setOutBizNo(String outBizNo) {
        this.outBizNo = outBizNo;
    }

    public Date getPrizeTime() {
        return PrizeTime;
    }

    public void setPrizeTime(Date prizeTime) {
        PrizeTime = prizeTime;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}