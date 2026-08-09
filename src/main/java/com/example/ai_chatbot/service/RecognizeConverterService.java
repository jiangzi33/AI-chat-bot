package com.example.ai_chatbot.service;

import com.example.ai_chatbot.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class RecognizeConverterService {
    @Autowired
    private OpenAIClientService openAIClientService;

    public String converter(String userQuestion,String jsonResult) {
        String systemPrompt = """
                你是智能数据助手，严格遵守以下规则：
                1. 不要输出原始JSON、不要markdown代码块；
                2. 根据【用户提问】和【查询数据】，整理成通俗易懂的中文回答；
                3. 如果查询结果为空、没有记录，直接礼貌告知：未查询到对应记录；
                4. 每一条记录一行以表格的形式展示，可以做一些汇总性的描述；
                5. 回答简洁干净，不要多余开场白。
                用户原始提问：%s
                后端查询得到的数据：%s
                """.formatted(userQuestion, jsonResult);
        List<Message> messages = new ArrayList<>();
        Message message = new Message();
        message.setRole("system");
        message.setContent(systemPrompt);
        messages.add(message);
        try {
            return openAIClientService.chatCompletion(messages);
        } catch (Exception e) {
            log.error("json转化失败");
            return "json转化失败";
        }
    }
}
