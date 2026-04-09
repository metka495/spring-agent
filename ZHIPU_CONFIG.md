# 智谱 Zhipu AI 配置指南

## ✅ 配置已更新完成

项目已使用 **Spring AI 官方 Zhipu AI Starter**。

### 📝 配置说明

#### Maven 依赖（pom.xml）

```xml
<properties>
    <spring-ai.version>1.0.0</spring-ai.version>
</properties>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.ai</groupId>
            <artifactId>spring-ai-bom</artifactId>
            <version>${spring-ai.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

<dependencies>
    <!-- Spring AI Zhipu AI Starter (智谱官方) -->
    <dependency>
        <groupId>org.springframework.ai</groupId>
        <artifactId>spring-ai-starter-model-zhipuai</artifactId>
    </dependency>
</dependencies>
```

#### 配置文件（application.yml）

```yaml
spring:
  ai:
    zhipuai:
      api-key: ${ZHIPUAI_API_KEY:your-api-key}
      chat:
        options:
          model: glm-4-flash
          temperature: 0.7
```

### 🚀 支持的智谱模型

| 模型 | 常量 | 说明 |
|------|------|------|
| glm-4-flash | GLM_4_Flash | 低价、高速模型 |
| glm-4-plus | GLM_4_Plus | 高性能模型 |
| glm-4-air | GLM_4_Air | 性价比模型 |
| glm-4-0520 | GLM_4_0520 | 最新版本 |

### 🔑 获取 API Key

1. 访问 [智谱AI开放平台](https://open.bigmodel.cn/usercenter/apikeys)
2. 注册/登录账号
3. 创建 API Key
4. 复制 API Key

### ⚙️ 配置 API Key

**方式一：环境变量（推荐）**

```bash
export ZHIPUAI_API_KEY=your-api-key
```

**方式二：修改配置文件**

编辑 `src/main/resources/application.yml`：

```yaml
spring:
  ai:
    zhipuai:
      api-key: your-api-key-here
```

### 🎯 在 IDEA 中启动

1. **打开项目**
   - 在 IDEA 中打开项目目录

2. **配置 API Key**
   - 方式一：设置环境变量 `ZHIPUAI_API_KEY`
   - 方式二：直接修改 `application.yml` 中的 `api-key`

3. **启动应用**
   - 找到 `SpringAgentApplication.java`
   - 右键 → Run 'SpringAgentApplication'

4. **访问前端**
   ```
   http://localhost:8080/chatui/index.html
   ```

### 🧪 测试 API

```bash
# 1. 获取 Agent 列表
curl http://localhost:8080/agent/list-apps

# 2. 简单聊天
curl -X POST http://localhost:8080/api/chat/simple \
  -H "Content-Type: text/plain" \
  -d "你好"

# 3. 完整聊天
curl -X POST http://localhost:8080/api/chat \
  -H "Content-Type: application/json" \
  -d '{
    "message": "请介绍一下智谱 AI",
    "conversationId": "test-001",
    "model": "glm-4-flash"
  }'
```

### 📊 代码示例

#### 使用 ChatModel

```java
@Service
public class ChatService {

    private final ChatModel chatModel;

    public ChatService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String chat(String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        ChatResponse response = chatModel.call(prompt);
        return response.getResult().getOutput().getText();
    }
}
```

#### 运行时配置

```java
ChatResponse response = chatModel.call(
    new Prompt(
        "生成5个著名海盗的名字",
        ZhiPuAiChatOptions.builder()
            .model(ZhiPuAiApi.ChatModel.GLM_4_Air.getValue())
            .temperature(0.5)
        .build()
    ));
```

### 🔍 验证配置

启动应用后，查看日志确认配置正确：

```
✅ 使用 ZhipuAiChatModel
✅ Model: glm-4-flash
```

### ❓ 常见问题

**Q: Zhipu AI Starter 和 OpenAI Starter 有什么区别？**

A: Zhipu AI Starter 是 Spring AI 官方为智谱 AI 提供的原生支持，直接使用智谱 API，不需要 OpenAI 兼容层。

**Q: 如何更换模型？**

A: 修改 `application.yml` 中的 `spring.ai.zhipuai.chat.options.model`。

**Q: API Key 格式是什么？**

A: 智谱 API Key 格式类似：`id.secret`

### 📚 参考资料

- [Spring AI Zhipu AI 官方文档](https://docs.spring.io/spring-ai/reference/api/chat/zhipuai-chat)
- [智谱 AI 开放平台](https://open.bigmodel.cn/)
- [智谱 API 文档](https://open.bigmodel.cn/dev/api)

### 🎉 完成配置

配置完成后，你就可以：

1. ✅ 使用智谱 GLM 系列模型
2. ✅ 调用 Agent 进行对话
3. ✅ 使用工具（天气查询、计算器等）
4. ✅ 通过 Agent Chat UI 进行交互

---

**提示**：请勿将 API Key 提交到版本控制系统。建议使用环境变量管理敏感信息。
