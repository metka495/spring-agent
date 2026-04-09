package com.example.agent.dto;

import lombok.Data;

/**
 * 聊天请求 DTO
 */
@Data
public class ChatRequest {

    /**
     * 用户消息内容
     */
    private String message;

    /**
     * 会话ID（可选，用于多轮对话）
     */
    private String conversationId;

    /**
     * 模型名称（可选，默认使用配置的模型）
     */
    private String model;
}
