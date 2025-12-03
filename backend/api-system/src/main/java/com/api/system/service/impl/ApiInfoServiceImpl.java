package com.api.system.service.impl;

import com.api.common.constant.UserConstants;
import com.api.common.core.text.Convert;
import com.api.common.exception.ServiceException;
import com.api.common.utils.DateUtils;
import com.api.common.utils.SecurityUtils;
import com.api.common.utils.StringUtils;
import com.api.system.domain.ApiInfo;
import com.api.system.domain.vo.ApiInfoVo;
import com.api.system.mapper.ApiInfoMapper;
import com.api.system.service.IApiInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * API信息Service业务层处理
 * 
 * @author api
 * @date 2023-01-01
 */
@Service
public class ApiInfoServiceImpl implements IApiInfoService {
    @Autowired
    private ApiInfoMapper apiInfoMapper;

    /**
     * 查询API信息
     * 
     * @param apiId API信息主键
     * @return API信息
     */
    @Override
    public ApiInfoVo selectApiInfoByApiId(Long apiId) {
        return apiInfoMapper.selectApiInfoByApiId(apiId);
    }

    /**
     * 查询API信息列表
     * 
     * @param apiInfo API信息
     * @return API信息
     */
    @Override
    public List<ApiInfoVo> selectApiInfoList(ApiInfo apiInfo) {
        return apiInfoMapper.selectApiInfoList(apiInfo);
    }

    /**
     * 新增API信息
     * 
     * @param apiInfo API信息
     * @return 结果
     */
    @Override
    public int insertApiInfo(ApiInfo apiInfo) {
        apiInfo.setCreateBy(SecurityUtils.getUserId());
        apiInfo.setCreateTime(DateUtils.getNowDate());
        return apiInfoMapper.insertApiInfo(apiInfo);
    }

    /**
     * 修改API信息
     * 
     * @param apiInfo API信息
     * @return 结果
     */
    @Override
    public int updateApiInfo(ApiInfo apiInfo) {
        apiInfo.setUpdateBy(SecurityUtils.getUserId());
        apiInfo.setUpdateTime(DateUtils.getNowDate());
        return apiInfoMapper.updateApiInfo(apiInfo);
    }

    /**
     * 批量删除API信息
     * 
     * @param apiIds 需要删除的API信息主键
     * @return 结果
     */
    @Override
    public int deleteApiInfoByApiIds(Long[] apiIds) {
        return apiInfoMapper.deleteApiInfoByApiIds(apiIds);
    }

    /**
     * 删除API信息信息
     * 
     * @param apiId API信息主键
     * @return 结果
     */
    @Override
    public int deleteApiInfoByApiId(Long apiId) {
        return apiInfoMapper.deleteApiInfoByApiId(apiId);
    }

    /**
     * 校验API路径是否唯一
     * 
     * @param apiInfo API信息
     * @return 结果
     */
    @Override
    public boolean checkApiPathUnique(ApiInfo apiInfo) {
        Long apiId = StringUtils.isNull(apiInfo.getApiId()) ? -1L : apiInfo.getApiId();
        ApiInfo info = apiInfoMapper.checkApiPathUnique(apiInfo.getApiPath(), apiInfo.getApiMethod(), apiId);
        if (StringUtils.isNotNull(info) && info.getApiId().longValue() != apiId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 根据分组ID查询API信息列表
     * 
     * @param groupId 分组ID
     * @return API信息列表
     */
    @Override
    public List<ApiInfoVo> selectApiInfoByGroupId(Long groupId) {
        return apiInfoMapper.selectApiInfoByGroupId(groupId);
    }

    /**
     * 更新API调用统计信息
     * 
     * @param params 统计参数
     * @return 结果
     */
    @Override
    public int updateApiCallStats(Map<String, Object> params) {
        return apiInfoMapper.updateApiCallStats(params);
    }

    /**
     * 根据角色ID查询API信息列表
     * 
     * @param roleId 角色ID
     * @return API信息列表
     */
    @Override
    public List<ApiInfoVo> selectApiInfoByRoleId(Long roleId) {
        return apiInfoMapper.selectApiInfoByRoleId(roleId);
    }

    /**
     * 查询今日API调用统计
     * 
     * @param apiId API ID
     * @return 统计信息
     */
    @Override
    public Map<String, Object> selectTodayApiStats(Long apiId) {
        return apiInfoMapper.selectTodayApiStats(apiId);
    }

    /**
     * 批量更新API状态
     * 
     * @param apiIds API ID列表
     * @param status 状态
     * @return 结果
     */
    @Override
    public int updateApiStatusBatch(Long[] apiIds, String status) {
        return apiInfoMapper.updateApiStatusBatch(apiIds, status);
    }

    /**
     * 根据关键词搜索API信息
     * 
     * @param keyword 关键词
     * @return API信息列表
     */
    @Override
    public List<ApiInfoVo> searchApiInfo(String keyword) {
        return apiInfoMapper.searchApiInfo(keyword);
    }

    /**
     * 查询热门API列表
     * 
     * @param limit 限制数量
     * @return API信息列表
     */
    @Override
    public List<ApiInfoVo> selectHotApiList(Integer limit) {
        return apiInfoMapper.selectHotApiList(limit);
    }

    /**
     * 查询API调用排行榜
     * 
     * @param params 查询参数
     * @return API信息列表
     */
    @Override
    public List<ApiInfoVo> selectApiRankingList(Map<String, Object> params) {
        return apiInfoMapper.selectApiRankingList(params);
    }

    /**
     * 导出API信息
     * 
     * @param apiInfo 查询条件
     * @return API信息列表
     */
    @Override
    public List<ApiInfoVo> exportApiInfo(ApiInfo apiInfo) {
        return apiInfoMapper.selectApiInfoList(apiInfo);
    }
}