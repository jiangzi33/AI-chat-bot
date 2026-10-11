package com.example.ai_chatbot.feign;

import com.example.ai_chatbot.controller.vo.BaseVO;
import com.example.ai_chatbot.integration.cmd.MusicCmd;
import com.example.ai_chatbot.integration.vo.SingleSystemConfigVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "music")
public interface MusicServiceFeignClient {

    @GetMapping("/systemConfig/query")
    SingleSystemConfigVO getSystemConfig(@RequestParam("code") String code);

    @PostMapping("/music/add")
    BaseVO addMusic(@RequestBody MusicCmd cmd);
}