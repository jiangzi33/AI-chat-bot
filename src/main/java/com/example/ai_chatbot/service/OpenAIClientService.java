package com.example.ai_chatbot.service;

import com.example.ai_chatbot.entity.Message;
import com.example.ai_chatbot.entity.OpenAIChatRequest;
import com.example.ai_chatbot.entity.OpenAIChatResponse;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class OpenAIClientService {

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @Value("${spring.ai.openai.api-url}")
    private String apiUrl;

    @Value("${spring.ai.openai.model}")
    private String model;

    @Value("${spring.ai.openai.temperature}")
    private Double temperature;

    @Autowired
    private ObjectMapper objectMapper;

    private OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    public String chatCompletion(List<Message> messages) throws Exception {
        OpenAIChatRequest request = OpenAIChatRequest.builder()
                .model(model)
                .messages(messages)
                .temperature(temperature)
                .build();

        String jsonBody = objectMapper.writeValueAsString(request);
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(jsonBody, mediaType);

        Request httpRequest = new Request.Builder()
                .url(apiUrl)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .post(body)
                .build();

        try (okhttp3.Response response = okHttpClient().newCall(httpRequest).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new RuntimeException("OpenAI接口调用失败，code=" + response.code());
            }
            String respJson = response.body().string();
            OpenAIChatResponse aiResp = objectMapper.readValue(respJson, OpenAIChatResponse.class);
            return aiResp.getChoices().get(0).getMessage().getContent();
        }
    }
}
