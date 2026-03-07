# 后端服务配置指南

由于当前环境缺少 Maven 构建工具且网络受限，无法自动构建和启动 Java 后端。请按照以下步骤手动配置环境并启动服务。

## 1. 安装 Maven

请从 Apache Maven 官网下载最新版本的 Maven 二进制包：
- 下载地址: https://maven.apache.org/download.cgi
- 解压下载的文件。
- 将 `bin` 目录添加到系统的 PATH 环境变量中。
- 验证安装: `mvn -version`

## 2. 配置数据库

确保本地已安装并启动 MySQL 和 Redis 服务。

### MySQL
1.  登录 MySQL: `mysql -u root -p`
2.  创建数据库:
    ```sql
    CREATE DATABASE bookkeeping;
    ```
3.  修改配置:
    编辑 `src/main/resources/application.properties` 文件，确保 `spring.datasource.username` 和 `spring.datasource.password` 与您的本地配置一致。

### Redis
确保 Redis 服务已启动并在默认端口 (6379) 监听。

## 3. 启动后端

在 `backend` 目录下执行以下命令：

```bash
mvn spring-boot:run
```

如果首次运行，Maven 会下载所需的依赖包，这可能需要一些时间。

## 4. 运行测试

### 单元测试
```bash
mvn test
```

### API 接口测试
后端启动成功后（默认端口 8080），您可以运行提供的测试脚本进行验证：

```bash
./test-api.sh
```

## 常见问题

- **依赖下载慢**: 可以在 `~/.m2/settings.xml` 中配置国内镜像源（如阿里云 Maven 镜像）。
- **数据库连接失败**: 请检查 `application.properties` 中的 URL、用户名和密码。
