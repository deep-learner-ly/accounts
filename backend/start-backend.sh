#!/bin/bash

# 定义 Maven 版本和下载地址
MAVEN_VERSION="3.9.6"
MAVEN_DIR="apache-maven-$MAVEN_VERSION"
MAVEN_TAR="$MAVEN_DIR-bin.tar.gz"
DOWNLOAD_URLS=(
    "https://mirrors.aliyun.com/apache/maven/maven-3/$MAVEN_VERSION/binaries/$MAVEN_TAR"
    "https://mirrors.tuna.tsinghua.edu.cn/apache/maven/maven-3/$MAVEN_VERSION/binaries/$MAVEN_TAR"
    "https://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/$MAVEN_TAR"
)

# 检查 Maven 是否已安装
if command -v mvn &> /dev/null; then
    echo "检测到系统 Maven..."
    mvn spring-boot:run
    exit 0
fi

# 检查本地是否已下载 Maven
if [ -d "$MAVEN_DIR" ]; then
    echo "检测到本地 Maven: $MAVEN_DIR"
    export PATH="$PWD/$MAVEN_DIR/bin:$PATH"
    mvn spring-boot:run
    exit 0
fi

echo "未检测到 Maven，尝试自动下载..."

# 尝试下载
for url in "${DOWNLOAD_URLS[@]}"; do
    echo "尝试从 $url 下载..."
    if curl -L -o "$MAVEN_TAR" "$url"; then
        if tar -xzf "$MAVEN_TAR"; then
            echo "Maven 下载并解压成功！"
            rm "$MAVEN_TAR"
            export PATH="$PWD/$MAVEN_DIR/bin:$PATH"
            mvn -v
            echo "正在启动后端服务..."
            # 添加内存限制，防止 OOM (Exit code 137)
            export MAVEN_OPTS="-Xmx384m"
            mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xmx384m"
            exit 0
        else
            echo "解压失败，尝试下一个镜像..."
            rm "$MAVEN_TAR"
        fi
    else
        echo "下载失败，尝试下一个镜像..."
    fi
done

echo "错误: 无法下载 Maven。请手动下载并安装 Maven，或检查网络连接。"
exit 1
