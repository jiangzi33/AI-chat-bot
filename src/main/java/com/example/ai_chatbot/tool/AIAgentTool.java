package com.example.ai_chatbot.tool;

import com.example.ai_chatbot.integration.AIChatBotIntegration;
import com.example.ai_chatbot.integration.cmd.MusicCmd;
import com.example.ai_chatbot.integration.vo.PlayRecordVO;
import com.example.ai_chatbot.integration.vo.PrizeRecordVO;
import com.example.ai_chatbot.integration.vo.SingleSystemConfigVO;
import com.example.ai_chatbot.integration.vo.SystemConfigVO;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AIAgentTool {
    @Autowired
    private AIChatBotIntegration aiChatBotIntegration;

    //如果一个方法上面@Tool代表这个 Java 方法是一个 AI 可以调用的 Tool。
    //name代表注册在大模型时的名字，大模型思考过程打印的工具名字是这个
    //description：方法提示词，用于描述方法干什么，大模型选择工具时的参考，用户输入和提示词越接近，越可能调用该方法
    @Tool(name = "queryPlayRecord", description = "当需要查询用户指定时间范围内的播放记录时调用,返回用户的指定时间范围内所有播放记录")
    public List<PlayRecordVO> queryPlayRecord(//@ToolParam 定义工具的参数提示词，参数获取规则
                                              @ToolParam(description = "播放范围开始，当用户指定某一天默认使用这一天的00:00:00作为开始时间")String startTime,
                                              @ToolParam(description = "播放范围结束，当用户未指定结束时间时，默认使用当前时间")String endTime,
                                              @ToolParam(description = "开始页面，当用户未指定的时候，默认0开始")int pageStart,
                                              @ToolParam(description = "页面大小，当用户未指定的时候，默认为10000")int pageSize,
                                              //ToolContext前面没有@ToolParam，说明参数不来源于大模型，来自于前端。
                                              ToolContext toolContext){
        //工具的执行过程，大模型不干涉，大模型工具选择，不干涉工具执行过程
       return aiChatBotIntegration.queryPlayRecordList((Integer) toolContext.getContext().get("userId"), startTime, endTime, pageStart, pageSize);
    }

    @Tool(name = "queryPrizeRecord", description = "当需要查询用户指定时间范围内的发奖记录时调用,返回用户的指定时间范围内所有发奖记录")
    public List<PrizeRecordVO> queryPrizeRecord(ToolContext toolContext,
                                                @ToolParam(description = "要查询的发奖开始时间,当用户指定某一天默认使用这一天的00:00:00作为开始时间")String startTime,
                                                @ToolParam(description = "要查询的发奖结束时间，当用户未指定结束时间时，默认使用当前时间")String endTime){
        return aiChatBotIntegration.queryPrizeRecordList((Integer) toolContext.getContext().get("userId"),startTime,endTime);
    }

    @Tool(name = "querySystemConfigByCode",description = "当需要查询用户提出一些模糊问题的时候，例如：“我今天奖励为什么发放不对？”，进行调用去核实当前系统发放奖励的规则")
    public SystemConfigVO querySystemConfigByCode(@ToolParam(description = "需要查询奖品对应的code,code的可选范围为stage和amount,其中stage为播放总时长和发奖阶段的映射关系，amount为播放总时长和发奖数量的映射关系")String code){
        return aiChatBotIntegration.querySystemConfigByCode(code);
    }

    @Tool(name = "addMusic", description = "当用户需要添加音乐的时候调用")
    public void addMusic(@ToolParam(description = "音乐内容")String content,
                         @ToolParam(description = "音乐标题") String title,
                         @ToolParam(description = "音乐作者") String author,
                         @ToolParam(description = "音乐标签") String tags,
                         @ToolParam(description = "音乐封面url，默认为空")String pictureUrl,
                         @ToolParam(description = "音乐音频url")String audioUrl){
        MusicCmd cmd = new MusicCmd();
        cmd.setContent(content);
        cmd.setTitle(title);
        cmd.setAuthor(author);
        cmd.setTags(tags);
        cmd.setPictureUrl(pictureUrl);
        cmd.setAudioUrl(audioUrl);
        aiChatBotIntegration.addMusic(cmd);
    }
}
