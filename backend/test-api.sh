#!/bin/bash

BASE_URL="http://localhost:8080/api"
COOKIE_FILE="cookies.txt"

echo "开始 API 接口测试..."

# 1. 注册用户
echo "\n[1] 注册用户..."
curl -s -X POST "$BASE_URL/auth/register" \
    -H "Content-Type: application/json" \
    -d '{"phone": "13800138000", "password": "password123"}'
echo ""

# 2. 登录用户
echo "\n[2] 登录用户..."
TOKEN=$(curl -s -X POST "$BASE_URL/auth/login" \
    -H "Content-Type: application/json" \
    -d '{"phone": "13800138000", "password": "password123"}' | grep -o '"token":"[^"]*' | cut -d'"' -f4)

if [ -z "$TOKEN" ]; then
    echo "登录失败，无法获取 Token"
    exit 1
fi
echo "获取 Token: $TOKEN"

# 3. 添加交易
echo "\n[3] 添加交易..."
curl -s -X POST "$BASE_URL/transactions" \
    -H "Authorization: Bearer $TOKEN" \
    -H "Content-Type: application/json" \
    -d '{
        "amount": 100.50,
        "type": "EXPENSE",
        "categoryId": 1,
        "description": "午餐",
        "transactionDate": "2023-10-27"
    }'
echo ""

# 4. 获取交易列表
echo "\n[4] 获取交易列表..."
curl -s -X GET "$BASE_URL/transactions" \
    -H "Authorization: Bearer $TOKEN"
echo ""

# 5. 获取统计数据
echo "\n[5] 获取统计数据..."
curl -s -X GET "$BASE_URL/transactions/stats?type=daily" \
    -H "Authorization: Bearer $TOKEN"
echo ""

echo "\n测试完成。"
