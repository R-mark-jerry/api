package com.api.system.service.impl;

import com.api.common.utils.DateUtils;
import com.api.system.domain.ApiParam;
import com.api.system.mapper.ApiParamMapper;
import com.api.system.service.IApiParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * API参数Service业务层处理
 * 
 * @author api
 * @date 2023-01-01
 */
@Service
public class ApiParamServiceImpl implements IApiParamService {
    @Autowired
    private ApiParamMapper apiParamMapper;

    /**
     * 查询API参数
     * 
     * @param paramId API参数主键
     * @return API参数
     */
    @Override
    public ApiParam selectApiParamByParamId(Long paramId) {
        return apiParamMapper.selectApiParamByParamId(paramId);
    }

    /**
     * 查询API参数列表
     * 
     * @param apiParam API参数
     * @return API参数
     */
    @Override
    public List<ApiParam> selectApiParamList(ApiParam apiParam) {
        return apiParamMapper.selectApiParamList(apiParam);
    }

    /**
     * 根据API ID查询参数列表
     * 
     * @param apiId API ID
     * @return 参数列表
     */
    @Override
    public List<ApiParam> selectApiParamByApiId(Long apiId) {
        return apiParamMapper.selectApiParamByApiId(apiId);
    }

    /**
     * 新增API参数
     * 
     * @param apiParam API参数
     * @return 结果
     */
    @Override
    public int insertApiParam(ApiParam apiParam) {
        apiParam.setCreateTime(DateUtils.getNowDate());
        return apiParamMapper.insertApiParam(apiParam);
    }

    /**
     * 修改API参数
     * 
     * @param apiParam API参数
     * @return 结果
     */
    @Override
    public int updateApiParam(ApiParam apiParam) {
        apiParam.setUpdateTime(DateUtils.getNowDate());
        return apiParamMapper.updateApiParam(apiParam);
    }

    /**
     * 批量删除API参数
     * 
     * @param paramIds 需要删除的API参数主键
     * @return 结果
     */
    @Override
    public int deleteApiParamByParamIds(Long[] paramIds) {
        return apiParamMapper.deleteApiParamByParamIds(paramIds);
    }

    /**
     * 删除API参数信息
     * 
     * @param paramId API参数主键
     * @return 结果
     */
    @Override
    public int deleteApiParamByParamId(Long paramId) {
        return apiParamMapper.deleteApiParamByParamId(paramId);
    }

    /**
     * 根据API ID删除参数
     * 
     * @param apiId API ID
     * @return 结果
     */
    @Override
    public int deleteApiParamByApiId(Long apiId) {
        return apiParamMapper.deleteApiParamByApiId(apiId);
    }

    /**
     * 批量新增API参数
     * 
     * @param apiParams API参数列表
     * @return 结果
     */
    @Override
    public int insertApiParamBatch(List<ApiParam> apiParams) {
        if (apiParams == null || apiParams.isEmpty()) {
            return 0;
        }
        
        // 设置创建时间
        for (ApiParam param : apiParams) {
            param.setCreateTime(DateUtils.getNowDate());
        }
        
        return apiParamMapper.insertApiParamBatch(apiParams);
    }

    /**
     * 批量更新API参数
     * 
     * @param apiParams API参数列表
     * @return 结果
     */
    @Override
    public int updateApiParamBatch(List<ApiParam> apiParams) {
        if (apiParams == null || apiParams.isEmpty()) {
            return 0;
        }
        
        // 设置更新时间
        for (ApiParam param : apiParams) {
            param.setUpdateTime(DateUtils.getNowDate());
        }
        
        return apiParamMapper.updateApiParamBatch(apiParams);
    }

    /**
     * 根据API ID删除所有参数并重新添加
     * 
     * @param apiId API ID
     * @param apiParams 新的参数列表
     * @return 结果
     */
    @Override
    public int refreshApiParams(Long apiId, List<ApiParam> apiParams) {
        // 先删除原有参数
        deleteApiParamByApiId(apiId);
        
        // 再添加新参数
        if (apiParams != null && !apiParams.isEmpty()) {
            // 设置API ID和创建时间
            for (ApiParam param : apiParams) {
                param.setApiId(apiId);
                param.setCreateTime(DateUtils.getNowDate());
            }
            return insertApiParamBatch(apiParams);
        }
        
        return 1;
    }
}