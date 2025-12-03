# API管理系统使用说明

## 项目概述

本项目是一个基于若依框架的API管理系统，实现了API分组管理、API信息管理和API调用日志等功能。

## 技术栈

- 后端：Spring Boot 3.x + Spring Security + MyBatis-Plus 3.5+
- 前端：Vue 3 + Element Plus
- 数据库：MySQL 8.0
- 其他：Hutool 5.x、Lombok、Knife4j、Redis

## 项目结构

```
project3/
├── backend/                    # 后端项目
│   ├── api-admin/              # 管理模块
│   ├── api-common/             # 公共模块
│   ├── api-framework/          # 框架模块
│   ├── api-system/            # 系统模块
│   └── api-tool/              # 工具模块
├── frontend/                  # 前端项目
│   ├── src/
│   │   ├── api/              # API接口
│   │   ├── assets/           # 静态资源
│   │   ├── components/       # 公共组件
│   │   ├── layout/           # 布局组件
│   │   ├── router/           # 路由配置
│   │   ├── store/            # 状态管理
│   │   ├── utils/            # 工具类
│   │   └── views/            # 页面组件
└── docs/                     # 文档
    └── sql/                  # SQL脚本
```

## 部署说明

### 1. 数据库初始化

1. 创建MySQL数据库
2. 执行 `docs/sql/api_manage_tables.sql` 创建API管理相关表
3. 执行 `docs/sql/api_menu_permissions.sql` 初始化菜单权限

### 2. 后端部署

1. 确保JDK 17环境
2. 修改 `application.yml` 中的数据库连接配置
3. 运行 `backend/pom.xml` 所在目录，执行：
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

### 3. 前端部署

1. 确保Node.js环境
2. 在 `frontend/` 目录下执行：
   ```bash
   npm install
   npm run dev
   ```

## 功能说明

### 1. API分组管理

- 支持树形结构的API分组管理
- 支持分组的增删改查操作
- 支持分组状态控制

### 2. API信息管理

- 支持API信息的增删改查操作
- 支持API分组关联
- 支持API请求方法、路径、描述等信息管理
- 支持API认证控制

### 3. API调用日志

- 记录API调用详细信息
- 支持日志查询、删除、导出
- 支持日志清空操作
- 支持响应时间分析

## 接口说明

### API分组管理

- `GET /api/manage/group/list` - 查询API分组列表
- `GET /api/manage/group/{id}` - 查询API分组详情
- `POST /api/manage/group` - 新增API分组
- `PUT /api/manage/group` - 修改API分组
- `DELETE /api/manage/group/{ids}` - 删除API分组

### API信息管理

- `GET /api/manage/info/list` - 查询API信息列表
- `GET /api/manage/info/{id}` - 查询API信息详情
- `POST /api/manage/info` - 新增API信息
- `PUT /api/manage/info` - 修改API信息
- `DELETE /api/manage/info/{ids}` - 删除API信息

### API调用日志

- `GET /api/manage/log/list` - 查询API调用日志列表
- `GET /api/manage/log/{id}` - 查询API调用日志详情
- `DELETE /api/manage/log/{ids}` - 删除API调用日志
- `DELETE /api/manage/log/clean` - 清空API调用日志

## 权限说明

系统使用若依框架的权限管理机制，主要权限标识如下：

- `api:group:list` - API分组查询
- `api:group:add` - API分组新增
- `api:group:edit` - API分组修改
- `api:group:remove` - API分组删除
- `api:group:export` - API分组导出

- `api:info:list` - API信息查询
- `api:info:add` - API信息新增
- `api:info:edit` - API信息修改
- `api:info:remove` - API信息删除
- `api:info:export` - API信息导出

- `api:log:list` - API调用日志查询
- `api:log:remove` - API调用日志删除
- `api:log:export` - API调用日志导出
- `api:log:clean` - API调用日志清空

## 注意事项

1. 确保数据库字符集为utf8mb4
2. 确保Redis服务正常运行（用于缓存和会话管理）
3. 首次部署需要执行SQL脚本初始化数据
4. 建议在生产环境中修改默认密码和密钥

## 扩展开发

如需扩展功能，请遵循以下规范：

1. 后端代码遵循若依框架的目录结构和命名规范
2. 前端代码遵循Vue 3 + Element Plus的开发规范
3. 新增功能需要添加相应的权限控制
4. 接口文档使用Knife4j自动生成

## 联系方式

如有问题或建议，请联系开发团队。