package com.example.agent.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 聊天服务 - 使用 Spring AI Zhipu AI (智谱官方)
 */
@Slf4j
@Service
public class ChatService {

    private final ChatModel chatModel;

    @Value("${spring.ai.zhipuai.chat.options.model:glm-4-flash}")
    private String defaultModel;

    public ChatService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    /**
     * 简单对话
     */
    public String chat(String message) {
        try {
            Prompt prompt = new Prompt(new UserMessage(message));
            ChatResponse response = chatModel.call(prompt);
            String reply = response.getResult().getOutput().getText();
            log.info("AI 回复: {}", reply);
            return reply;
        } catch (Exception e) {
            log.error("调用智谱 BigModel API 失败", e);
            throw new RuntimeException("调用智谱 BigModel API 失败: " + e.getMessage(), e);
        }
    }

    /**
     * 带配置的对话
     */
    public ChatResult chat(ChatRequest request) {
        try {
            Prompt prompt = new Prompt(new UserMessage(request.message()));
            ChatResponse response = chatModel.call(prompt);

            String reply = response.getResult().getOutput().getText();
            String conversationId = request.conversationId() != null
                    ? request.conversationId()
                    : UUID.randomUUID().toString();
            String model = request.model() != null ? request.model() : defaultModel;

            log.info("AI 回复: {}", reply);
            return new ChatResult(reply, conversationId, model);
        } catch (Exception e) {
            log.error("调用智谱 BigModel API 失败", e);
            throw new RuntimeException("调用智谱 BigModel API 失败: " + e.getMessage(), e);
        }
    }

    /**
     * 聊天请求
     */
    public record ChatRequest(String message, String conversationId, String model) {
    }

    /**
     * 聊天结果
     */
    public record ChatResult(String reply, String conversationId, String model) {
    }
}
