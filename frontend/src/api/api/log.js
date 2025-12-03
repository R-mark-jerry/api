import request from '@/utils/request'

// 查询API调用日志列表
export function listLog(query) {
  return request({
    url: '/api/manage/log/list',
    method: 'get',
    params: query
  })
}

// 查询API调用日志详细
export function getLog(logId) {
  return request({
    url: '/api/manage/log/' + logId,
    method: 'get'
  })
}

// 删除API调用日志
export function delLog(logId) {
  return request({
    url: '/api/manage/log/' + logId,
    method: 'delete'
  })
}

// 清空API调用日志
export function cleanLog() {
  return request({
    url: '/api/manage/log/clean',
    method: 'delete'
  })
}

// 导出API调用日志
export function exportLog(query) {
  return request({
    url: '/api/manage/log/export',
    method: 'get',
    params: query
  })
}

// 根据API ID查询调用日志列表
export function listLogByApiId(apiId) {
  return request({
    url: '/api/manage/log/api/' + apiId,
    method: 'get'
  })
}

// 根据用户ID查询调用日志列表
export function listLogByUserId(userId) {
  return request({
    url: '/api/manage/log/user/' + userId,
    method: 'get'
  })
}

// 根据时间范围查询调用日志列表
export function listLogByTimeRange(params) {
  return request({
    url: '/api/manage/log/timeRange',
    method: 'get',
    params: params
  })
}

// 查询API调用统计信息
export function getApiCallStats(params) {
  return request({
    url: '/api/manage/log/stats',
    method: 'get',
    params: params
  })
}

// 查询API调用趋势数据
export function getApiCallTrend(params) {
  return request({
    url: '/api/manage/log/trend',
    method: 'get',
    params: params
  })
}

// 查询API调用排行榜
export function getApiCallRanking(params) {
  return request({
    url: '/api/manage/log/ranking',
    method: 'get',
    params: params
  })
}

// 查询API错误统计
export function getApiErrorStats(params) {
  return request({
    url: '/api/manage/log/errorStats',
    method: 'get',
    params: params
  })
}

// 查询API响应时间统计
export function getApiResponseTimeStats(params) {
  return request({
    url: '/api/manage/log/responseTimeStats',
    method: 'get',
    params: params
  })
}

// 清空指定时间之前的日志
export function cleanLogBeforeTime(beforeTime) {
  return request({
    url: '/api/manage/log/cleanBefore',
    method: 'delete',
    params: { beforeTime }
  })
}

// 记录API调用日志
export function recordApiCallLog(data) {
  return request({
    url: '/api/manage/log/record',
    method: 'post',
    data: data
  })
}