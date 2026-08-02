package com.example.ai_chatbot.entity;

import lombok.Builder;

import java.util.List;

@Builder
public class OpenAIChatRequest {
    private String model;
    private List<Message> messages;
    private Double temperature;

    public OpenAIChatRequest() {
    }

    public OpenAIChatRequest(String model, List<Message> messages, Double temperature) {
        this.model = model;
        this.messages = messages;
        this.temperature = temperature;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
}
