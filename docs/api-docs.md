# API 文档

本文档列出了记账应用的所有 API 端点。

## 认证 (Auth)

### 注册用户
- **URL**: `/api/auth/register`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
    "phone": "13800138000",
    "password": "password123"
  }
  ```
- **Response**:
  ```json
  {
    "token": "jwt_token_here"
  }
  ```

### 用户登录
- **URL**: `/api/auth/login`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
    "phone": "13800138000",
    "password": "password123"
  }
  ```
- **Response**:
  ```json
  {
    "token": "jwt_token_here"
  }
  ```

## 交易 (Transactions)

所有请求需在 Header 中包含 `Authorization: Bearer <token>`。

### 获取所有交易
- **URL**: `/api/transactions`
- **Method**: `GET`
- **Response**:
  ```json
  [
    {
      "id": 1,
      "amount": 100.00,
      "type": "EXPENSE",
      "categoryId": 1,
      "userId": 1,
      "description": "午餐",
      "transactionDate": "2023-10-27",
      "createdAt": "2023-10-27T12:00:00"
    }
  ]
  ```

### 创建交易
- **URL**: `/api/transactions`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
    "amount": 100.00,
    "type": "EXPENSE",
    "categoryId": 1,
    "description": "午餐",
    "transactionDate": "2023-10-27"
  }
  ```
- **Response**: (Created Transaction object)

### 更新交易
- **URL**: `/api/transactions/{id}`
- **Method**: `PUT`
- **Request Body**:
  ```json
  {
    "amount": 120.00,
    "type": "EXPENSE",
    "categoryId": 1,
    "description": "丰盛午餐",
    "transactionDate": "2023-10-27"
  }
  ```

### 删除交易
- **URL**: `/api/transactions/{id}`
- **Method**: `DELETE`
- **Response**: 200 OK

### 获取统计数据
- **URL**: `/api/transactions/stats?period=monthly` (or `weekly`, `yearly`)
- **Method**: `GET`
- **Response**:
  ```json
  {
    "totalIncome": 5000.00,
    "totalExpense": 2000.00,
    "balance": 3000.00,
    "categoryStats": [
      { "category": "Food", "amount": 1000.00 },
      { "category": "Transport", "amount": 500.00 }
    ]
  }
  ```

## 备份 (Backup)

### 导出数据
- **URL**: `/api/backup`
- **Method**: `GET`
- **Response**: (Backup JSON data)

### 恢复数据
- **URL**: `/api/backup/restore`
- **Method**: `POST`
- **Request Body**: (Backup JSON data)
