# Agent Chat UI 前端设置指南

## 方式选择

### ✅ 方式一：嵌入式前端（推荐）

前端已自动集成，无需额外配置：

1. 启动后端应用：
   ```bash
   mvn spring-boot:run
   ```

2. 访问前端：
   ```
   http://localhost:8080/chatui/index.html
   ```

---

### 🔧 方式二：独立前端（开发模式）

适用于需要修改前端代码或进行前端开发的场景。

#### 1. 克隆 Spring AI Alibaba 仓库

```bash
cd /path/to/your/workspace
git clone https://github.com/alibaba/spring-ai-alibaba.git
cd spring-ai-alibaba/spring-ai-alibaba-studio/agent-chat-ui
```

#### 2. 安装依赖

```bash
# 使用 npm
npm install

# 或使用 pnpm（推荐）
pnpm install
```

#### 3. 配置环境变量

创建 `.env.development` 文件：

```properties
# 后端 API 地址
NEXT_PUBLIC_API_URL=http://localhost:8080

# 默认 Agent 名称（必须与后端注册的 Agent 名称一致）
NEXT_PUBLIC_APP_NAME=chatAgent

# 用户 ID
NEXT_PUBLIC_USER_ID=user-001
```

#### 4. 启动前端开发服务器

```bash
# 使用 npm
npm run dev

# 或使用 pnpm
pnpm dev
```

#### 5. 访问前端

```
http://localhost:3000
```

---

## 前端功能说明

### Agent 选择

前端会从后端动态获取可用的 Agent 列表：

- **chatAgent** - 智能聊天助手
- **calculatorAgent** - 数学计算专家
- **weatherAgent** - 天气查询助手

### 对话功能

1. **选择 Agent**：从下拉列表选择要使用的 Agent
2. **发送消息**：输入消息并点击发送
3. **查看响应**：实时查看 Agent 的响应
4. **工具调用可视化**：查看 Agent 调用工具的过程

### API 端点

前端会调用以下后端 API：

- `/agent/list-apps` - 获取 Agent 列表
- `/agent/chat` - 聊天接口

---

## 开发建议

### 前端开发

如果需要修改前端代码：

1. 使用独立前端模式
2. 修改 `agent-chat-ui` 目录下的代码
3. 前端会自动热重载

### 后端开发

如果只需要使用前端：

1. 使用嵌入式前端（方式一）
2. 专注于后端 Agent 逻辑开发
3. 前端会自动更新

---

## 故障排除

### 问题：前端无法连接后端

**解决方案**：
1. 确保后端应用正在运行：`curl http://localhost:8080/agent/list-apps`
2. 检查 `.env.development` 中的 `NEXT_PUBLIC_API_URL` 配置
3. 确保端口没有被占用

### 问题：Agent 列表为空

**解决方案**：
1. 检查后端日志，确认 Agent 已正确注册
2. 确保使用了正确的 Agent 名称
3. 查看后端 `AgentController` 的初始化日志

### 问题：对话无响应

**解决方案**：
1. 检查 API Key 是否正确配置
2. 查看后端日志中的错误信息
3. 确认网络连接正常

---

## 生产部署

### 嵌入式前端

生产环境直接使用嵌入式前端，无需额外部署：

```bash
mvn clean package
java -jar target/spring-agent-1.0.0.jar
```

访问：`http://your-server:8080/chatui/index.html`

### 独立前端

如果需要独立部署前端：

```bash
# 1. 构建前端
cd agent-chat-ui
npm run build

# 2. 部署 dist 目录到静态文件服务器
# 例如：nginx、Apache、CDN 等
```

---

## 更多信息

- [Spring AI Alibaba 官方文档](https://java2ai.com/docs/overview)
- [Agent Chat UI GitHub](https://github.com/alibaba/spring-ai-alibaba/tree/main/spring-ai-alibaba-studio/agent-chat-ui)
