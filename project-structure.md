# API管理后台平台 - 项目目录结构

## 整体项目结构

```
api-admin-platform/
├── backend/                          # 后端项目
│   ├── api-admin/                    # 管理模块（启动模块）
│   │   ├── src/main/java/
│   │   │   └── com/api/admin/
│   │   │       ├── ApiAdminApplication.java
│   │   │       └── controller/       # 管理端控制器
│   │   └── src/main/resources/
│   │       ├── application.yml       # 配置文件
│   │       ├── logback-spring.xml    # 日志配置
│   │       └── mapper/               # MyBatis映射文件
│   │
│   ├── api-common/                   # 通用模块
│   │   ├── src/main/java/
│   │   │   └── com/api/common/
│   │   │       ├── annotation/       # 自定义注解
│   │   │       ├── config/           # 配置类
│   │   │       ├── constant/         # 常量类
│   │   │       ├── core/             # 核心组件
│   │   │       │   ├── domain/       # 通用实体基类
│   │   │       │   ├── page/         # 分页组件
│   │   │       │   └── text/         # 文本处理
│   │   │       ├── enums/            # 枚举类
│   │   │       ├── exception/        # 异常类
│   │   │       ├── filter/           # 过滤器
│   │   │       ├── utils/            # 工具类
│   │   │       └── xss/              # XSS过滤
│   │   └── src/main/resources/
│   │
│   ├── api-framework/                # 框架模块
│   │   ├── src/main/java/
│   │   │   └── com/api/framework/
│   │   │       ├── aspectj/          # AOP切面
│   │   │       ├── config/           # 配置类
│   │   │       ├── datasource/       # 数据源配置
│   │   │       ├── intercepter/      # 拦截器
│   │   │       ├── jwt/              # JWT相关
│   │   │       ├── manager/          # 异步管理
│   │   │       ├── redis/            # Redis相关
│   │   │       ├── security/         # 安全框架
│   │   │       └── web/              # Web配置
│   │   └── src/main/resources/
│   │
│   ├── api-system/                   # 系统模块
│   │   ├── src/main/java/
│   │   │   └── com/api/system/
│   │   │       ├── controller/       # 系统控制器
│   │   │       ├── domain/           # 系统实体
│   │   │       ├── mapper/           # 系统映射器
│   │   │       └── service/          # 系统服务
│   │   └── src/main/resources/
│   │       └── mapper/               # MyBatis映射文件
│   │
│   ├── api-tool/                     # 工具模块
│   │   ├── src/main/java/
│   │   │   └── com/api/tool/
│   │   │       ├── controller/       # 工具控制器
│   │   │       ├── domain/           # 工具实体
│   │   │       ├── service/          # 工具服务
│   │   │       └── util/             # 工具类
│   │   │           ├── excel/        # Excel导入导出
│   │   │           └── file/         # 文件上传下载
│   │   └── src/main/resources/
│   │
│   └── pom.xml                       # 父级POM文件
│
├── frontend/                         # 前端项目
│   ├── public/                       # 静态资源
│   │   ├── index.html
│   │   └── favicon.ico
│   ├── src/
│   │   ├── api/                      # API接口
│   │   │   ├── login.js
│   │   │   ├── menu.js
│   │   │   ├── system/               # 系统模块API
│   │   │   └── monitor/              # 监控模块API
│   │   ├── assets/                   # 资源文件
│   │   │   ├── images/
│   │   │   └── styles/
│   │   ├── components/               # 公共组件
│   │   │   ├── Breadcrumb/
│   │   │   ├── Hamburger/
│   │   │   ├── Pagination/
│   │   │   └── RightToolbar/
│   │   ├── directive/                # 自定义指令
│   │   ├── layout/                   # 布局组件
│   │   │   ├── components/
│   │   │   ├── mixin/
│   │   │   └── index.vue
│   │   ├── plugins/                  # 插件
│   │   ├── router/                   # 路由配置
│   │   │   └── index.js
│   │   ├── store/                    # 状态管理
│   │   │   ├── modules/
│   │   │   └── index.js
│   │   ├── utils/                    # 工具类
│   │   │   ├── auth.js
│   │   │   ├── request.js
│   │   │   ├── validate.js
│   │   │   └── permission.js
│   │   ├── views/                    # 页面视图
│   │   │   ├── login/                # 登录页面
│   │   │   ├── register/             # 注册页面
│   │   │   ├── system/               # 系统管理
│   │   │   │   ├── user/             # 用户管理
│   │   │   │   ├── role/             # 角色管理
│   │   │   │   ├── menu/             # 菜单管理
│   │   │   │   ├── dept/             # 部门管理
│   │   │   │   ├── dict/             # 字典管理
│   │   │   │   └── config/           # 参数设置
│   │   │   ├── monitor/              # 系统监控
│   │   │   │   ├── operlog/          # 操作日志
│   │   │   │   ├── logininfor/       # 登录日志
│   │   │   │   └── server/           # 服务监控
│   │   │   ├── tool/                 # 系统工具
│   │   │   │   ├── excel/            # Excel导入导出
│   │   │   │   └── upload/           # 文件上传
│   │   │   └── dashboard/            # 控制台
│   │   ├── App.vue                   # 根组件
│   │   └── main.js                   # 入口文件
│   ├── package.json                  # 依赖配置
│   └── vue.config.js                 # 构建配置
│
├── docs/                             # 项目文档
│   ├── sql/                          # 数据库脚本
│   │   └── api_platform.sql          # 数据库表结构
│   ├── api/                          # API文档
│   └── README.md                     # 项目说明
│
├── docker/                           # Docker配置
│   ├── docker-compose.yml
│   ├── mysql/
│   └── redis/
│
└── README.md                         # 项目说明
```

