import request from '@/utils/request'

// 查询API分组列表
export function listGroup(query) {
  return request({
    url: '/api/manage/group/list',
    method: 'get',
    params: query
  })
}

// 查询API分组详细
export function getGroup(groupId) {
  return request({
    url: '/api/manage/group/' + groupId,
    method: 'get'
  })
}

// 新增API分组
export function addGroup(data) {
  return request({
    url: '/api/manage/group',
    method: 'post',
    data: data
  })
}

// 修改API分组
export function updateGroup(data) {
  return request({
    url: '/api/manage/group',
    method: 'put',
    data: data
  })
}

// 删除API分组
export function delGroup(groupId) {
  return request({
    url: '/api/manage/group/' + groupId,
    method: 'delete'
  })
}

// 导出API分组
export function exportGroup(query) {
  return request({
    url: '/api/manage/group/export',
    method: 'get',
    params: query
  })
}

// 查询API分组树结构
export function listGroupTree(query) {
  return request({
    url: '/api/manage/group/tree',
    method: 'get',
    params: query
  })
}

// 根据分组ID查询所有子分组
export function listChildrenGroup(groupId) {
  return request({
    url: '/api/manage/group/children/' + groupId,
    method: 'get'
  })
}

// 查询分组是否存在子分组
export function hasChildGroup(groupId) {
  return request({
    url: '/api/manage/group/hasChild/' + groupId,
    method: 'get'
  })
}

// 查询分组是否存在API
export function checkGroupExistApi(groupId) {
  return request({
    url: '/api/manage/group/checkApi/' + groupId,
    method: 'get'
  })
}

// 根据分组ID列表查询分组信息
export function listGroupByIds(groupIds) {
  return request({
    url: '/api/manage/group/ids/' + groupIds,
    method: 'get'
  })
}