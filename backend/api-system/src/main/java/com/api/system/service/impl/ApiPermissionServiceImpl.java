package com.api.system.service.impl;

import com.api.common.utils.DateUtils;
import com.api.common.utils.SecurityUtils;
import com.api.common.utils.StringUtils;
import com.api.system.domain.ApiPermission;
import com.api.system.mapper.ApiPermissionMapper;
import com.api.system.service.IApiPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * API权限Service业务层处理
 * 
 * @author api
 * @date 2023-01-01
 */
@Service
public class ApiPermissionServiceImpl implements IApiPermissionService {
    @Autowired
    private ApiPermissionMapper apiPermissionMapper;

    /**
     * 查询API权限
     * 
     * @param permissionId API权限主键
     * @return API权限
     */
    @Override
    public ApiPermission selectApiPermissionByPermissionId(Long permissionId) {
        return apiPermissionMapper.selectApiPermissionByPermissionId(permissionId);
    }

    /**
     * 查询API权限列表
     * 
     * @param apiPermission API权限
     * @return API权限
     */
    @Override
    public List<ApiPermission> selectApiPermissionList(ApiPermission apiPermission) {
        return apiPermissionMapper.selectApiPermissionList(apiPermission);
    }

    /**
     * 根据API ID查询权限列表
     * 
     * @param apiId API ID
     * @return 权限列表
     */
    @Override
    public List<ApiPermission> selectApiPermissionByApiId(Long apiId) {
        return apiPermissionMapper.selectApiPermissionByApiId(apiId);
    }

    /**
     * 根据角色ID查询权限列表
     * 
     * @param roleId 角色ID
     * @return 权限列表
     */
    @Override
    public List<ApiPermission> selectApiPermissionByRoleId(Long roleId) {
        return apiPermissionMapper.selectApiPermissionByRoleId(roleId);
    }

    /**
     * 新增API权限
     * 
     * @param apiPermission API权限
     * @return 结果
     */
    @Override
    public int insertApiPermission(ApiPermission apiPermission) {
        apiPermission.setCreateBy(SecurityUtils.getUserId());
        apiPermission.setCreateTime(DateUtils.getNowDate());
        return apiPermissionMapper.insertApiPermission(apiPermission);
    }

    /**
     * 修改API权限
     * 
     * @param apiPermission API权限
     * @return 结果
     */
    @Override
    public int updateApiPermission(ApiPermission apiPermission) {
        apiPermission.setUpdateBy(SecurityUtils.getUserId());
        apiPermission.setUpdateTime(DateUtils.getNowDate());
        return apiPermissionMapper.updateApiPermission(apiPermission);
    }

    /**
     * 批量删除API权限
     * 
     * @param permissionIds 需要删除的API权限主键
     * @return 结果
     */
    @Override
    public int deleteApiPermissionByPermissionIds(Long[] permissionIds) {
        return apiPermissionMapper.deleteApiPermissionByPermissionIds(permissionIds);
    }

    /**
     * 删除API权限信息
     * 
     * @param permissionId API权限主键
     * @return 结果
     */
    @Override
    public int deleteApiPermissionByPermissionId(Long permissionId) {
        return apiPermissionMapper.deleteApiPermissionByPermissionId(permissionId);
    }

    /**
     * 根据API ID删除权限
     * 
     * @param apiId API ID
     * @return 结果
     */
    @Override
    public int deleteApiPermissionByApiId(Long apiId) {
        return apiPermissionMapper.deleteApiPermissionByApiId(apiId);
    }

    /**
     * 根据角色ID删除权限
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public int deleteApiPermissionByRoleId(Long roleId) {
        return apiPermissionMapper.deleteApiPermissionByRoleId(roleId);
    }

    /**
     * 批量新增API权限
     * 
     * @param apiPermissions API权限列表
     * @return 结果
     */
    @Override
    public int insertApiPermissionBatch(List<ApiPermission> apiPermissions) {
        if (apiPermissions == null || apiPermissions.isEmpty()) {
            return 0;
        }
        
        // 设置创建时间和创建者
        for (ApiPermission permission : apiPermissions) {
            permission.setCreateBy(SecurityUtils.getUserId());
            permission.setCreateTime(DateUtils.getNowDate());
        }
        
        return apiPermissionMapper.insertApiPermissionBatch(apiPermissions);
    }

    /**
     * 批量删除API权限
     * 
     * @param apiPermissions API权限列表
     * @return 结果
     */
    @Override
    public int deleteApiPermissionBatch(List<ApiPermission> apiPermissions) {
        if (apiPermissions == null || apiPermissions.isEmpty()) {
            return 0;
        }
        
        return apiPermissionMapper.deleteApiPermissionBatch(apiPermissions);
    }