## 后端模块详细说明

### 1. api-admin（管理模块）
- 项目启动入口
- 管理端控制器
- 全局配置文件

### 2. api-common（通用模块）
- 通用工具类
- 常量定义
- 异常处理
- 数据验证
- XSS过滤

### 3. api-framework（框架模块）
- Spring Security配置
- JWT认证处理
- Redis缓存配置
- 数据源配置
- AOP切面处理

### 4. api-system（系统模块）
- 用户管理
- 角色管理
- 菜单管理
- 部门管理
- 字典管理
- 参数配置
- 通知公告
- 操作日志
- 登录日志

### 5. api-tool（工具模块）
- 文件上传下载
- Excel导入导出
- 数据字典工具
- 系统监控工具

## 前端模块详细说明

### 1. 核心技术栈
- Vue 3.x
- Element Plus
- Vue Router
- Vuex
- Axios

### 2. 目录结构说明
- `api/`: API接口定义
- `assets/`: 静态资源
- `components/`: 公共组件
- `layout/`: 布局组件
- `router/`: 路由配置
- `store/`: 状态管理
- `utils/`: 工具函数
- `views/`: 页面视图

## 技术架构分层

### 1. 表现层（Controller）
- 接收前端请求
- 参数校验
- 调用业务层
- 返回响应结果

### 2. 业务层（Service）
- 业务逻辑处理
- 事务管理
- 调用数据访问层

### 3. 数据访问层（Mapper）
- 数据库操作
- SQL映射
- 数据持久化

### 4. 实体层（Domain/Entity）
- 数据模型定义
- 数据传输对象
- 值对象

## 安全架构

### 1. 认证机制
- JWT Token认证
- 登录状态管理
- 会话超时处理

### 2. 权限控制
- RBAC权限模型
- 菜单权限
- 按钮权限
- 数据权限

### 3. 安全防护
- XSS过滤
- SQL注入防护
- CSRF防护
- 接口限流

## 缓存架构

### 1. Redis缓存
- 用户信息缓存
- 字典数据缓存
- 权限信息缓存
- 会话信息缓存

### 2. 缓存策略
- 热点数据缓存
- 缓存更新策略
- 缓存穿透防护
- 缓存雪崩防护

## 日志架构

### 1. 日志分类
- 操作日志
- 登录日志
- 错误日志
- 业务日志

### 2. 日志处理
- 异步日志记录
- 日志文件分割
- 日志级别控制
- 日志格式化