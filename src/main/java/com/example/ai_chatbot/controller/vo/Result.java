package com.example.ai_chatbot.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <T> Result<T> success(T data) {
        return Result.<T>builder().code(200).msg("ok").data(data).build();
    }

    public static <T> Result<T> fail(String msg) {
        return Result.<T>builder().code(500).msg(msg).build();
    }
}