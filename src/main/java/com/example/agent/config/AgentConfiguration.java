package com.example.agent.config;

import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.example.agent.service.AgentService;
import com.example.agent.tool.CalculatorTool;
import com.example.agent.tool.WeatherTool;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Agent 配置类 - 使用 ReactAgent 构建智能体
 */
@Configuration
public class AgentConfiguration {

    @Autowired
    private AgentService agentService;

    /**
     * 创建聊天助手 Agent
     * 这个 Agent 可以使用天气查询和计算器工具
     */
    @Bean("chatAgent")
    public ReactAgent chatAgent(ChatModel chatModel, WeatherTool weatherTool, CalculatorTool calculatorTool) {
        ReactAgent agent = ReactAgent.builder()
                .name("chat-agent")
                .description("一个智能聊天助手，可以查询天气信息、进行数学计算，并与用户进行自然对话")
                .model(chatModel)
                .systemPrompt("""
                        你是一个友好、专业的 AI 助手。你可以帮助用户：
                        1. 查询天气信息
                        2. 进行数学计算
                        3. 回答各种问题

                        请用中文回复，保持友好和专业的语气。
                        当需要使用工具时，请先向用户说明你要执行的操作。
                        """)
                .methodTools(weatherTool, calculatorTool)
                .build();
        agentService.registerAgent("chatAgent", agent);
        return agent;
    }

    /**
     * 创建计算专家 Agent
     * 专注于数学计算
     */
    @Bean("calculatorAgent")
    public ReactAgent calculatorAgent(ChatModel chatModel, CalculatorTool calculatorTool) {
        ReactAgent agent = ReactAgent.builder()
                .name("calculator-agent")
                .description("数学计算专家，擅长各种数学运算")
                .model(chatModel)
                .systemPrompt("""
                        你是一个数学计算专家。你可以：
                        1. 进行加减乘除运算
                        2. 进行幂运算
                        3. 解决数学问题

                        请用中文回复，在计算前向用户确认计算表达式。
                        """)
                .methodTools(calculatorTool)
                .build();
        agentService.registerAgent("calculatorAgent", agent);
        return agent;
    }

    /**
     * 创建天气助手 Agent
     * 专注于天气查询
     */
    @Bean("weatherAgent")
    public ReactAgent weatherAgent(ChatModel chatModel, WeatherTool weatherTool) {
        ReactAgent agent = ReactAgent.builder()
                .name("weather-agent")
                .description("天气查询助手，可以查询各城市的天气信息")
                .model(chatModel)
                .systemPrompt("""
                        你是一个天气查询助手。你可以：
                        1. 查询指定城市的天气
                        2. 提供天气相关信息

                        请用中文回复，友好地回答用户的天气相关问题。
                        """)
                .methodTools(weatherTool)
                .build();
        agentService.registerAgent("weatherAgent", agent);
        return agent;
    }
}
