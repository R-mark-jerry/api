import request from '@/utils/request'

// 查询API权限列表
export function listPermission(query) {
  return request({
    url: '/api/manage/permission/list',
    method: 'get',
    params: query
  })
}

// 查询API权限详细
export function getPermission(permissionId) {
  return request({
    url: '/api/manage/permission/' + permissionId,
    method: 'get'
  })
}

// 根据API ID查询权限列表
export function listPermissionByApiId(apiId) {
  return request({
    url: '/api/manage/permission/api/' + apiId,
    method: 'get'
  })
}

// 根据角色ID查询权限列表
export function listPermissionByRoleId(roleId) {
  return request({
    url: '/api/manage/permission/role/' + roleId,
    method: 'get'
  })
}

// 新增API权限
export function addPermission(data) {
  return request({
    url: '/api/manage/permission',
    method: 'post',
    data: data
  })
}

// 修改API权限
export function updatePermission(data) {
  return request({
    url: '/api/manage/permission',
    method: 'put',
    data: data
  })
}

// 删除API权限
export function delPermission(permissionId) {
  return request({
    url: '/api/manage/permission/' + permissionId,
    method: 'delete'
  })
}

// 批量删除API权限
export function delPermissions(permissionIds) {
  return request({
    url: '/api/manage/permission/' + permissionIds,
    method: 'delete'
  })
}

// 根据API ID删除权限
export function delPermissionByApiId(apiId) {
  return request({
    url: '/api/manage/permission/api/' + apiId,
    method: 'delete'
  })
}

// 根据角色ID删除权限
export function delPermissionByRoleId(roleId) {
  return request({
    url: '/api/manage/permission/role/' + roleId,
    method: 'delete'
  })
}

// 批量新增API权限
export function addPermissions(data) {
  return request({
    url: '/api/manage/permission/batch',
    method: 'post',
    data: data
  })
}

// 批量删除API权限
export function delPermissionsBatch(data) {
  return request({
    url: '/api/manage/permission/batch',
    method: 'delete',
    data: data
  })
}

// 校验API和角色权限是否已存在
export function checkApiRolePermission(apiId, roleId) {
  return request({
    url: '/api/manage/permission/check',
    method: 'get',
    params: { apiId, roleId }
  })
}

// 根据用户ID查询有权限的API列表
export function listApiIdByUserId(userId) {
  return request({
    url: '/api/manage/permission/user/' + userId,
    method: 'get'
  })
}

// 查询用户可访问的API
export function checkUserApiPermission(userId, apiPath, apiMethod) {
  return request({
    url: '/api/manage/permission/check/user',
    method: 'get',
    params: { userId, apiPath, apiMethod }
  })
}

// 为角色分配API权限
export function assignApiPermissionsToRole(roleId, apiIds, permissionType) {
  return request({
    url: '/api/manage/permission/assign/' + roleId,
    method: 'post',
    data: { apiIds, permissionType }
  })
}

// 取消角色的API权限
export function revokeApiPermissionsFromRole(roleId, apiIds) {
  return request({
    url: '/api/manage/permission/revoke/' + roleId,
    method: 'post',
    data: { apiIds }
  })
}

// 复制API权限
export function copyApiPermissions(sourceRoleId, targetRoleId) {
  return request({
    url: '/api/manage/permission/copy',
    method: 'post',
    data: { sourceRoleId, targetRoleId }
  })
}

// 查询API权限统计信息
export function getApiPermissionStats(params) {
  return request({
    url: '/api/manage/permission/stats',
    method: 'get',
    params: params
  })
}