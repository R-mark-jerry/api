package com.api.system.service.impl;

import com.api.common.utils.DateUtils;
import com.api.common.utils.SecurityUtils;
import com.api.common.utils.StringUtils;
import com.api.system.domain.ApiCallLog;
import com.api.system.domain.vo.ApiCallLogVo;
import com.api.system.mapper.ApiCallLogMapper;
import com.api.system.service.IApiCallLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * API调用日志Service业务层处理
 * 
 * @author api
 * @date 2023-01-01
 */
@Service
public class ApiCallLogServiceImpl implements IApiCallLogService {
    @Autowired
    private ApiCallLogMapper apiCallLogMapper;

    /**
     * 查询API调用日志
     * 
     * @param logId API调用日志主键
     * @return API调用日志
     */
    @Override
    public ApiCallLogVo selectApiCallLogByLogId(Long logId) {
        return apiCallLogMapper.selectApiCallLogByLogId(logId);
    }

    /**
     * 查询API调用日志列表
     * 
     * @param apiCallLog API调用日志
     * @return API调用日志
     */
    @Override
    public List<ApiCallLogVo> selectApiCallLogList(ApiCallLog apiCallLog) {
        return apiCallLogMapper.selectApiCallLogList(apiCallLog);
    }

    /**
     * 新增API调用日志
     * 
     * @param apiCallLog API调用日志
     * @return 结果
     */
    @Override
    public int insertApiCallLog(ApiCallLog apiCallLog) {
        apiCallLog.setCallTime(DateUtils.getNowDate());
        return apiCallLogMapper.insertApiCallLog(apiCallLog);
    }

    /**
     * 修改API调用日志
     * 
     * @param apiCallLog API调用日志
     * @return 结果
     */
    @Override
    public int updateApiCallLog(ApiCallLog apiCallLog) {
        return apiCallLogMapper.updateApiCallLog(apiCallLog);
    }

    /**
     * 批量删除API调用日志
     * 
     * @param logIds 需要删除的API调用日志主键
     * @return 结果
     */
    @Override
    public int deleteApiCallLogByLogIds(Long[] logIds) {
        return apiCallLogMapper.deleteApiCallLogByLogIds(logIds);
    }

    /**
     * 删除API调用日志信息
     * 
     * @param logId API调用日志主键
     * @return 结果
     */
    @Override
    public int deleteApiCallLogByLogId(Long logId) {
        return apiCallLogMapper.deleteApiCallLogByLogId(logId);
    }

    /**
     * 根据API ID查询调用日志列表
     * 
     * @param apiId API ID
     * @return 调用日志列表
     */
    @Override
    public List<ApiCallLogVo> selectApiCallLogByApiId(Long apiId) {
        return apiCallLogMapper.selectApiCallLogByApiId(apiId);
    }

    /**
     * 根据用户ID查询调用日志列表
     * 
     * @param userId 用户ID
     * @return 调用日志列表
     */
    @Override
    public List<ApiCallLogVo> selectApiCallLogByUserId(Long userId) {
        return apiCallLogMapper.selectApiCallLogByUserId(userId);
    }

    /**
     * 根据时间范围查询调用日志列表
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 调用日志列表
     */
    @Override
    public List<ApiCallLogVo> selectApiCallLogByTimeRange(Date startTime, Date endTime) {
        return apiCallLogMapper.selectApiCallLogByTimeRange(startTime, endTime);
    }

    /**
     * 查询API调用统计信息
     * 
     * @param params 查询参数
     * @return 统计信息
     */
    @Override
    public Map<String, Object> selectApiCallStats(Map<String, Object> params) {
        return apiCallLogMapper.selectApiCallStats(params);
    }

    /**
     * 查询API调用趋势数据
     * 
     * @param params 查询参数
     * @return 趋势数据
     */
    @Override
    public List<Map<String, Object>> selectApiCallTrend(Map<String, Object> params) {
        return apiCallLogMapper.selectApiCallTrend(params);
    }

    /**
     * 查询API调用排行榜
     * 
     * @param params 查询参数
     * @return 排行榜数据
     */
    @Override
    public List<Map<String, Object>> selectApiCallRanking(Map<String, Object> params) {
        return apiCallLogMapper.selectApiCallRanking(params);
    }

    /**
     * 查询API错误统计
     * 
     * @param params 查询参数
     * @return 错误统计
     */
    @Override
    public List<Map<String, Object>> selectApiErrorStats(Map<String, Object> params) {
        return apiCallLogMapper.selectApiErrorStats(params);
    }

    /**
     * 查询API响应时间统计
     * 
     * @param params 查询参数
     * @return 响应时间统计
     */
    @Override
    public List<Map<String, Object>> selectApiResponseTimeStats(Map<String, Object> params) {
        return apiCallLogMapper.selectApiResponseTimeStats(params);
    }

    /**
     * 清空指定时间之前的日志
     * 
     * @param beforeTime 时间点
     * @return 结果
     */
    @Override
    public int deleteApiCallLogBeforeTime(Date beforeTime) {
        return apiCallLogMapper.deleteApiCallLogBeforeTime(beforeTime);
    }

    /**
     * 根据条件查询调用日志数量
     * 
     * @param apiCallLog 查询条件
     * @return 数量
     */
    @Override
    public int selectApiCallLogCount(ApiCallLog apiCallLog) {
        return apiCallLogMapper.selectApiCallLogCount(apiCallLog);
    }

    /**
     * 清空所有API调用日志
     * 
     * @return 结果
     */
    @Override
    public int cleanAllApiCallLog() {
        // 这里可以通过TRUNCATE TABLE或者DELETE FROM TABLE来实现
        // 为了安全起见，使用DELETE FROM TABLE
        return apiCallLogMapper.deleteApiCallLogByLogIds(null);
    }

    /**
     * 导出API调用日志
     * 
     * @param apiCallLog 查询条件
     * @return API调用日志列表
     */
    @Override
    public List<ApiCallLogVo> exportApiCallLog(ApiCallLog apiCallLog) {
        return apiCallLogMapper.selectApiCallLogList(apiCallLog);
    }

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
    @Override
    public int recordApiCallLog(Long apiId, Long userId, String userName, String requestIp, 
                              String requestMethod, String requestUrl, String requestParams,
                              Integer responseStatus, String responseData, Double responseTime,
                              String isSuccess, String errorMessage) {
        ApiCallLog apiCallLog = new ApiCallLog();
        apiCallLog.setApiId(apiId);
        apiCallLog.setUserId(userId);
        apiCallLog.setUserName(StringUtils.isNotEmpty(userName) ? userName : "");
        apiCallLog.setRequestIp(StringUtils.isNotEmpty(requestIp) ? requestIp : "");
        apiCallLog.setRequestMethod(StringUtils.isNotEmpty(requestMethod) ? requestMethod : "");
        apiCallLog.setRequestUrl(StringUtils.isNotEmpty(requestUrl) ? requestUrl : "");
        apiCallLog.setRequestParams(requestParams);
        apiCallLog.setResponseStatus(responseStatus != null ? responseStatus : 200);
        apiCallLog.setResponseData(responseData);
        apiCallLog.setResponseTime(responseTime != null ? responseTime : 0.0);
        apiCallLog.setIsSuccess(StringUtils.isNotEmpty(isSuccess) ? isSuccess : "1");
        apiCallLog.setErrorMessage(errorMessage);
        
        return insertApiCallLog(apiCallLog);
    }
}