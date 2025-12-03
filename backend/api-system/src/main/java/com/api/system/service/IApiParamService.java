package com.api.system.service;

import com.api.system.domain.ApiParam;

import java.util.List;

/**
 * API参数Service接口
 * 
 * @author api
 * @date 2023-01-01
 */
public interface IApiParamService {
    /**
     * 查询API参数
     * 
     * @param paramId API参数主键
     * @return API参数
     */
    ApiParam selectApiParamByParamId(Long paramId);

    /**
     * 查询API参数列表
     * 
     * @param apiParam API参数
     * @return API参数集合
     */
    List<ApiParam> selectApiParamList(ApiParam apiParam);

    /**
     * 根据API ID查询参数列表
     * 
     * @param apiId API ID
     * @return 参数列表
     */
    List<ApiParam> selectApiParamByApiId(Long apiId);

    /**
     * 新增API参数
     * 
     * @param apiParam API参数
     * @return 结果
     */
    int insertApiParam(ApiParam apiParam);

    /**
     * 修改API参数
     * 
     * @param apiParam API参数
     * @return 结果
     */
    int updateApiParam(ApiParam apiParam);

    /**
     * 批量删除API参数
     * 
     * @param paramIds 需要删除的API参数主键集合
     * @return 结果
     */
    int deleteApiParamByParamIds(Long[] paramIds);

    /**
     * 删除API参数信息
     * 
     * @param paramId API参数主键
     * @return 结果
     */
    int deleteApiParamByParamId(Long paramId);

    /**
     * 根据API ID删除参数
     * 
     * @param apiId API ID
     * @return 结果
     */
    int deleteApiParamByApiId(Long apiId);

    /**
     * 批量新增API参数
     * 
     * @param apiParams API参数列表
     * @return 结果
     */
    int insertApiParamBatch(List<ApiParam> apiParams);

    /**
     * 批量更新API参数
     * 
     * @param apiParams API参数列表
     * @return 结果
     */
    int updateApiParamBatch(List<ApiParam> apiParams);

    /**
     * 根据API ID删除所有参数并重新添加
     * 
     * @param apiId API ID
     * @param apiParams 新的参数列表
     * @return 结果
     */
    int refreshApiParams(Long apiId, List<ApiParam> apiParams);
}