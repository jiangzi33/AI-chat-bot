package com.example.ai_chatbot.entity;

import java.util.List;

public class Message {
    private String role;

    private String content;

    private Object refusal;

    private List<Object> annotations;

    public Message() {
    }

    public Message(String role, String content, Object refusal, List<Object> annotations) {
        this.role = role;
        this.content = content;
        this.refusal = refusal;
        this.annotations = annotations;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object getRefusal() {
        return refusal;
    }

    public void setRefusal(Object refusal) {
        this.refusal = refusal;
    }

    public List<Object> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<Object> annotations) {
        this.annotations = annotations;
    }
}
