# 基础版自动记账APP（无AI） Spec

## Why
用户需要一个简单、直观、可本地使用的记账工具，排除所有AI相关功能，确保数据隐私和操作直接性。此应用旨在提供纯粹的财务记录体验。

## What Changes
- **UI Prototype Design**: 创建一套基于HTML/CSS/FontAwesome的高保真移动端界面原型。
- **Mobile App Development**: 使用React Native构建跨平台移动应用（适配iOS/Android）。
- **Backend Development**: 使用Java Spring Boot构建后端服务，整合MySQL、Redis和MyBatis。
- **Database**: 后端采用MySQL作为主存储，Redis作为缓存；移动端采用SQLite。

## Impact
- **Affected specs**: 新增独立项目。
- **Affected code**: 全新代码库。

## ADDED Requirements
### Requirement: UI Prototype
系统应提供可视化的HTML原型，包含以下页面：
1.  **首页**: 账单概览、快捷记账入口、基础收支统计入口。
2.  **记账页**: 纯手动记账，支持收支类型选择、金额输入、时间选择、备注。
3.  **账单列表页**: 支持筛选、编辑、删除、导出，按时间/类型筛选。
4.  **统计页**: 展示日/周/月收支数字及基础柱状图（无智能分析）。
5.  **个人中心页**: 登录/头像修改/预算设置/本地数据备份恢复。

### Requirement: Mobile Application
- **技术栈**: React Native。
- **功能**:
    - 本地数据存储（SQLite）。
    - 离线记账功能。
    - 数据同步（可选）。
    - 导出Excel功能。

### Requirement: Backend Service
- **技术栈**: Java, Spring Boot, MySQL, Redis, MyBatis。
- **功能**:
    - 用户注册/登录（手机号验证码）。
    - 数据同步接口（RESTful）。
    - 数据备份/恢复接口。
    - 缓存支持 (Redis)。

## REMOVED Requirements
- **AI Features**: 移除所有智能分类、智能分析、语音记账等AI相关入口与逻辑。
