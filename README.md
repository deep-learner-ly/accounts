# 记账项目部署指南

本项目包含一个基于 Spring Boot 的后端和一个基于 React Native 的移动端应用，以及一个 HTML/CSS 原型。

## 前置条件 (Prerequisites)

在开始之前，请确保您的开发环境已安装以下软件：

*   **Java**: JDK 17 或更高版本
*   **MySQL**: 数据库服务
*   **Redis**: 缓存服务
*   **Node.js**: v18 或更高版本 (用于运行移动端应用)
*   **Maven**: 构建后端项目

## 数据库设置 (Database Setup)

1.  创建一个名为 `bookkeeping` 的 MySQL 数据库。
    ```sql
    CREATE DATABASE bookkeeping;
    ```
2.  导入数据库结构。运行 `backend/src/main/resources/schema.sql` 中的 SQL 脚本。
3.  配置数据库连接。编辑 `backend/src/main/resources/application.properties` 文件，修改数据库用户名和密码：
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/bookkeeping?useSSL=false&serverTimezone=UTC
    spring.datasource.username=root
    spring.datasource.password=your_password
    ```
4.  确保 Redis 服务正在运行 (默认端口 6379)。

## 后端运行 (Backend)

1.  进入后端目录：
    ```bash
    cd backend
    ```
2.  使用 Maven 运行项目：
    ```bash
    mvn spring-boot:run
    ```
3.  后端服务将在 `http://localhost:8080` 启动。

## 移动端运行 (Mobile App)

1.  进入移动端目录：
    ```bash
    cd mobile-app
    ```
2.  安装依赖：
    ```bash
    npm install
    ```
3.  启动应用：
    ```bash
    npm start
    ```
    这将启动 Expo 开发服务器。您可以使用 Expo Go 应用扫描二维码在手机上预览，或按 `a` 在 Android 模拟器运行，按 `i` 在 iOS 模拟器运行。

## 查看 UI 原型 (UI Prototype)

1.  进入原型目录：
    ```bash
    cd ui-prototype
    ```
2.  直接在浏览器中打开 `index.html` 文件即可查看界面原型。

## 测试 (Testing)

运行后端测试：
```bash
cd backend
mvn test
```
