package com.example.agent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 聊天响应 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {

    /**
     * AI 回复内容
     */
    private String reply;

    /**
     * 会话ID
     */
    private String conversationId;

    /**
     * 使用的模型
     */
    private String model;

    public static ChatResponse success(String reply, String conversationId, String model) {
        return new ChatResponse(reply, conversationId, model);
    }
}
