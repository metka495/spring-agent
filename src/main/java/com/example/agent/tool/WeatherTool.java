package com.example.agent.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * 天气查询工具 - Agent 可调用的工具方法示例
 */
@Component
public class WeatherTool {

    /**
     * 查询天气信息
     */
    @Tool(description = "查询指定城市的天气信息")
    public String getWeather(@ToolParam(description = "城市名称") String city) {
        System.out.println("查询城市天气: " + city);

        // 模拟天气查询
        if (city == null || city.isEmpty()) {
            return "请提供城市名称";
        }

        // 这里可以调用真实的天气 API
        return String.format("%s 今天天气晴朗，温度 25°C，适合出行！", city);
    }

    /**
     * 获取当前时间
     */
    @Tool(description = "获取当前时间")
    public String getCurrentTime() {
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        return "当前时间是: " + now.toString();
    }
}
