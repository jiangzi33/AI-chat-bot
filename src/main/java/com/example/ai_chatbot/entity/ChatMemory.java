package com.example.ai_chatbot.entity;

import java.util.Date;

public class ChatMemory {
    private int id;
    private String sessionId;
    private int fromUserId;
    private int toUserId;
    private String content;
    private Date chatTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getChatTime() {
        return chatTime;
    }

    public void setChatTime(Date chatTime) {
        this.chatTime = chatTime;
    }

    public ChatMemory() {
    }

    public ChatMemory(int id, String sessionId, int fromUserId, int toUserId, String content, Date chatTime) {
        this.id = id;
        this.sessionId = sessionId;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.content = content;
        this.chatTime = chatTime;
    }
}
