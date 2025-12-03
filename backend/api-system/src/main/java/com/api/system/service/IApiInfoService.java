package com.api.system.service;

import com.api.system.domain.ApiInfo;
import com.api.system.domain.dto.ApiInfoDto;
import com.api.system.domain.vo.ApiInfoVo;

import java.util.List;
import java.util.Map;

/**
 * API信息Service接口
 * 
 * @author api
 * @date 2023-01-01
 */
public interface IApiInfoService {
    /**
     * 查询API信息
     * 
     * @param apiId API信息主键
     * @return API信息
     */
    ApiInfoVo selectApiInfoByApiId(Long apiId);

    /**
     * 查询API信息列表
     * 
     * @param apiInfo API信息
     * @return API信息集合
     */
    List<ApiInfoVo> selectApiInfoList(ApiInfo apiInfo);

    /**
     * 新增API信息
     * 
     * @param apiInfo API信息
     * @return 结果
     */
    int insertApiInfo(ApiInfo apiInfo);

    /**
     * 修改API信息
     * 
     * @param apiInfo API信息
     * @return 结果
     */
    int updateApiInfo(ApiInfo apiInfo);

    /**
     * 批量删除API信息
     * 
     * @param apiIds 需要删除的API信息主键集合
     * @return 结果
     */
    int deleteApiInfoByApiIds(Long[] apiIds);

    /**
     * 删除API信息信息
     * 
     * @param apiId API信息主键
     * @return 结果
     */
    int deleteApiInfoByApiId(Long apiId);

    /**
     * 校验API路径是否唯一
     * 
     * @param apiInfo API信息
     * @return 结果
     */
    boolean checkApiPathUnique(ApiInfo apiInfo);

    /**
     * 根据分组ID查询API信息列表
     * 
     * @param groupId 分组ID
     * @return API信息列表
     */
    List<ApiInfoVo> selectApiInfoByGroupId(Long groupId);

    /**
     * 更新API调用统计信息
     * 
     * @param params 统计参数
     * @return 结果
     */
    int updateApiCallStats(Map<String, Object> params);

    /**
     * 根据角色ID查询API信息列表
     * 
     * @param roleId 角色ID
     * @return API信息列表
     */
    List<ApiInfoVo> selectApiInfoByRoleId(Long roleId);

    /**
     * 查询今日API调用统计
     * 
     * @param apiId API ID
     * @return 统计信息
     */
    Map<String, Object> selectTodayApiStats(Long apiId);

    /**
     * 批量更新API状态
     * 
     * @param apiIds API ID列表
     * @param status 状态
     * @return 结果
     */
    int updateApiStatusBatch(Long[] apiIds, String status);

    /**
     * 根据关键词搜索API信息
     * 
     * @param keyword 关键词
     * @return API信息列表
     */
    List<ApiInfoVo> searchApiInfo(String keyword);

    /**
     * 查询热门API列表
     * 
     * @param limit 限制数量
     * @return API信息列表
     */
    List<ApiInfoVo> selectHotApiList(Integer limit);

    /**
     * 查询API调用排行榜
     * 
     * @param params 查询参数
     * @return API信息列表
     */
    List<ApiInfoVo> selectApiRankingList(Map<String, Object> params);

    /**
     * 导出API信息
     * 
     * @param apiInfo 查询条件
     * @return API信息列表
     */
    List<ApiInfoVo> exportApiInfo(ApiInfo apiInfo);
}