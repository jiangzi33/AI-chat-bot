package com.example.ai_chatbot.integration;

import com.example.ai_chatbot.controller.vo.BaseVO;
import com.example.ai_chatbot.exception.AcrossSysException;
import com.example.ai_chatbot.feign.MultiStagePriceFeignClient;
import com.example.ai_chatbot.feign.MusicServiceFeignClient;
import com.example.ai_chatbot.integration.cmd.MusicCmd;
import com.example.ai_chatbot.integration.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AIChatBotIntegration {

    @Autowired
    private MultiStagePriceFeignClient multiStagePriceFeignClient;

    @Autowired
    private MusicServiceFeignClient musicServiceFeignClient;

    public List<PlayRecordVO> queryPlayRecordList(int userId, String startTime, String endTime, int pageStart, int pageSize) {
        MultiPlayRecordVO result = multiStagePriceFeignClient.queryPlayRecordList(userId, startTime, endTime, pageStart, pageSize);
        if (!result.getBaseVO().isSuccess() || result.getBaseVO().getCode() != 200) {
            throw new AcrossSysException("acrossing system is fail");
        }
        return result.getPlayRecordVOList();
    }

    public List<PrizeRecordVO> queryPrizeRecordList(int userId, String startTime, String endTime) {
        MultiPrizeRecordVO result = multiStagePriceFeignClient.queryPrizeRecordList(userId, startTime, endTime);
        if (!result.getBaseVO().isSuccess() || result.getBaseVO().getCode() != 200) {
            throw new AcrossSysException("acrossing system is fail");
        }
        return result.getPrizeRecordVOList();
    }

    public List<CoinRecordVO> queryCoinRecordList(int start, int pageSize, String startTime, String endTime) {
        MultiCoinRecordVO result = multiStagePriceFeignClient.queryCoinRecordList(start, pageSize, startTime, endTime);
        if (!result.getBaseVO().isSuccess() || result.getBaseVO().getCode() != 200) {
            throw new AcrossSysException("acrossing system is fail");
        }
        return result.getCoinRecordVOList();
    }

    public SystemConfigVO querySystemConfigByCode(String code) {
        SingleSystemConfigVO result = musicServiceFeignClient.getSystemConfig(code);
        if (!result.getBaseVO().isSuccess() || result.getBaseVO().getCode() != 200) {
            throw new AcrossSysException("acrossing system is fail");
        }
        return result.getSystemConfigVO();
    }

    public void addMusic(MusicCmd cmd) {
        BaseVO result = musicServiceFeignClient.addMusic(cmd);
        if (!result.isSuccess() || result.getCode() != 200) {
            throw new AcrossSysException("acrossing system is fail");
        }
    }
}