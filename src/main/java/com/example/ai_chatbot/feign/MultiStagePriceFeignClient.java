package com.example.ai_chatbot.feign;

import com.example.ai_chatbot.integration.vo.MultiCoinRecordVO;
import com.example.ai_chatbot.integration.vo.MultiPlayRecordVO;
import com.example.ai_chatbot.integration.vo.MultiPrizeRecordVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "multi-stage-price")
public interface MultiStagePriceFeignClient {

    @GetMapping("/play-record/query")
    MultiPlayRecordVO queryPlayRecordList(@RequestParam("userId") int userId,
                                          @RequestParam("startTime") String startTime,
                                          @RequestParam("endTime") String endTime,
                                          @RequestParam("pageStart") int pageStart,
                                          @RequestParam("pageSize") int pageSize);

    @GetMapping("/prize-record/query")
    MultiPrizeRecordVO queryPrizeRecordList(@RequestParam("userId") int userId,
                                             @RequestParam("startTime") String startTime,
                                             @RequestParam("endTime") String endTime);

    @GetMapping("/prize-record/query")
    MultiCoinRecordVO queryCoinRecordList(@RequestParam("start") int start,
                                          @RequestParam("pageSize") int pageSize,
                                          @RequestParam("startTime") String startTime,
                                          @RequestParam("endTime") String endTime);
}