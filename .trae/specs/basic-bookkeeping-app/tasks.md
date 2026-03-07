# Tasks

- [x] Task 1: UI原型设计
  - [x] SubTask 1.1: 创建首页HTML/CSS原型
  - [x] SubTask 1.2: 创建记账页HTML/CSS原型
  - [x] SubTask 1.3: 创建账单列表页HTML/CSS原型
  - [x] SubTask 1.4: 创建统计页HTML/CSS原型
  - [x] SubTask 1.5: 创建个人中心页HTML/CSS原型
  - [x] SubTask 1.6: 整合所有页面，添加交互逻辑说明

- [x] Task 2: 后端服务开发 (Spring Boot)
  - [x] SubTask 2.1: 初始化Spring Boot项目 (集成Web, MySQL, Redis, MyBatis)
  - [x] SubTask 2.2: 设计数据库Schema (User, Transaction, Category) 并编写SQL脚本
  - [x] SubTask 2.3: 配置MyBatis Mapper及Redis缓存
  - [x] SubTask 2.4: 实现用户认证接口 (注册/登录/Token)
  - [x] SubTask 2.5: 实现账单CRUD接口
  - [x] SubTask 2.6: 实现统计数据接口
  - [x] SubTask 2.7: 实现数据备份/恢复接口

- [x] Task 3: 移动端APP开发 (React Native)
  - [x] SubTask 3.1: 初始化React Native项目
  - [x] SubTask 3.2: 集成SQLite本地数据库
  - [x] SubTask 3.3: 实现首页UI及逻辑
  - [x] SubTask 3.4: 实现记账页UI及逻辑
  - [x] SubTask 3.5: 实现账单列表页UI及逻辑
  - [x] SubTask 3.6: 实现统计页UI及逻辑
  - [x] SubTask 3.7: 实现个人中心页及数据同步功能

- [x] Task 4: 测试与交付
  - [x] SubTask 4.1: 编写后端接口测试用例 (JUnit)
  - [x] SubTask 4.2: 进行APP功能测试 (手动记账、筛选、导出)
  - [x] SubTask 4.3: 编写部署文档和接口文档
  - [x] SubTask 4.4: 打包发布版本

# Task Dependencies
- [Task 3] depends on [Task 1] (UI Design)
- [Task 3] depends on [Task 2] (API Definition for Sync)
