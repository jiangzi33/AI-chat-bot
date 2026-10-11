package com.example.ai_chatbot.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.DefaultAroundAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.model.tool.ToolExecutionResult;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 多轮 Tool Calling 的 Advisor（Spring AI 1.0 没有内置的 ToolCallingAdvisor，这里自己实现）。
 * 排在 MessageChatMemoryAdvisor 之后，这样记忆只保存用户问题和最终回答，不会每轮重复写入。
 */
public class DetailedToolCallingAdvisor implements CallAdvisor {

    private static final Logger log = LoggerFactory.getLogger(DetailedToolCallingAdvisor.class);

    private static final int ORDER = Advisor.DEFAULT_CHAT_MEMORY_PRECEDENCE_ORDER + 100;

    private final ToolCallingManager toolCallingManager;

    private final int maxIterations;

    public DetailedToolCallingAdvisor(ToolCallingManager toolCallingManager, int maxIterations) {
        Assert.notNull(toolCallingManager, "toolCallingManager must not be null");
        Assert.isTrue(maxIterations > 0, "maxIterations must be greater than 0");
        this.toolCallingManager = toolCallingManager;
        this.maxIterations = maxIterations;
    }

    @Override
    public String getName() {
        return "DetailedToolCallingAdvisor";
    }

    @Override
    public int getOrder() {
        return ORDER;
    }

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {
        Assert.notNull(request, "request must not be null");
        Assert.notNull(chain, "chain must not be null");

        if (!(request.prompt().getOptions() instanceof ToolCallingChatOptions requestOptions)) {
            return chain.nextCall(request);
        }

        // 关掉模型内部的自动工具执行，由本 Advisor 逐轮执行工具，才能打印每轮过程并限制轮次
        ToolCallingChatOptions options = requestOptions.copy();
        options.setInternalToolExecutionEnabled(false);

        List<Message> instructions = request.prompt().getInstructions();

        for (int iteration = 1; iteration <= this.maxIterations; iteration++) {
            log.info("========== HelloAgent 思考轮次 {}/{} ==========", iteration, this.maxIterations);

            ChatClientRequest iterationRequest = ChatClientRequest.builder()
                    .prompt(new Prompt(instructions, options))
                    .context(request.context())
                    .build();

            ChatClientResponse clientResponse = downstreamChain(chain).nextCall(iterationRequest);
            ChatResponse chatResponse = clientResponse.chatResponse();
            Assert.notNull(chatResponse, "chatResponse must not be null");

            printModelResponse(chatResponse);

            if (!chatResponse.hasToolCalls()) {
                log.info("HelloAgent 在第 {} 轮完成回答", iteration);
                return clientResponse;
            }

            if (iteration == this.maxIterations) {
                String message = "已达到最大思考轮次上限（" + this.maxIterations + "），停止继续调用工具。";
                log.warn(message);
                ChatResponse limitedResponse = ChatResponse.builder()
                        .from(chatResponse)
                        .generations(List.of(new Generation(new AssistantMessage(message))))
                        .build();
                return clientResponse.mutate().chatResponse(limitedResponse).build();
            }

            ToolExecutionResult executionResult = this.toolCallingManager
                    .executeToolCalls(iterationRequest.prompt(), chatResponse);
            printToolResults(executionResult);

            if (executionResult.returnDirect()) {
                ChatResponse directResponse = ChatResponse.builder()
                        .from(chatResponse)
                        .generations(ToolExecutionResult.buildGenerations(executionResult))
                        .build();
                return clientResponse.mutate().chatResponse(directResponse).build();
            }

            instructions = executionResult.conversationHistory();
        }

        throw new IllegalStateException("Unexpected end of HelloAgent iteration loop");
    }

    /**
     * Spring AI 1.0 的 advisor 链每调用一次 nextCall 就会消耗一个节点，不能在同一条链上重复调用。
     * 这里每一轮都用排在本 Advisor 之后的 advisor（最终是调用模型的 ChatModelCallAdvisor）重新建一条链。
     */
    private CallAdvisorChain downstreamChain(CallAdvisorChain chain) {
        List<CallAdvisor> downstream = chain.getCallAdvisors().stream()
                .filter(advisor -> advisor.getOrder() > this.getOrder())
                .toList();
        return DefaultAroundAdvisorChain.builder(chain.getObservationRegistry())
                .pushAll(downstream)
                .build();
    }

    private void printModelResponse(ChatResponse chatResponse) {
        for (Generation generation : chatResponse.getResults()) {
            AssistantMessage output = generation.getOutput();
            String content = output.getText();
            log.info("模型输出: {}", content == null || content.isBlank() ? "<无文本输出>" : content);
            log.info("结束原因: {}", generation.getMetadata().getFinishReason());

            for (AssistantMessage.ToolCall toolCall : output.getToolCalls()) {
                log.info("调用工具: name={}, id={}, type={}",
                        toolCall.name(), toolCall.id(), toolCall.type());
                log.info("工具参数: {}", toolCall.arguments());
            }
        }
    }

    private void printToolResults(ToolExecutionResult executionResult) {
        for (Message message : executionResult.conversationHistory()) {
            if (message instanceof ToolResponseMessage toolResponseMessage) {
                for (ToolResponseMessage.ToolResponse response : toolResponseMessage.getResponses()) {
                    log.info("工具结果: name={}, id={}, result={}",
                            response.name(), response.id(), response.responseData());
                }
            }
        }
    }
}
