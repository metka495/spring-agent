package com.example.agent.controller;

import com.example.agent.dto.ApiResponse;
import com.example.agent.dto.ChatRequest;
import com.example.agent.dto.ChatResponse;
import com.example.agent.service.ChatService;
import com.example.agent.service.ChatService.ChatResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 聊天控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    /**
     * 简单聊天接口
     * POST /api/chat
     * Body: { "message": "你好" }
     */
    @PostMapping
    public ApiResponse<ChatResponse> chat(@RequestBody com.example.agent.dto.ChatRequest request) {
        try {
            ChatService.ChatRequest serviceRequest = new ChatService.ChatRequest(
                request.getMessage(),
                request.getConversationId(),
                request.getModel()
            );
            ChatResult result = chatService.chat(serviceRequest);
            ChatResponse response = ChatResponse.success(result.reply(), result.conversationId(), result.model());
            return ApiResponse.success(response);
        } catch (Exception e) {
            log.error("聊天处理失败", e);
            return ApiResponse.error("聊天处理失败: " + e.getMessage());
        }
    }

    /**
     * 简单消息接口（仅传文本）
     * POST /api/chat/simple
     * Body: "你好"
     */
    @PostMapping("/simple")
    public ApiResponse<ChatResponse> simpleChat(@RequestBody String message) {
        try {
            String reply = chatService.chat(message);
            ChatResponse response = ChatResponse.success(reply, null, "glm-4-flash");
            return ApiResponse.success(response);
        } catch (Exception e) {
            log.error("聊天处理失败", e);
            return ApiResponse.error("聊天处理失败: " + e.getMessage());
        }
    }

    /**
     * GET 请求方式聊天
     * GET /api/chat?message=你好
     */
    @GetMapping
    public ApiResponse<ChatResponse> getChat(@RequestParam String message) {
        try {
            String reply = chatService.chat(message);
            ChatResponse response = ChatResponse.success(reply, null, "glm-4-flash");
            return ApiResponse.success(response);
        } catch (Exception e) {
            log.error("聊天处理失败", e);
            return ApiResponse.error("聊天处理失败: " + e.getMessage());
        }
    }

    /**
     * 健康检查
     */
    @GetMapping("/health")
    public ApiResponse<String> health() {
        return ApiResponse.success("Spring AI Agent is running!");
    }
}
