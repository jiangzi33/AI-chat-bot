package com.example.ai_chatbot.integration;

import com.example.ai_chatbot.exception.AcrossSysException;
import com.example.ai_chatbot.integration.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class AIChatBotIntegration {
    @Autowired
    private RestTemplate restTemplate;
    public List<PlayRecordVO> queryPlayRecordList(int userId, String startTime, String endTime, int pageStart, int pageSize){
        String url = String.format("http://127.0.0.1:8083/play-record/query?userId=%d&startTime=%s&endTime=%spageStart=%d&pageSize=%d",userId,startTime,endTime,pageStart,pageSize);
        HttpHeaders httpHeaders = new HttpHeaders();
        ResponseEntity<MultiPlayRecordVO> multiPlayRecordVOResponseEntity = restTemplate.getForEntity(url, MultiPlayRecordVO.class);
        if(!multiPlayRecordVOResponseEntity.getBody().getBaseVO().isSuccess() || multiPlayRecordVOResponseEntity.getBody().getBaseVO().getCode()!=200){
            throw new AcrossSysException("acrossing system is fail");
        }
        return multiPlayRecordVOResponseEntity.getBody().getPlayRecordVOList();
    }

    public List<PrizeRecordVO> queryPrizeRecordList(int userId, String startTime, String endTime){
        String url = String.format("http://127.0.0.1:8083/prize-record/query?userId=%d&startTime=%s&endTime=%s",userId,startTime,endTime);
        HttpHeaders httpHeaders = new HttpHeaders();
        ResponseEntity<MultiPrizeRecordVO> multiPrizeRecordVOResponseEntity = restTemplate.getForEntity(url, MultiPrizeRecordVO.class);
        if(!multiPrizeRecordVOResponseEntity.getBody().getBaseVO().isSuccess() || multiPrizeRecordVOResponseEntity.getBody().getBaseVO().getCode()!=200){
            throw new AcrossSysException("acrossing system is fail");
        }
        return multiPrizeRecordVOResponseEntity.getBody().getPrizeRecordVOList();
    }

    public List<CoinRecordVO> queryCoinRecordList(int start, int pageSize, String startTime, String endTime){
        String url = String.format("http://127.0.0.1:8083/prize-record/query?start=%d&pageSize=%d&startTime=%s&endTime=%s",start,pageSize,startTime,endTime);
        HttpHeaders httpHeaders = new HttpHeaders();
        ResponseEntity<MultiCoinRecordVO> multiCoinRecordVOResponseEntity = restTemplate.getForEntity(url, MultiCoinRecordVO.class);
        if(!multiCoinRecordVOResponseEntity.getBody().getBaseVO().isSuccess() || multiCoinRecordVOResponseEntity.getBody().getBaseVO().getCode()!=200){
            throw new AcrossSysException("acrossing system is fail");
        }
        return multiCoinRecordVOResponseEntity.getBody().getCoinRecordVOList();
    }


}

