-- ----------------------------
-- API管理相关表设计
-- ----------------------------

-- ----------------------------
-- 1、API分组表
-- ----------------------------
DROP TABLE IF EXISTS `api_group`;
CREATE TABLE `api_group` (
  `group_id` bigint NOT NULL AUTO_INCREMENT COMMENT '分组ID',
  `group_name` varchar(100) NOT NULL COMMENT '分组名称',
  `group_code` varchar(50) NOT NULL COMMENT '分组编码',
  `parent_id` bigint DEFAULT 0 COMMENT '父分组ID',
  `order_num` int DEFAULT 0 COMMENT '显示顺序',
  `description` varchar(500) DEFAULT NULL COMMENT '分组描述',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`group_id`),
  UNIQUE KEY `uk_group_code` (`group_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='API分组表';

-- ----------------------------
-- 2、API信息表
-- ----------------------------
DROP TABLE IF EXISTS `api_info`;
CREATE TABLE `api_info` (
  `api_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'API ID',
  `api_name` varchar(100) NOT NULL COMMENT 'API名称',
  `api_path` varchar(200) NOT NULL COMMENT 'API路径',
  `api_method` varchar(10) NOT NULL COMMENT '请求方法(GET/POST/PUT/DELETE等)',
  `group_id` bigint NOT NULL COMMENT '所属分组ID',
  `api_version` varchar(20) DEFAULT 'v1.0' COMMENT 'API版本',
  `api_description` varchar(500) DEFAULT NULL COMMENT 'API描述',
  `request_example` text COMMENT '请求示例',
  `response_example` text COMMENT '响应示例',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用 2调试中）',
  `is_public` char(1) DEFAULT '0' COMMENT '是否公开（0否 1是）',
  `call_count` bigint DEFAULT 0 COMMENT '调用次数',
  `success_count` bigint DEFAULT 0 COMMENT '成功次数',
  `error_count` bigint DEFAULT 0 COMMENT '错误次数',
  `avg_response_time` decimal(10,2) DEFAULT 0.00 COMMENT '平均响应时间(毫秒)',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`api_id`),
  KEY `idx_api_path_method` (`api_path`, `api_method`),
  KEY `idx_group_id` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='API信息表';

