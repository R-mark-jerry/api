-- ----------------------------
-- 1、存储所有部门信息
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `parent_id` bigint(20) DEFAULT 0 COMMENT '父部门ID',
  `ancestors` varchar(50) DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) DEFAULT '' COMMENT '部门名称',
  `order_num` int(4) DEFAULT 0 COMMENT '显示顺序',
  `leader` varchar(20) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `status` char(1) DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

-- ----------------------------
-- 2、存储所有菜单信息
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int(4) DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) DEFAULT NULL COMMENT '路由参数',
  `is_frame` int(1) DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `is_cache` int(1) DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` int(1) DEFAULT 0 COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2000 DEFAULT CHARSET=utf8mb4 COMMENT='菜单权限表';

-- ----------------------------
-- 3、存储所有角色信息
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(4) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定义数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) DEFAULT 1 COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) DEFAULT 1 COMMENT '部门树选择项是否关联显示',
  `status` char(1) NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='角色信息表';

-- ----------------------------
-- 4、存储用户和角色关联信息
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户和角色关联表';

-- ----------------------------
-- 5、存储角色和菜单关联信息
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色和菜单关联表';

-- ----------------------------
-- 6、存储角色和部门关联信息
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`,`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色和部门关联表';

-- ----------------------------
-- 7、存储所有用户信息
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(30) NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) DEFAULT '' COMMENT '手机号码',
  `sex` char(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) DEFAULT '' COMMENT '密码',
  `status` char(1) DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

-- ----------------------------
-- 8、存储字典类型信息
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type` (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`),
  UNIQUE KEY `dict_type` (`dict_type`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='字典类型表';

-- ----------------------------
-- 9、存储字典数据信息
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data` (
  `dict_code` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(4) DEFAULT 0 COMMENT '字典排序',
  `dict_label` varchar(100) DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='字典数据表';

-- ----------------------------
-- 10、存储参数配置信息
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `config_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='参数配置表';

-- ----------------------------
-- 11、存储操作日志记录
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log` (
  `oper_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) DEFAULT '' COMMENT '模块标题',
  `business_type` int(2) DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) DEFAULT '' COMMENT '请求方式',
  `operator_type` int(1) DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) DEFAULT '' COMMENT '返回参数',
  `status` int(1) DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`oper_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='操作日志记录';

-- ----------------------------
-- 12、存储登录日志记录
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor` (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) DEFAULT '' COMMENT '操作系统',
  `status` char(1) DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) DEFAULT '' COMMENT '提示消息',
  `login_time` datetime DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='系统访问记录';

-- ----------------------------
-- 初始化-部门表数据
-- ----------------------------
insert into sys_dept values(100,  0,   '0',          '总公司',   0, 'admin', '15888888888', 'admin@163.com', '0', '0', 'admin', '2023-01-01', '', NULL);
insert into sys_dept values(101,  100, '0,100',      '深圳总公司', 1, 'admin', '15888888888', 'admin@163.com', '0', '0', 'admin', '2023-01-01', '', NULL);
insert into sys_dept values(102,  100, '0,100',      '长沙分公司', 2, 'admin', '15888888888', 'admin@163.com', '0', '0', 'admin', '2023-01-01', '', NULL);
insert into sys_dept values(103,  101, '0,100,101',  '研发部门',   1, 'admin', '15888888888', 'admin@163.com', '0', '0', 'admin', '2023-01-01', '', NULL);
insert into sys_dept values(104,  101, '0,100,101',  '市场部门',   2, 'admin', '15888888888', 'admin@163.com', '0', '0', 'admin', '2023-01-01', '', NULL);
insert into sys_dept values(105,  101, '0,100,101',  '测试部门',   3, 'admin', '15888888888', 'admin@163.com', '0', '0', 'admin', '2023-01-01', '', NULL);

-- ----------------------------
-- 初始化-菜单表数据
-- ----------------------------
-- 一级菜单
insert into sys_menu values(1, '系统管理', 0, 1, 'system',           null, '', 1, 0, 'M', '0', '0', '', 'system',           'admin', '2023-01-01', '', null, '系统管理目录');
insert into sys_menu values(2, '系统监控', 0, 2, 'monitor',          null, '', 1, 0, 'M', '0', '0', '', 'monitor',          'admin', '2023-01-01', '', null, '系统监控目录');
insert into sys_menu values(3, '系统工具', 0, 3, 'tool',             null, '', 1, 0, 'M', '0', '0', '', 'tool',             'admin', '2023-01-01', '', null, '系统工具目录');

-- 二级菜单
insert into sys_menu values(100, '用户管理', 1, 1, 'user',       'system/user/index',        '', 1, 0, 'C', '0', '0', 'system:user:list',        'user',          'admin', '2023-01-01', '', null, '用户管理菜单');
insert into sys_menu values(101, '角色管理', 1, 2, 'role',       'system/role/index',        '', 1, 0, 'C', '0', '0', 'system:role:list',        'peoples',       'admin', '2023-01-01', '', null, '角色管理菜单');
insert into sys_menu values(102, '菜单管理', 1, 3, 'menu',       'system/menu/index',        '', 1, 0, 'C', '0', '0', 'system:menu:list',        'tree-table',    'admin', '2023-01-01', '', null, '菜单管理菜单');
insert into sys_menu values(103, '部门管理', 1, 4, 'dept',       'system/dept/index',        '', 1, 0, 'C', '0', '0', 'system:dept:list',        'tree',          'admin', '2023-01-01', '', null, '部门管理菜单');
insert into sys_menu values(104, '字典管理', 1, 5, 'dict',       'system/dict/index',        '', 1, 0, 'C', '0', '0', 'system:dict:list',        'dict',          'admin', '2023-01-01', '', null, '字典管理菜单');
insert into sys_menu values(105, '参数设置', 1, 6, 'config',     'system/config/index',       '', 1, 0, 'C', '0', '0', 'system:config:list',      'edit',          'admin', '2023-01-01', '', null, '参数设置菜单');

-- 三级菜单
insert into sys_menu values(1001, '用户查询', 100, 1,  '', '', '', 1, 0, 'F', '0', '0', 'system:user:query',        '#', 'admin', '2023-01-01', '', null, '');
insert into sys_menu values(1002, '用户新增', 100, 2,  '', '', '', 1, 0, 'F', '0', '0', 'system:user:add',           '#', 'admin', '2023-01-01', '', null, '');
insert into sys_menu values(1003, '用户修改', 100, 3,  '', '', '', 1, 0, 'F', '0', '0', 'system:user:edit',          '#', 'admin', '2023-01-01', '', null, '');
insert into sys_menu values(1004, '用户删除', 100, 4,  '', '', '', 1, 0, 'F', '0', '0', 'system:user:remove',        '#', 'admin', '2023-01-01', '', null, '');
insert into sys_menu values(1005, '用户导出', 100, 5,  '', '', '', 1, 0, 'F', '0', '0', 'system:user:export',        '#', 'admin', '2023-01-01', '', null, '');
insert into sys_menu values(1006, '用户导入', 100, 6,  '', '', '', 1, 0, 'F', '0', '0', 'system:user:import',        '#', 'admin', '2023-01-01', '', null, '');
insert into sys_menu values(1007, '重置密码', 100, 7,  '', '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd',      '#', 'admin', '2023-01-01', '', null, '');

-- ----------------------------
-- 初始化-角色表数据
-- ----------------------------
insert into sys_role values(1, '超级管理员', 'admin',  1, '1', true, true, '0', '0', 'admin', '2023-01-01', '', null, '超级管理员');
insert into sys_role values(2, '普通角色', 'common', 2, '2', true, true, '0', '0', 'admin', '2023-01-01', '', null, '普通角色');

-- ----------------------------
-- 初始化-用户表数据
-- ----------------------------
insert into sys_user values(1,  'admin', '管理员', '00', 'admin@163.com', '15888888888', '1', '', '$2a$10$7JB720yubVSOfvVWbfXCOOxjTOQcQjmrJF1ZM4nAVccp/.rkMlDW', '0', '0', '127.0.0.1', '2023-01-01', 'admin', '2023-01-01', '', null, '管理员');
insert into sys_user values(2,  'ry',    '若依',   '00', 'ry@qq.com',          '15666666666', '1', '', '$2a$10$7JB720yubVSOfvVWbfXCOOxjTOQcQjmrJF1ZM4nAVccp/.rkMlDW', '0', '0', '127.0.0.1', '2023-01-01', 'admin', '2023-01-01', '', null, '测试员');

-- ----------------------------
-- 初始化-用户和角色关联表数据
-- ----------------------------
insert into sys_user_role values (1, 1);
insert into sys_user_role values (2, 2);

-- ----------------------------
-- 初始化-角色和菜单关联表数据
-- ----------------------------
-- 超级管理员拥有所有菜单权限
insert into sys_role_menu values (1, 1);
insert into sys_role_menu values (1, 2);
insert into sys_role_menu values (1, 3);
insert into sys_role_menu values (1, 100);
insert into sys_role_menu values (1, 101);
insert into sys_role_menu values (1, 102);
insert into sys_role_menu values (1, 103);
insert into sys_role_menu values (1, 104);
insert into sys_role_menu values (1, 105);
insert into sys_role_menu values (1, 1001);
insert into sys_role_menu values (1, 1002);
insert into sys_role_menu values (1, 1003);
insert into sys_role_menu values (1, 1004);
insert into sys_role_menu values (1, 1005);
insert into sys_role_menu values (1, 1006);
insert into sys_role_menu values (1, 1007);

-- 普通角色拥有部分菜单权限
insert into sys_role_menu values (2, 1);
insert into sys_role_menu values (2, 100);
insert into sys_role_menu values (2, 1001);
insert into sys_role_menu values (2, 1002);
insert into sys_role_menu values (2, 1003);
insert into sys_role_menu values (2, 1004);

-- ----------------------------
-- 初始化-字典类型数据
-- ----------------------------
insert into sys_dict_type values(1,  '用户性别', 'sys_user_sex',        '0', 'admin', '2023-01-01', '', null, '用户性别列表');
insert into sys_dict_type values(2,  '菜单状态', 'sys_show_hide',       '0', 'admin', '2023-01-01', '', null, '菜单状态列表');
insert into sys_dict_type values(3,  '系统开关', 'sys_normal_disable',  '0', 'admin', '2023-01-01', '', null, '系统开关列表');

-- ----------------------------
-- 初始化-字典数据
-- ----------------------------
insert into sys_dict_data values(1,  1,  '男',       '0',       'sys_user_sex',        '',   '',        'Y', '0', 'admin', '2023-01-01', '', null, '性别男');
insert into sys_dict_data values(2,  2,  '女',       '1',       'sys_user_sex',        '',   '',        'N', '0', 'admin', '2023-01-01', '', null, '性别女');
insert into sys_dict_data values(3,  3,  '未知',     '2',       'sys_user_sex',        '',   '',        'N', '0', 'admin', '2023-01-01', '', null, '性别未知');

insert into sys_dict_data values(4,  1,  '显示',     '0',       'sys_show_hide',       '',   'primary',  'Y', '0', 'admin', '2023-01-01', '', null, '显示菜单');
insert into sys_dict_data values(5,  2,  '隐藏',     '1',       'sys_show_hide',       '',   'danger',   'N', '0', 'admin', '2023-01-01', '', null, '隐藏菜单');

insert into sys_dict_data values(6,  1,  '正常',     '0',       'sys_normal_disable',  '',   'primary',  'Y', '0', 'admin', '2023-01-01', '', null, '正常状态');
insert into sys_dict_data values(7,  2,  '停用',     '1',       'sys_normal_disable',  '',   'danger',   'N', '0', 'admin', '2023-01-01', '', null, '停用状态');

-- ----------------------------
-- 初始化-参数配置数据
-- ----------------------------
insert into sys_config values(1, '主框架页-默认皮肤样式名称',     'sys.index.skinName',           'skin-blue',     'Y', 'admin', '2023-01-01', '', null, '蓝色主题');
insert into sys_config values(2, '用户管理-账号初始密码',         'sys.user.initPassword',         '123456',        'Y', 'admin', '2023-01-01', '', null, '初始化密码 123456');
insert into sys_config values(3, '主框架页-侧边栏主题',           'sys.index.sideTheme',           'theme-dark',    'Y', 'admin', '2023-01-01', '', null, '深色主题');
insert into sys_config values(4, '账号自助-验证码开关',             'sys.account.captchaEnabled',      'true',          'Y', 'admin', '2023-01-01', '', null, '是否开启验证码功能（true开启，false关闭）');
insert into sys_config values(5, '账号自助-是否开启用户注册功能',     'sys.account.registerUser',        'false',         'Y', 'admin', '2023-01-01', '', null, '是否开启注册用户功能（true开启，false关闭）');