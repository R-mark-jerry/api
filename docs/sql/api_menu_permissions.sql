-- API管理菜单权限SQL脚本

-- 1. 添加API管理父菜单
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('API管理', 0, 1, 'api', NULL, 1, 0, 'M', '0', '0', NULL, 'api', 'admin', sysdate(), '', NULL, 'API管理目录');

-- 获取刚插入的API管理父菜单ID (假设为2000)
SET @parent_menu_id = 2000;

-- 2. 添加API分组管理菜单
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('API分组', @parent_menu_id, 1, 'group', 'api/group/index', 1, 0, 'C', '0', '0', 'api:group:list', 'tree', 'admin', sysdate(), '', NULL, 'API分组管理菜单');

-- 获取API分组管理菜单ID
SET @group_menu_id = LAST_INSERT_ID();

-- API分组管理按钮权限
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('API分组查询', @group_menu_id, 1, '#', '', 1, 0, 'F', '0', '0', 'api:group:query', '#', 'admin', sysdate(), '', NULL, '');

INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('API分组新增', @group_menu_id, 2, '#', '', 1, 0, 'F', '0', '0', 'api:group:add', '#', 'admin', sysdate(), '', NULL, '');

INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('API分组修改', @group_menu_id, 3, '#', '', 1, 0, 'F', '0', '0', 'api:group:edit', '#', 'admin', sysdate(), '', NULL, '');

INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('API分组删除', @group_menu_id, 4, '#', '', 1, 0, 'F', '0', '0', 'api:group:remove', '#', 'admin', sysdate(), '', NULL, '');

INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('API分组导出', @group_menu_id, 5, '#', '', 1, 0, 'F', '0', '0', 'api:group:export', '#', 'admin', sysdate(), '', NULL, '');

-- 3. 添加API信息管理菜单
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('API信息', @parent_menu_id, 2, 'info', 'api/info/index', 1, 0, 'C', '0', '0', 'api:info:list', 'list', 'admin', sysdate(), '', NULL, 'API信息管理菜单');

-- 获取API信息管理菜单ID
SET @info_menu_id = LAST_INSERT_ID();

-- API信息管理按钮权限
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('API信息查询', @info_menu_id, 1, '#', '', 1, 0, 'F', '0', '0', 'api:info:query', '#', 'admin', sysdate(), '', NULL, '');

INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('API信息新增', @info_menu_id, 2, '#', '', 1, 0, 'F', '0', '0', 'api:info:add', '#', 'admin', sysdate(), '', NULL, '');

INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('API信息修改', @info_menu_id, 3, '#', '', 1, 0, 'F', '0', '0', 'api:info:edit', '#', 'admin', sysdate(), '', NULL, '');

INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('API信息删除', @info_menu_id, 4, '#', '', 1, 0, 'F', '0', '0', 'api:info:remove', '#', 'admin', sysdate(), '', NULL, '');

INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('API信息导出', @info_menu_id, 5, '#', '', 1, 0, 'F', '0', '0', 'api:info:export', '#', 'admin', sysdate(), '', NULL, '');

-- 4. 添加API调用日志菜单
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('调用日志', @parent_menu_id, 3, 'log', 'api/log/index', 1, 0, 'C', '0', '0', 'api:log:list', 'log', 'admin', sysdate(), '', NULL, 'API调用日志菜单');

-- 获取API调用日志菜单ID
SET @log_menu_id = LAST_INSERT_ID();

-- API调用日志按钮权限
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('调用日志查询', @log_menu_id, 1, '#', '', 1, 0, 'F', '0', '0', 'api:log:query', '#', 'admin', sysdate(), '', NULL, '');

INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('调用日志删除', @log_menu_id, 2, '#', '', 1, 0, 'F', '0', '0', 'api:log:remove', '#', 'admin', sysdate(), '', NULL, '');

INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('调用日志导出', @log_menu_id, 3, '#', '', 1, 0, 'F', '0', '0', 'api:log:export', '#', 'admin', sysdate(), '', NULL, '');

INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('调用日志清空', @log_menu_id, 4, '#', '', 1, 0, 'F', '0', '0', 'api:log:clean', '#', 'admin', sysdate(), '', NULL, '');

-- 5. 为管理员角色分配API管理权限 (假设管理员角色ID为1)
INSERT INTO sys_role_menu (role_id, menu_id) 
SELECT 1, menu_id FROM sys_menu WHERE menu_name LIKE 'API%' OR perms LIKE 'api:%';