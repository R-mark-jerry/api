package com.api.system.mapper;

import com.api.system.domain.ApiCallLog;
import com.api.system.domain.vo.ApiCallLogVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * API调用日志Mapper接口
 * 
 * @author api
 * @date 2023-01-01
 */
public interface ApiCallLogMapper {
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
     * 删除API调用日志
     * 
     * @param logId API调用日志主键
     * @return 结果
     */
    int deleteApiCallLogByLogId(Long logId);

    /**
     * 批量删除API调用日志
     * 
     * @param logIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteApiCallLogByLogIds(Long[] logIds);

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
    List<ApiCallLogVo> selectApiCallLogByTimeRange(@Param("startTime") Date startTime, 
                                                   @Param("endTime") Date endTime);

    /**
     * 查询API调用统计信息
     * 
     * @param params 查询参数
     * @return 统计信息
     */
    Map<String, Object> selectApiCallStats(@Param("params") Map<String, Object> params);

    /**
     * 查询API调用趋势数据
     * 
     * @param params 查询参数
     * @return 趋势数据
     */
    List<Map<String, Object>> selectApiCallTrend(@Param("params") Map<String, Object> params);

    /**
     * 查询API调用排行榜
     * 
     * @param params 查询参数
     * @return 排行榜数据
     */
    List<Map<String, Object>> selectApiCallRanking(@Param("params") Map<String, Object> params);

    /**
     * 查询API错误统计
     * 
     * @param params 查询参数
     * @return 错误统计
     */
    List<Map<String, Object>> selectApiErrorStats(@Param("params") Map<String, Object> params);

    /**
     * 查询API响应时间统计
     * 
     * @param params 查询参数
     * @return 响应时间统计
     */
    List<Map<String, Object>> selectApiResponseTimeStats(@Param("params") Map<String, Object> params);

    /**
     * 清空指定时间之前的日志
     * 
     * @param beforeTime 时间点
     * @return 结果
     */
    int deleteApiCallLogBeforeTime(@Param("beforeTime") Date beforeTime);

    /**
     * 根据条件查询调用日志数量
     * 
     * @param apiCallLog 查询条件
     * @return 数量
     */
    int selectApiCallLogCount(ApiCallLog apiCallLog);
}