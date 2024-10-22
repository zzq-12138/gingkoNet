# 项目报告

## 1. 项目简介
   本项目是一个基于Java和 JavaScript的网盘系统，用户可以注册、登录并上传文件。项目使用了Thymeleaf作为模板引擎，MySQL作为数据库，Tomcat作为服务器。

## 2. 环境配置
   JDK: 21.0.2或以上 
   Maven: 3.6或以上  
   MySQL: 5.7或以上  
   Tomcat: 9.0.86或以上  
   IDE: IntelliJ IDEA 2024.1.2

## 3. 数据库配置
创建数据库：  
```
CREATE DATABASE schedule_system;  
```

创建用户表：
```
CREATE TABLE sys_user (
uid INT AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(50) NOT NULL,
user_pwd VARCHAR(100) NOT NULL
);
```
## 4. 项目配置
   在src/main/resources目录下创建application.properties文件，配置数据库连接信息：
```java
spring.datasource.url=jdbc:mysql://localhost:3306/schedule_system
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

## 5. 项目运行
   使用Maven构建项目：  
   mvn clean install  
   部署到Tomcat：  
   将生成的war文件复制到Tomcat的webapps目录下。  
   启动Tomcat服务器。
   将项目同步到云服务器的Linux系统，在云服务器上进行项目配置
## 6. 功能说明
###   用户注册：  
   访问/register页面，输入用户名和密码进行注册。  
   密码必须是8-16位的数字、大写字母、小写字母和合法特殊符号中任选三种的组合。  
   在注册页面每次输入密码后，只要鼠标点击输入框以外就自动检测并返回当前密码格式核对情况。
###   用户登录：  
   访问/login页面，输入用户名和密码进行登录。  
###   文件上传：  
   登录成功后，在/list界面选择上传，并且允许重命名文件  
###    文件下载：
    在/list界面选择下载文件
###   文件删除：
    在/list界面选择删除文件
## 7. 代码结构
   src/com/atguigu/schedule/dao: 数据访问层
   src/com/atguigu/schedule/service: 业务逻辑层
   src/com/atguigu/schedule/controller: 控制器层
   src/com/atguigu/schedule/util: 工具类