    /**
     * 校验API和角色权限是否已存在
     * 
     * @param apiId API ID
     * @param roleId 角色ID
     * @return API权限
     */
    @Override
    public ApiPermission checkApiRolePermission(Long apiId, Long roleId) {
        return apiPermissionMapper.checkApiRolePermission(apiId, roleId);
    }

    /**
     * 根据用户ID查询有权限的API列表
     * 
     * @param userId 用户ID
     * @return API列表
     */
    @Override
    public List<Long> selectApiIdListByUserId(Long userId) {
        return apiPermissionMapper.selectApiIdListByUserId(userId);
    }

    /**
     * 查询用户可访问的API
     * 
     * @param userId 用户ID
     * @param apiPath API路径
     * @param apiMethod 请求方法
     * @return 权限信息
     */
    @Override
    public ApiPermission checkUserApiPermission(Long userId, String apiPath, String apiMethod) {
        return apiPermissionMapper.checkUserApiPermission(userId, apiPath, apiMethod);
    }

    /**
     * 为角色分配API权限
     * 
     * @param roleId 角色ID
     * @param apiIds API ID列表
     * @param permissionType 权限类型
     * @return 结果
     */
    @Override
    public int assignApiPermissionsToRole(Long roleId, Long[] apiIds, String permissionType) {
        if (apiIds == null || apiIds.length == 0) {
            return 0;
        }
        
        List<ApiPermission> permissions = new ArrayList<>();
        for (Long apiId : apiIds) {
            // 检查是否已存在权限
            ApiPermission existPermission = checkApiRolePermission(apiId, roleId);
            if (existPermission == null) {
                ApiPermission permission = new ApiPermission();
                permission.setApiId(apiId);
                permission.setRoleId(roleId);
                permission.setPermissionType(permissionType);
                permission.setCreateBy(SecurityUtils.getUserId());
                permission.setCreateTime(DateUtils.getNowDate());
                permissions.add(permission);
            }
        }
        
        if (!permissions.isEmpty()) {
            return insertApiPermissionBatch(permissions);
        }
        
        return 0;
    }

    /**
     * 取消角色的API权限
     * 
     * @param roleId 角色ID
     * @param apiIds API ID列表
     * @return 结果
     */
    @Override
    public int revokeApiPermissionsFromRole(Long roleId, Long[] apiIds) {
        if (apiIds == null || apiIds.length == 0) {
            return 0;
        }
        
        int result = 0;
        for (Long apiId : apiIds) {
            ApiPermission permission = checkApiRolePermission(apiId, roleId);
            if (permission != null) {
                result += deleteApiPermissionByPermissionId(permission.getPermissionId());
            }
        }
        
        return result;
    }

    /**
     * 复制API权限
     * 
     * @param sourceRoleId 源角色ID
     * @param targetRoleId 目标角色ID
     * @return 结果
     */
    @Override
    public int copyApiPermissions(Long sourceRoleId, Long targetRoleId) {
        // 先删除目标角色的所有API权限
        deleteApiPermissionByRoleId(targetRoleId);
        
        // 查询源角色的所有API权限
        List<ApiPermission> sourcePermissions = selectApiPermissionByRoleId(sourceRoleId);
        
        if (sourcePermissions.isEmpty()) {
            return 0;
        }
        
        // 创建新的权限列表
        List<ApiPermission> targetPermissions = new ArrayList<>();
        for (ApiPermission sourcePermission : sourcePermissions) {
            ApiPermission targetPermission = new ApiPermission();
            targetPermission.setApiId(sourcePermission.getApiId());
            targetPermission.setRoleId(targetRoleId);
            targetPermission.setPermissionType(sourcePermission.getPermissionType());
            targetPermission.setCreateBy(SecurityUtils.getUserId());
            targetPermission.setCreateTime(DateUtils.getNowDate());
            targetPermissions.add(targetPermission);
        }
        
        return insertApiPermissionBatch(targetPermissions);
    }

    /**
     * 查询API权限统计信息
     * 
     * @param params 查询参数
     * @return 统计信息
     */
    @Override
    public Map<String, Object> selectApiPermissionStats(Map<String, Object> params) {
        // 这里可以实现统计逻辑，例如：
        // 1. 统计各角色的API权限数量
        // 2. 统计各API的权限分配情况
        // 3. 统计权限类型分布等
        
        // 由于Mapper中没有对应方法，这里返回空Map
        // 实际项目中可以根据需要添加相应的Mapper方法
        return Map.of();
    }
}