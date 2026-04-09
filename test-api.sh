#!/bin/bash

# Spring AI Alibaba Agent 框架 API 测试脚本

echo "🚀 Spring AI Alibaba Agent 框架 API 测试"
echo "=========================================="
echo ""

# 颜色定义
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

BASE_URL="http://localhost:8080"

# 测试函数
test_api() {
    local name="$1"
    local url="$2"
    local method="${3:-GET}"
    local data="$4"

    echo -e "${YELLOW}测试: $name${NC}"
    echo "URL: $url"
    echo "Method: $method"

    if [ -n "$data" ]; then
        echo "Data: $data"
        response=$(curl -s -X "$method" "$url" \
            -H "Content-Type: application/json" \
            -d "$data")
    else
        response=$(curl -s -X "$method" "$url")
    fi

    echo "Response:"
    echo "$response" | jq . 2>/dev/null || echo "$response"
    echo ""
}

# 1. 获取 Agent 列表
test_api "获取 Agent 列表" "$BASE_URL/agent/list-apps"

# 2. 简单聊天 (GET)
test_api "简单聊天 (GET)" "$BASE_URL/api/chat?message=你好"

# 3. 简单聊天 (POST)
test_api "简单聊天 (POST)" "$BASE_URL/api/chat/simple" "POST" "你好，请介绍一下自己"

# 4. 完整聊天 (POST)
test_api "完整聊天 (POST)" "$BASE_URL/api/chat" "POST" '{
  "message": "北京今天的天气怎么样？",
  "conversationId": "test-001",
  "model": "glm-4-flash"
}'

# 5. 健康检查
test_api "健康检查" "$BASE_URL/api/chat/health"

# 6. 测试计算器 Agent
test_api "计算器 Agent" "$BASE_URL/api/chat" "POST" '{
  "message": "请计算 123 + 456",
  "agentName": "calculatorAgent"
}'

# 7. 测试天气 Agent
test_api "天气 Agent" "$BASE_URL/api/chat" "POST" '{
  "message": "查询上海的天气",
  "agentName": "weatherAgent"
}'

echo -e "${GREEN}✅ 测试完成！${NC}"
echo ""
echo "💡 提示：访问 Agent Chat UI: $BASE_URL/chatui/index.html"
