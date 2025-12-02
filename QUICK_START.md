# API管理后台平台 - 快速启动指南

## 环境要求

### 基础环境
- **JDK 17+** - 后端运行环境
- **Node.js 16+** - 前端运行环境
- **MySQL 8.0+** - 数据库
- **Redis 6.0+** - 缓存数据库
- **Maven 3.6+** - 后端构建工具

### 推荐开发工具
- **IDE**: IntelliJ IDEA 2023+
- **数据库工具**: Navicat / DBeaver
- **API测试**: Postman / Apifox
- **版本控制**: Git

## 快速部署步骤

### 1. 数据库初始化

1. 创建数据库：
```sql
CREATE DATABASE api_admin_platform DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

2. 导入数据表结构和初始数据：
```bash
mysql -u root -p api_admin_platform < docs/sql/api_admin_platform.sql
```

### 2. 后端配置

1. 修改数据库配置：
编辑 `backend/api-admin/src/main/resources/application-druid.yml`
```yaml
spring:
  datasource:
    druid:
      master:
        url: jdbc:mysql://10.174.128.64:3306/api_admin_platform?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8
        username: root
        password: your_password
```

2. 修改Redis配置：
编辑 `backend/api-admin/src/main/resources/application.yml`
```yaml
spring:
  redis:
    host: localhost
    port: 6379
    password: 
    database: 0
```

3. 启动后端服务：
```bash
cd backend
mvn clean install
cd api-admin
mvn spring-boot:run
```

后端服务启动成功后，访问地址：http://localhost:8080

### 3. 前端配置

1. 安装依赖：
```bash
cd frontend
npm install
```

2. 修改API地址（可选）：
编辑 `frontend/src/utils/request.js`
```javascript
const service = axios.create({
  baseURL: 'http://localhost:8080', // 后端接口地址
  timeout: 10000
})
```

3. 启动前端开发服务器：
```bash
npm run dev
```

前端服务启动成功后，访问地址：http://localhost:80

### 4. 登录系统

1. 打开浏览器访问：http://localhost:80
2. 使用默认账号登录：
   - 用户名：`admin`
   - 密码：`admin123`

## 项目结构说明

### 后端模块结构
```
backend/
├── api-admin/          # 启动模块
│   ├── src/main/java/
│   └── src/main/resources/
├── api-common/         # 通用模块
│   └── src/main/java/com/api/common/
├── api-framework/      # 框架模块
│   └── src/main/java/com/api/framework/
├── api-system/         # 系统模块
│   └── src/main/java/com/api/system/
└── api-tool/           # 工具模块
    └── src/main/java/com/api/tool/
```

### 前端模块结构
```
frontend/
├── public/             # 静态资源
├── src/
│   ├── api/            # API接口
│   ├── assets/         # 资源文件
│   ├── components/     # 公共组件
│   ├── layout/         # 布局组件
│   ├── router/         # 路由配置
│   ├── store/          # 状态管理
│   ├── utils/          # 工具函数
│   └── views/          # 页面视图
├── package.json
└── vite.config.js
```

## 常见问题解决

### 1. 数据库连接失败
- 检查MySQL服务是否启动
- 确认数据库用户名和密码正确
- 检查防火墙设置

### 2. Redis连接失败
- 检查Redis服务是否启动
- 确认Redis配置信息正确
- 检查网络连接

### 3. 前端启动失败
- 清除node_modules重新安装：`rm -rf node_modules && npm install`
- 检查Node.js版本是否符合要求
- 检查端口是否被占用

### 4. 后端启动失败
- 检查JDK版本是否符合要求
- 确认Maven依赖下载完整
- 检查配置文件格式是否正确

## 开发调试

### 1. 后端调试
- 在IDE中设置断点
- 使用Debug模式启动
- 查看控制台日志

### 2. 前端调试
- 使用浏览器开发者工具
- 查看Network请求
- 使用Vue DevTools扩展

### 3. API文档
启动后端服务后，访问Knife4j文档：
http://localhost:8080/doc.html

## 生产环境部署

### 1. 后端打包
```bash
cd backend/api-admin
mvn clean package
java -jar target/api-admin.jar
```

### 2. 前端打包
```bash
cd frontend
npm run build
# 将dist目录部署到Web服务器
```

### 3. Nginx配置示例
```nginx
server {
    listen 80;
    server_name your-domain.com;
    
    location / {
        root /path/to/frontend/dist;
        try_files $uri $uri/ /index.html;
    }
    
    location /api/ {
        proxy_pass http://localhost:8080/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

## 技术支持

如遇到问题，请按以下步骤排查：

1. 检查环境是否符合要求
2. 查看日志文件获取错误信息
3. 参考项目文档和常见问题
4. 提交Issue到项目仓库

---

**API管理后台平台** - 现代化企业级管理解决方案