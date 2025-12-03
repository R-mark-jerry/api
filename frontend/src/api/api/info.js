import request from '@/utils/request'

// 查询API信息列表
export function listInfo(query) {
  return request({
    url: '/api/manage/info/list',
    method: 'get',
    params: query
  })
}

// 查询API信息详细
export function getInfo(apiId) {
  return request({
    url: '/api/manage/info/' + apiId,
    method: 'get'
  })
}

// 新增API信息
export function addInfo(data) {
  return request({
    url: '/api/manage/info',
    method: 'post',
    data: data
  })
}

// 修改API信息
export function updateInfo(data) {
  return request({
    url: '/api/manage/info',
    method: 'put',
    data: data
  })
}

// 删除API信息
export function delInfo(apiId) {
  return request({
    url: '/api/manage/info/' + apiId,
    method: 'delete'
  })
}

// 导出API信息
export function exportInfo(query) {
  return request({
    url: '/api/manage/info/export',
    method: 'get',
    params: query
  })
}

// 根据分组ID查询API信息列表
export function listInfoByGroupId(groupId) {
  return request({
    url: '/api/manage/info/group/' + groupId,
    method: 'get'
  })
}

// 批量更新API状态
export function updateApiStatus(data) {
  return request({
    url: '/api/manage/info/status',
    method: 'put',
    data: data
  })
}

// 根据关键词搜索API信息
export function searchInfo(keyword) {
  return request({
    url: '/api/manage/info/search',
    method: 'get',
    params: { keyword }
  })
}

// 查询热门API列表
export function listHotApi(limit) {
  return request({
    url: '/api/manage/info/hot',
    method: 'get',
    params: { limit }
  })
}

// 查询API调用排行榜
export function listApiRanking(data) {
  return request({
    url: '/api/manage/info/ranking',
    method: 'post',
    data: data
  })
}

// 测试API
export function testApi(apiId, data) {
  return request({
    url: '/api/manage/info/test/' + apiId,
    method: 'post',
    data: data
  })
}

// 查询今日API调用统计
export function getTodayApiStats(apiId) {
  return request({
    url: '/api/manage/info/stats/' + apiId,
    method: 'get'
  })
}