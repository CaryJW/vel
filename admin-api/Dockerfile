FROM openjdk:11

# 设置时区
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo "Asia/Shanghai" > /etc/timezone

ADD target/vel-0.0.1-SNAPSHOT.jar app.jar

# 配置容器启动后执行的命令
ENTRYPOINT ["java","-jar","/app.jar"]