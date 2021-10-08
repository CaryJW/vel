# vel

![https://img.shields.io/badge/java-1.8%2B-yellow](https://img.shields.io/badge/java-1.8%2B-yellow)
![https://img.shields.io/badge/springboot-2.4.5-yellow](https://img.shields.io/badge/springboot-2.4.5-yellow)
![https://img.shields.io/badge/mybatis--plus-3.4.0-yellow](https://img.shields.io/badge/mybatis--plus-3.4.0-yellow)
![https://img.shields.io/badge/shiro-1.7.0-yellow](https://img.shields.io/badge/shiro-1.7.0-yellow)
![https://img.shields.io/badge/jjwt-0.11.2-yellow](https://img.shields.io/badge/jjwt-0.11.2-yellow)

给 [vel-admin](https://gitee.com/flyxiaozhu/vel-admin) 后台和其他业务提供 api

## JDK版本

1.8+

## JDK16版本导致`easyexcel`使用异常

vm选项添加，具体原因：

- [如何解决Java 9上的InaccessibleObjectException](https://www.itranslater.com/qa/details/2582505421583942656)
- [高版本Java中的反射](https://www.mcbbs.net/thread-1211948-1-1.html)

````
--add-opens java.base/java.lang=ALL-UNNAMED
````

## sql文件

````
docs\vel.sql
docs\permissions.sql
````

## 模块划分

### 1. 多模块

````
vel
 ├─ admin-api   提供后台 api
 ├─ core        公用模块
 └─ service-api 提供其他业务 api
````

### 2. 单模块

[single-vel](https://gitee.com/flyxiaozhu/single-vel)

## 相关配置

### 1. 文件上传配置

````
sys:
  #上传方式：local 本地、ali 阿里oss
  upload-type: local
````

### 2. `xss` 配置

````
xss:
    # 是否过滤富文本
    is-include-rich-text: false
    # 忽略过滤的 url
    excludes:
      - /favicon.ico
      - /image/*
````

### 3. `shiro` 配置

````
shiro:
  # 开启aop日志记录
  aop-log: true
  # 免认证的路径配置
  anon_url:
    - /
    - /static/**
    - /file/**
    - /test/**
    - /login
    - /captcha
````

### 4. `token` 配置

````
jwt:
  # jwt 密钥
  secret: 6sqGjmX3m6R4TMAVIQ+GmNdJJuGxzCp3FMeOn3WSVF0=
  # jwt 过期时间 两小时 单位秒
  expire_time: 7200
````

### 5. 跨域配置

````
sys:
  cors:
    # 同源允许的域名请求，* 表示接受任意域名的请求
    origin: "*"
    # 允许跨域的请求方法
    methods: GET,POST,OPTIONS,PUT,DELETE
````

### 6. 异步处理（使用异步线程，并没有使用消息队列）

````
/**
* 异步保存操作日志
*
* @param point 切点
* @param log   日志
*/
@Async("velAsyncThreadPool")
void saveLog(ProceedingJoinPoint point, Log log);
````

## 代码自动生成

````
配置运行 core/src/main/java/..../CodeGenerator.java
````

## 部署
* 将 `vel-admin` 前端文件打包，覆盖 `vel\admin-api\src\main\resources\public`
* maven 打包

