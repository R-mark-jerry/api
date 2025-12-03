package com.api.system.mapper;

import com.api.system.domain.ApiInfo;
import com.api.system.domain.vo.ApiInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * API信息Mapper接口
 * 
 * @author api
 * @date 2023-01-01
 */
public interface ApiInfoMapper {
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
     * 删除API信息
     * 
     * @param apiId API信息主键
     * @return 结果
     */
    int deleteApiInfoByApiId(Long apiId);

    /**
     * 批量删除API信息
     * 
     * @param apiIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteApiInfoByApiIds(Long[] apiIds);

    /**
     * 根据API路径和方法查询API信息
     * 
     * @param apiPath API路径
     * @param apiMethod 请求方法
     * @param apiId API ID（排除自己）
     * @return API信息
     */
    ApiInfo checkApiPathUnique(@Param("apiPath") String apiPath, 
                           @Param("apiMethod") String apiMethod, 
                           @Param("apiId") Long apiId);

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
    int updateApiCallStats(@Param("params") Map<String, Object> params);

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
    int updateApiStatusBatch(@Param("apiIds") Long[] apiIds, @Param("status") String status);

    /**
     * 根据关键词搜索API信息
     * 
     * @param keyword 关键词
     * @return API信息列表
     */
    List<ApiInfoVo> searchApiInfo(@Param("keyword") String keyword);

    /**
     * 查询热门API列表
     * 
     * @param limit 限制数量
     * @return API信息列表
     */
    List<ApiInfoVo> selectHotApiList(@Param("limit") Integer limit);

    /**
     * 查询API调用排行榜
     * 
     * @param params 查询参数
     * @return API信息列表
     */
    List<ApiInfoVo> selectApiRankingList(@Param("params") Map<String, Object> params);
}