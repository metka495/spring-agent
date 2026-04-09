package com.example.agent.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * 计算器工具 - Agent 可调用的数学计算方法
 */
@Component
public class CalculatorTool {

    /**
     * 加法运算
     */
    @Tool(description = "计算两个数的和")
    public double add(
            @ToolParam(description = "第一个数") double a,
            @ToolParam(description = "第二个数") double b) {
        System.out.println("计算: " + a + " + " + b);
        return a + b;
    }

    /**
     * 减法运算
     */
    @Tool(description = "计算两个数的差")
    public double subtract(
            @ToolParam(description = "被减数") double a,
            @ToolParam(description = "减数") double b) {
        System.out.println("计算: " + a + " - " + b);
        return a - b;
    }

    /**
     * 乘法运算
     */
    @Tool(description = "计算两个数的积")
    public double multiply(
            @ToolParam(description = "第一个数") double a,
            @ToolParam(description = "第二个数") double b) {
        System.out.println("计算: " + a + " * " + b);
        return a * b;
    }

    /**
     * 除法运算
     */
    @Tool(description = "计算两个数的商")
    public double divide(
            @ToolParam(description = "被除数") double a,
            @ToolParam(description = "除数") double b) {
        System.out.println("计算: " + a + " / " + b);
        if (b == 0) {
            throw new IllegalArgumentException("除数不能为零");
        }
        return a / b;
    }

    /**
     * 幂运算
     */
    @Tool(description = "计算一个数的 n 次方")
    public double power(
            @ToolParam(description = "底数") double base,
            @ToolParam(description = "指数") double exponent) {
        System.out.println("计算: " + base + " ^ " + exponent);
        return Math.pow(base, exponent);
    }
}
