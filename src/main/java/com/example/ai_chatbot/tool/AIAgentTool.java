package com.example.ai_chatbot.tool;

import com.example.ai_chatbot.integration.AIChatBotIntegration;
import com.example.ai_chatbot.integration.vo.PlayRecordVO;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AIAgentTool {
    @Autowired
    private AIChatBotIntegration aiChatBotIntegration;

    @Tool(name = "当需要查询用户指定时间范围内的播放记录时调用返回用户的所有播放记录")
    public List<PlayRecordVO> queryPlayRecord(@ToolParam(description = "用户ID") int userId,
                                              @ToolParam(description = "播放范围开始")String startTime,
                                              @ToolParam(description = "播放范围结束")String endTime,
                                              @ToolParam(description = "开始页面")int pageStart,
                                              @ToolParam(description = "页面大小")int pageSize){
       return aiChatBotIntegration.queryPlayRecordList(userId, startTime, endTime, pageStart, pageSize);
    }
}
