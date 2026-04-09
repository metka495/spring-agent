package com.example.agent.service;

import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Agent 服务 - 管理和调用不同的 Agent
 */
@Service
public class AgentService {

    private final Map<String, ReactAgent> agents = new HashMap<>();

    /**
     * 注册 Agent
     */
    public void registerAgent(String name, ReactAgent agent) {
        agents.put(name, agent);
    }

    /**
     * 获取所有已注册的 Agent
     */
    public Map<String, ReactAgent> getAllAgents() {
        return new HashMap<>(agents);
    }

    /**
     * 根据 name 获取 Agent
     */
    public ReactAgent getAgent(String name) {
        return agents.get(name);
    }

    /**
     * 调用指定的 Agent
     */
    public String callAgent(String agentName, String message) {
        ReactAgent agent = getAgent(agentName);
        if (agent == null) {
            throw new IllegalArgumentException("未找到名为 " + agentName + " 的 Agent");
        }

        try {
            // 使用 UserMessage 包装用户输入
            AssistantMessage response = agent.call(new UserMessage(message));
            return response.getText();
        } catch (Exception e) {
            return "调用 Agent 失败: " + e.getMessage();
        }
    }

    /**
     * 检查 Agent 是否存在
     */
    public boolean hasAgent(String name) {
        return agents.containsKey(name);
    }

    /**
     * 获取所有 Agent 名称
     */
    public Collection<String> getAgentNames() {
        return agents.keySet();
    }
}
