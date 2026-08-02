package com.example.ai_chatbot.entity;

import lombok.Builder;

@Builder
public class Choice {
    private Integer index;

    private Message message;

    private Object logprobs;

    private String finish_reason;

    public Choice() {
    }

    public Choice(Integer index, Message message, Object logprobs, String finish_reason) {
        this.index = index;
        this.message = message;
        this.logprobs = logprobs;
        this.finish_reason = finish_reason;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Object getLogprobs() {
        return logprobs;
    }

    public void setLogprobs(Object logprobs) {
        this.logprobs = logprobs;
    }

    public String getFinish_reason() {
        return finish_reason;
    }

    public void setFinish_reason(String finish_reason) {
        this.finish_reason = finish_reason;
    }
}