-- ----------------------------
-- 3、API参数表
-- ----------------------------
DROP TABLE IF EXISTS `api_param`;
CREATE TABLE `api_param` (
  `param_id` bigint NOT NULL AUTO_INCREMENT COMMENT '参数ID',
  `api_id` bigint NOT NULL COMMENT 'API ID',
  `param_name` varchar(100) NOT NULL COMMENT '参数名称',
  `param_type` varchar(20) NOT NULL COMMENT '参数类型(header/path/query/body)',
  `data_type` varchar(20) NOT NULL COMMENT '数据类型(string/number/boolean/object/array)',
  `required` char(1) DEFAULT '0' COMMENT '是否必填（0否 1是）',
  `default_value` varchar(200) DEFAULT NULL COMMENT '默认值',
  `param_description` varchar(500) DEFAULT NULL COMMENT '参数描述',
  `order_num` int DEFAULT 0 COMMENT '显示顺序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`param_id`),
  KEY `idx_api_id` (`api_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='API参数表';

-- ----------------------------
-- 4、API权限表
-- ----------------------------
DROP TABLE IF EXISTS `api_permission`;
CREATE TABLE `api_permission` (
  `permission_id` bigint NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `api_id` bigint NOT NULL COMMENT 'API ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `permission_type` char(1) DEFAULT '0' COMMENT '权限类型（0允许 1禁止）',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`permission_id`),
  UNIQUE KEY `uk_api_role` (`api_id`, `role_id`),
  KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='API权限表';

-- ----------------------------
-- 5、API调用日志表
-- ----------------------------
DROP TABLE IF EXISTS `api_call_log`;
CREATE TABLE `api_call_log` (
  `log_id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `api_id` bigint NOT NULL COMMENT 'API ID',
  `user_id` bigint DEFAULT NULL COMMENT '调用用户ID',
  `user_name` varchar(50) DEFAULT '' COMMENT '调用用户名',
  `request_ip` varchar(128) DEFAULT '' COMMENT '请求IP',
  `request_method` varchar(10) DEFAULT '' COMMENT '请求方法',
  `request_url` varchar(500) DEFAULT '' COMMENT '请求URL',
  `request_params` text COMMENT '请求参数',
  `response_status` int DEFAULT 200 COMMENT '响应状态码',
  `response_data` text COMMENT '响应数据',
  `response_time` decimal(10,2) DEFAULT 0.00 COMMENT '响应时间(毫秒)',
  `is_success` char(1) DEFAULT '0' COMMENT '是否成功（0失败 1成功）',
  `error_message` text COMMENT '错误信息',
  `call_time` datetime DEFAULT NULL COMMENT '调用时间',
  PRIMARY KEY (`log_id`),
  KEY `idx_api_id` (`api_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_call_time` (`call_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='API调用日志表';

-- ----------------------------
-- 初始化API分组数据
-- ----------------------------
INSERT INTO `api_group` VALUES (1, '用户管理', 'user', 0, 1, '用户管理相关API', '0', '0', 1, '2023-01-01 00:00:00', NULL, '2023-01-01 00:00:00', '用户管理API分组');
INSERT INTO `api_group` VALUES (2, '系统管理', 'system', 0, 2, '系统管理相关API', '0', '0', 1, '2023-01-01 00:00:00', NULL, '2023-01-01 00:00:00', '系统管理API分组');
INSERT INTO `api_group` VALUES (3, 'API管理', 'api', 0, 3, 'API管理相关API', '0', '0', 1, '2023-01-01 00:00:00', NULL, '2023-01-01 00:00:00', 'API管理API分组');

-- ----------------------------
-- 初始化API信息数据
-- ----------------------------
INSERT INTO `api_info` VALUES (1, '用户列表查询', '/api/manage/user/list', 'GET', 1, 'v1.0', '查询用户列表信息', NULL, NULL, '0', '0', 0, 0, 0, 0.00, '0', 1, '2023-01-01 00:00:00', NULL, '2023-01-01 00:00:00', '用户列表查询API');
INSERT INTO `api_info` VALUES (2, '用户新增', '/api/manage/user', 'POST', 1, 'v1.0', '新增用户信息', NULL, NULL, '0', '0', 0, 0, 0, 0.00, '0', 1, '2023-01-01 00:00:00', NULL, '2023-01-01 00:00:00', '用户新增API');
INSERT INTO `api_info` VALUES (3, '用户修改', '/api/manage/user', 'PUT', 1, 'v1.0', '修改用户信息', NULL, NULL, '0', '0', 0, 0, 0, 0.00, '0', 1, '2023-01-01 00:00:00', NULL, '2023-01-01 00:00:00', '用户修改API');
INSERT INTO `api_info` VALUES (4, '用户删除', '/api/manage/user/{id}', 'DELETE', 1, 'v1.0', '删除用户信息', NULL, NULL, '0', '0', 0, 0, 0, 0.00, '0', 1, '2023-01-01 00:00:00', NULL, '2023-01-01 00:00:00', '用户删除API');

-- ----------------------------
-- 初始化API参数数据
-- ----------------------------
INSERT INTO `api_param` VALUES (1, 1, 'pageNum', 'query', 'number', '0', '1', '页码', 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00');
INSERT INTO `api_param` VALUES (2, 1, 'pageSize', 'query', 'number', '0', '10', '每页数量', 2, '2023-01-01 00:00:00', '2023-01-01 00:00:00');
INSERT INTO `api_param` VALUES (3, 1, 'userName', 'query', 'string', '0', NULL, '用户名', 3, '2023-01-01 00:00:00', '2023-01-01 00:00:00');
INSERT INTO `api_param` VALUES (4, 2, 'userName', 'body', 'string', '1', NULL, '用户名', 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00');
INSERT INTO `api_param` VALUES (5, 2, 'nickName', 'body', 'string', '1', NULL, '昵称', 2, '2023-01-01 00:00:00', '2023-01-01 00:00:00');
INSERT INTO `api_param` VALUES (6, 2, 'email', 'body', 'string', '0', NULL, '邮箱', 3, '2023-01-01 00:00:00', '2023-01-01 00:00:00');
INSERT INTO `api_param` VALUES (7, 3, 'userId', 'body', 'number', '1', NULL, '用户ID', 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00');
INSERT INTO `api_param` VALUES (8, 3, 'userName', 'body', 'string', '0', NULL, '用户名', 2, '2023-01-01 00:00:00', '2023-01-01 00:00:00');
INSERT INTO `api_param` VALUES (9, 4, 'id', 'path', 'number', '1', NULL, '用户ID', 1, '2023-01-01 00:00:00', '2023-01-01 00:00:00');

-- ----------------------------
-- 初始化API权限数据
-- ----------------------------
INSERT INTO `api_permission` VALUES (1, 1, 1, '0', 1, '2023-01-01 00:00:00', NULL, '2023-01-01 00:00:00');
INSERT INTO `api_permission` VALUES (2, 2, 1, '0', 1, '2023-01-01 00:00:00', NULL, '2023-01-01 00:00:00');
INSERT INTO `api_permission` VALUES (3, 3, 1, '0', 1, '2023-01-01 00:00:00', NULL, '2023-01-01 00:00:00');
INSERT INTO `api_permission` VALUES (4, 4, 1, '0', 1, '2023-01-01 00:00:00', NULL, '2023-01-01 00:00:00');

-- ----------------------------
-- 添加菜单权限数据
-- ----------------------------
-- API管理菜单
INSERT INTO `sys_menu` VALUES (2000, 'API管理', 0, 4, 'api', NULL, '', 1, 0, 'M', '0', '0', '', 'api', 'admin', '2023-01-01 00:00:00', '', NULL, 'API管理目录');

-- API分组管理
INSERT INTO `sys_menu` VALUES (2001, 'API分组管理', 2000, 1, 'apiGroup', 'api/group/index', '', 1, 0, 'C', '0', '0', 'api:group:list', 'tree', 'admin', '2023-01-01 00:00:00', '', NULL, 'API分组管理菜单');
INSERT INTO `sys_menu` VALUES (2002, 'API分组查询', 2001, 1, '', '', '', 1, 0, 'F', '0', '0', 'api:group:query', '#', 'admin', '2023-01-01 00:00:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2003, 'API分组新增', 2001, 2, '', '', '', 1, 0, 'F', '0', '0', 'api:group:add', '#', 'admin', '2023-01-01 00:00:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2004, 'API分组修改', 2001, 3, '', '', '', 1, 0, 'F', '0', '0', 'api:group:edit', '#', 'admin', '2023-01-01 00:00:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2005, 'API分组删除', 2001, 4, '', '', '', 1, 0, 'F', '0', '0', 'api:group:remove', '#', 'admin', '2023-01-01 00:00:00', '', NULL, '');

-- API信息管理
INSERT INTO `sys_menu` VALUES (2010, 'API信息管理', 2000, 2, 'apiInfo', 'api/info/index', '', 1, 0, 'C', '0', '0', 'api:info:list', 'documentation', 'admin', '2023-01-01 00:00:00', '', NULL, 'API信息管理菜单');
INSERT INTO `sys_menu` VALUES (2011, 'API信息查询', 2010, 1, '', '', '', 1, 0, 'F', '0', '0', 'api:info:query', '#', 'admin', '2023-01-01 00:00:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2012, 'API信息新增', 2010, 2, '', '', '', 1, 0, 'F', '0', '0', 'api:info:add', '#', 'admin', '2023-01-01 00:00:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2013, 'API信息修改', 2010, 3, '', '', '', 1, 0, 'F', '0', '0', 'api:info:edit', '#', 'admin', '2023-01-01 00:00:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2014, 'API信息删除', 2010, 4, '', '', '', 1, 0, 'F', '0', '0', 'api:info:remove', '#', 'admin', '2023-01-01 00:00:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2015, 'API信息导出', 2010, 5, '', '', '', 1, 0, 'F', '0', '0', 'api:info:export', '#', 'admin', '2023-01-01 00:00:00', '', NULL, '');

-- API调用日志
INSERT INTO `sys_menu` VALUES (2020, 'API调用日志', 2000, 3, 'apiLog', 'api/log/index', '', 1, 0, 'C', '0', '0', 'api:log:list', 'log', 'admin', '2023-01-01 00:00:00', '', NULL, 'API调用日志菜单');
INSERT INTO `sys_menu` VALUES (2021, 'API日志查询', 2020, 1, '', '', '', 1, 0, 'F', '0', '0', 'api:log:query', '#', 'admin', '2023-01-01 00:00:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2022, 'API日志导出', 2020, 2, '', '', '', 1, 0, 'F', '0', '0', 'api:log:export', '#', 'admin', '2023-01-01 00:00:00', '', NULL, '');

-- 给超级管理员分配API管理菜单权限
INSERT INTO `sys_role_menu` VALUES (1, 2000);
INSERT INTO `sys_role_menu` VALUES (1, 2001);
INSERT INTO `sys_role_menu` VALUES (1, 2002);
INSERT INTO `sys_role_menu` VALUES (1, 2003);
INSERT INTO `sys_role_menu` VALUES (1, 2004);
INSERT INTO `sys_role_menu` VALUES (1, 2005);
INSERT INTO `sys_role_menu` VALUES (1, 2010);
INSERT INTO `sys_role_menu` VALUES (1, 2011);
INSERT INTO `sys_role_menu` VALUES (1, 2012);
INSERT INTO `sys_role_menu` VALUES (1, 2013);
INSERT INTO `sys_role_menu` VALUES (1, 2014);
INSERT INTO `sys_role_menu` VALUES (1, 2015);
INSERT INTO `sys_role_menu` VALUES (1, 2020);
INSERT INTO `sys_role_menu` VALUES (1, 2021);
INSERT INTO `sys_role_menu` VALUES (1, 2022);