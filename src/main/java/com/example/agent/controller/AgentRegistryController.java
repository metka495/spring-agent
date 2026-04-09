package com.example.agent.controller;

import com.example.agent.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Agent 注册控制器 - 提供 Agent 列表 API
 * 用于 Agent Chat UI 前端动态获取可用的 Agent
 */
@RestController
@RequestMapping("/agent")
public class AgentRegistryController {

    @Autowired
    private AgentService agentService;

    /**
     * 获取所有已注册的 Agent 列表
     * 这个端点供 Agent Chat UI 前端调用
     */
    @GetMapping("/list-apps")
    public Map<String, Object> listApps() {
        Map<String, Object> result = new HashMap<>();

        // 获取所有 Agent 并转换为前端需要的格式
        List<Map<String, String>> apps = agentService.getAllAgents().entrySet().stream()
                .map(entry -> {
                    Map<String, String> appInfo = new HashMap<>();
                    appInfo.put("name", entry.getKey());
                    appInfo.put("description", "Agent: " + entry.getKey());
                    return appInfo;
                })
                .collect(Collectors.toList());

        result.put("apps", apps);
        result.put("count", apps.size());

        return result;
    }

    /**
     * 获取 Agent 详细信息
     */
    @GetMapping("/info")
    public Map<String, Object> getAgentInfo(String name) {
        Map<String, Object> result = new HashMap<>();

        if (!agentService.hasAgent(name)) {
            result.put("error", "未找到名为 " + name + " 的 Agent");
            return result;
        }

        var agent = agentService.getAgent(name);
        result.put("name", name);
        result.put("description", "Agent: " + name);
        result.put("exists", true);

        return result;
    }
}
