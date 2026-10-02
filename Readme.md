### 网盘项目使用说明

#### 1. 项目简介

本项目是一个基于Java和 JavaScript的网盘系统，用户可以注册、登录并上传文件。项目使用了Thymeleaf作为模板引擎，MySQL作为数据库，Tomcat作为服务器。

#### 2. 环境配置
- **JDK**: 11或以上
- **Maven**: 3.6或以上
- **MySQL**: 5.7或以上
- **Tomcat**: 9.0或以上
- **IDE**: IntelliJ IDEA 2024.1.2

#### 3. 数据库配置
1. 创建数据库：
    ```sql
    CREATE DATABASE schedule_system;
    ```
2. 创建用户表：
    ```sql
    CREATE TABLE sys_user (
        uid INT AUTO_INCREMENT PRIMARY KEY,
        username VARCHAR(50) NOT NULL,
        user_pwd VARCHAR(100) NOT NULL
    );
    ```

#### 4. 项目配置
1. 在`src/main/resources`目录下创建`application.properties`文件，配置数据库连接信息：
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/schedule_system
    spring.datasource.username=root
    spring.datasource.password=yourpassword
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    ```

#### 5. 项目运行
1. 使用Maven构建项目：
    ```bash
    mvn clean install
    ```
2. 部署到Tomcat：
    - 将生成的`war`文件复制到Tomcat的`webapps`目录下。
    - 启动Tomcat服务器。

#### 6. 功能说明
1. **用户注册**：
    - 访问`/register`页面，输入用户名和密码进行注册。
    - 密码必须是8-16位的数字、大写字母、小写字母和合法特殊符号中任选三种的组合。
2. **用户登录**：
    - 访问`/login`页面，输入用户名和密码进行登录。
3. **文件上传**：
    - 登录成功后，访问`/upload`页面上传文件。

#### 7. 代码结构
- `src/com/atguigu/schedule/dao`: 数据访问层
- `src/com/atguigu/schedule/service`: 业务逻辑层
- `src/com/atguigu/schedule/controller`: 控制器层
- `src/com/atguigu/schedule/util`: 工具类

#### 8. 常见问题
1. **登录失败时无法重新转回`login.html`**：
    - 确认`login.html`文件存在于正确的路径。
    - 确保`ViewBaseServlet`中的前缀和后缀设置正确。
    - 检查`web.xml`中的配置，确保前缀和后缀设置正确。

2. **密码格式校验**：
    - 在注册页面每次输入密码后，只要鼠标点击输入框以外就自动检测并返回当前密码格式核对情况。

#### 9. 联系方式
如有任何问题，请联系项目维护者：`2876587146@qq.com`

