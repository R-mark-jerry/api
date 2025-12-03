package com.api.system.service;

import com.api.system.domain.ApiCallLog;
import com.api.system.domain.vo.ApiCallLogVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * API调用日志Service接口
 * 
 * @author api
 * @date 2023-01-01
 */
public interface IApiCallLogService {
    /**
     * 查询API调用日志
     * 
     * @param logId API调用日志主键
     * @return API调用日志
     */
    ApiCallLogVo selectApiCallLogByLogId(Long logId);

    /**
     * 查询API调用日志列表
     * 
     * @param apiCallLog API调用日志
     * @return API调用日志集合
     */
    List<ApiCallLogVo> selectApiCallLogList(ApiCallLog apiCallLog);

    /**
     * 新增API调用日志
     * 
     * @param apiCallLog API调用日志
     * @return 结果
     */
    int insertApiCallLog(ApiCallLog apiCallLog);

    /**
     * 修改API调用日志
     * 
     * @param apiCallLog API调用日志
     * @return 结果
     */
    int updateApiCallLog(ApiCallLog apiCallLog);

    /**
     * 批量删除API调用日志
     * 
     * @param logIds 需要删除的API调用日志主键集合
     * @return 结果
     */
    int deleteApiCallLogByLogIds(Long[] logIds);

    /**
     * 删除API调用日志信息
     * 
     * @param logId API调用日志主键
     * @return 结果
     */
    int deleteApiCallLogByLogId(Long logId);

    /**
     * 根据API ID查询调用日志列表
     * 
     * @param apiId API ID
     * @return 调用日志列表
     */
    List<ApiCallLogVo> selectApiCallLogByApiId(Long apiId);

    /**
     * 根据用户ID查询调用日志列表
     * 
     * @param userId 用户ID
     * @return 调用日志列表
     */
    List<ApiCallLogVo> selectApiCallLogByUserId(Long userId);

    /**
     * 根据时间范围查询调用日志列表
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 调用日志列表
     */
    List<ApiCallLogVo> selectApiCallLogByTimeRange(Date startTime, Date endTime);

    /**
     * 查询API调用统计信息
     * 
     * @param params 查询参数
     * @return 统计信息
     */
    Map<String, Object> selectApiCallStats(Map<String, Object> params);

    /**
     * 查询API调用趋势数据
     * 
     * @param params 查询参数
     * @return 趋势数据
     */
    List<Map<String, Object>> selectApiCallTrend(Map<String, Object> params);

    /**
     * 查询API调用排行榜
     * 
     * @param params 查询参数
     * @return 排行榜数据
     */
    List<Map<String, Object>> selectApiCallRanking(Map<String, Object> params);

    /**
     * 查询API错误统计
     * 
     * @param params 查询参数
     * @return 错误统计
     */
    List<Map<String, Object>> selectApiErrorStats(Map<String, Object> params);

    /**
     * 查询API响应时间统计
     * 
     * @param params 查询参数
     * @return 响应时间统计
     */
    List<Map<String, Object>> selectApiResponseTimeStats(Map<String, Object> params);

    /**
     * 清空指定时间之前的日志
     * 
     * @param beforeTime 时间点
     * @return 结果
     */
    int deleteApiCallLogBeforeTime(Date beforeTime);

    /**
     * 根据条件查询调用日志数量
     * 
     * @param apiCallLog 查询条件
     * @return 数量
     */
    int selectApiCallLogCount(ApiCallLog apiCallLog);

    /**
     * 清空所有API调用日志
     * 
     * @return 结果
     */
    int cleanAllApiCallLog();

    /**
     * 导出API调用日志
     * 
     * @param apiCallLog 查询条件
     * @return API调用日志列表
     */
    List<ApiCallLogVo> exportApiCallLog(ApiCallLog apiCallLog);

    /**
     * 记录API调用日志
     * 
     * @param apiId API ID
     * @param userId 用户ID
     * @param userName 用户名
     * @param requestIp 请求IP
     * @param requestMethod 请求方法
     * @param requestUrl 请求URL
     * @param requestParams 请求参数
     * @param responseStatus 响应状态码
     * @param responseData 响应数据
     * @param responseTime 响应时间
     * @param isSuccess 是否成功
     * @param errorMessage 错误信息
     * @return 结果
     */
    int recordApiCallLog(Long apiId, Long userId, String userName, String requestIp, 
                        String requestMethod, String requestUrl, String requestParams,
                        Integer responseStatus, String responseData, Double responseTime,
                        String isSuccess, String errorMessage);
}