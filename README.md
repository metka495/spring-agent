# Spring AI Alibaba Agent 框架

基于 Spring Boot 和 Spring AI Alibaba 的 AI Agent 框架，使用 ReactAgent 构建智能体，集成 Agent Chat UI 前端。

## 项目特点

- ✅ **ReactAgent 智能体**：使用 Spring AI Alibaba 的 ReactAgent 构建智能对话代理
- ✅ **工具调用**：支持天气查询、数学计算等自定义工具
- ✅ **Agent Chat UI**：内置 Web UI，支持动态选择和调用不同的 Agent
- ✅ **多 Agent 支持**：可创建多个专门的 Agent（聊天助手、计算专家、天气助手等）
- ✅ **智谱 GLM 模型**：使用智谱 AI 的 GLM-4-Flash 模型

## 项目结构

```
spirng-agent/
├── src/main/java/com/example/agent/
│   ├── SpringAgentApplication.java         # 启动类
│   ├── config/
│   │   └── AgentConfiguration.java         # Agent 配置（ReactAgent）
│   ├── controller/
│   │   ├── ChatController.java             # REST 聊天接口
│   │   └── AgentRegistryController.java    # Agent 注册接口
│   ├── service/
│   │   ├── ChatService.java                # 聊天服务
│   │   └── AgentService.java               # Agent 管理服务
│   ├── tool/
│   │   ├── WeatherTool.java                # 天气查询工具
│   │   └── CalculatorTool.java             # 计算器工具
│   └── dto/
│       ├── ChatRequest.java                # 请求 DTO
│       ├── ChatResponse.java               # 响应 DTO
│       └── ApiResponse.java                # 统一响应格式
└── src/main/resources/
    └── application.yml                     # 配置文件
```

## 快速开始

### 1. 获取 API Key

访问 [智谱AI开放平台](https://open.bigmodel.cn/usercenter/apikeys) 获取 API Key。

### 2. 配置 API Key

编辑 `src/main/resources/application.yml`：

```yaml
spring:
  ai:
    dashscope:
      api-key: your-actual-api-key
```

或使用环境变量：

```bash
export ZHIPU_API_KEY=your-api-key
```

### 3. 启动项目

```bash
mvn spring-boot:run
```

### 4. 访问 Agent Chat UI

打开浏览器访问：`http://localhost:8080/chatui/index.html`

## 可用的 Agent

项目预配置了 3 个 Agent：

| Agent 名称 | 描述 | 能力 |
|-----------|------|------|
| chatAgent | 智能聊天助手 | 天气查询、数学计算、自然对话 |
| calculatorAgent | 数学计算专家 | 加减乘除、幂运算等数学计算 |
| weatherAgent | 天气查询助手 | 查询城市天气信息 |

## API 使用示例

### 1. 获取 Agent 列表

```bash
curl http://localhost:8080/agent/list-apps
```

响应：

```json
{
  "count": 3,
  "apps": [
    {"name": "chatAgent", "description": "Agent: chatAgent"},
    {"name": "calculatorAgent", "description": "Agent: calculatorAgent"},
    {"name": "weatherAgent", "description": "Agent: weatherAgent"}
  ]
}
```

### 2. 简单聊天（GET）

```bash
curl "http://localhost:8080/api/chat?message=你好"
```

### 3. 简单聊天（POST）

```bash
curl -X POST http://localhost:8080/api/chat/simple \
  -H "Content-Type: text/plain" \
  -d "你好"
```

### 4. 完整聊天（POST）

```bash
curl -X POST http://localhost:8080/api/chat \
  -H "Content-Type: application/json" \
  -d '{
    "message": "请介绍一下 Spring AI",
    "conversationId": "conv-001",
    "model": "glm-4-flash"
  }'
```

### 5. 健康检查

```bash
curl http://localhost:8080/api/chat/health
```

## Agent Chat UI 使用

### 访问 UI

启动应用后，打开浏览器访问：

```
http://localhost:8080/chatui/index.html
```

### UI 功能

1. **动态选择 Agent**：从下拉列表中选择要使用的 Agent
2. **实时对话**：与选中的 Agent 进行实时对话
3. **工具调用可视化**：查看 Agent 调用工具的过程和结果

### 环境配置（可选）

如果需要使用独立的前端模式，可以配置前端环境：

```properties
# .env.development
NEXT_PUBLIC_API_URL=http://localhost:8080
NEXT_PUBLIC_APP_NAME=chatAgent
NEXT_PUBLIC_USER_ID=user-001
```

## 技术栈

- **Spring Boot 3.3.6** - Web 框架
- **Spring AI Alibaba 1.1.2.2** - AI 框架
  - spring-ai-alibaba-starter-dashscope - DashScope 模型集成
  - spring-ai-alibaba-agent-framework - Agent 框架
  - spring-ai-alibaba-studio - Agent Chat UI
- **ReactAgent** - Agent 实现方式
- **智谱 GLM-4-Flash** - AI 模型
- **Jackson** - JSON 处理
- **Lombok** - 简化代码

## 自定义工具

创建自定义工具非常简单，只需使用 `@Tool` 注解：

```java
@Component
public class MyTool {

    @Tool(description = "工具的描述信息")
    public String myMethod(@ToolParam(description = "参数描述") String param) {
        // 工具逻辑
        return "结果";
    }
}
```

然后在 Agent 配置中添加工具：

```java
@Bean("myAgent")
public ReactAgent myAgent(ChatModel chatModel, MyTool myTool) {
    return ReactAgent.builder()
            .name("my-agent")
            .description("我的 Agent 描述")
            .model(chatModel)
            .methodTools(myTool)
            .build();
}
```

## 创建自定义 Agent

在 `AgentConfiguration` 类中添加新的 Agent：

```java
@Bean("myCustomAgent")
public ReactAgent myCustomAgent(ChatModel chatModel) {
    ReactAgent agent = ReactAgent.builder()
            .name("my-custom-agent")
            .description("自定义 Agent 的描述")
            .model(chatModel)
            .systemPrompt("你是一个专业的助手...")
            .methodTools(/* 添加工具 */)
            .build();
    agentService.registerAgent("myCustomAgent", agent);
    return agent;
}
```

## 下一步扩展

- [ ] 添加对话历史管理
- [ ] 添加流式输出（SSE）
- [ ] 添加更多自定义工具
- [ ] 添加 RAG（知识检索增强）
- [ ] 添加多 Agent 协作

## 参考资料

- [Spring AI Alibaba 官方文档](https://java2ai.com/docs/overview)
- [Spring AI Alibaba GitHub](https://github.com/alibaba/spring-ai-alibaba)
- [智谱 AI 开放平台](https://open.bigmodel.cn/)
- [ReactAgent 文档](https://java2ai.com/docs/frameworks/agent-framework/tutorials/agents)
- [工具调用文档](https://java2ai.com/docs/frameworks/agent-framework/tutorials/tools)

## 许可证

MIT License
