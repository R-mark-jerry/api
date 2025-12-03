package com.api.system.service;

import com.api.system.domain.ApiPermission;

import java.util.List;
import java.util.Map;

/**
 * API权限Service接口
 * 
 * @author api
 * @date 2023-01-01
 */
public interface IApiPermissionService {
    /**
     * 查询API权限
     * 
     * @param permissionId API权限主键
     * @return API权限
     */
    ApiPermission selectApiPermissionByPermissionId(Long permissionId);

    /**
     * 查询API权限列表
     * 
     * @param apiPermission API权限
     * @return API权限集合
     */
    List<ApiPermission> selectApiPermissionList(ApiPermission apiPermission);

    /**
     * 根据API ID查询权限列表
     * 
     * @param apiId API ID
     * @return 权限列表
     */
    List<ApiPermission> selectApiPermissionByApiId(Long apiId);

    /**
     * 根据角色ID查询权限列表
     * 
     * @param roleId 角色ID
     * @return 权限列表
     */
    List<ApiPermission> selectApiPermissionByRoleId(Long roleId);

    /**
     * 新增API权限
     * 
     * @param apiPermission API权限
     * @return 结果
     */
    int insertApiPermission(ApiPermission apiPermission);

    /**
     * 修改API权限
     * 
     * @param apiPermission API权限
     * @return 结果
     */
    int updateApiPermission(ApiPermission apiPermission);

    /**
     * 批量删除API权限
     * 
     * @param permissionIds 需要删除的API权限主键集合
     * @return 结果
     */
    int deleteApiPermissionByPermissionIds(Long[] permissionIds);

    /**
     * 删除API权限信息
     * 
     * @param permissionId API权限主键
     * @return 结果
     */
    int deleteApiPermissionByPermissionId(Long permissionId);

    /**
     * 根据API ID删除权限
     * 
     * @param apiId API ID
     * @return 结果
     */
    int deleteApiPermissionByApiId(Long apiId);

    /**
     * 根据角色ID删除权限
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    int deleteApiPermissionByRoleId(Long roleId);

    /**
     * 批量新增API权限
     * 
     * @param apiPermissions API权限列表
     * @return 结果
     */
    int insertApiPermissionBatch(List<ApiPermission> apiPermissions);

    /**
     * 批量删除API权限
     * 
     * @param apiPermissions API权限列表
     * @return 结果
     */
    int deleteApiPermissionBatch(List<ApiPermission> apiPermissions);

    /**
     * 校验API和角色权限是否已存在
     * 
     * @param apiId API ID
     * @param roleId 角色ID
     * @return API权限
     */
    ApiPermission checkApiRolePermission(Long apiId, Long roleId);

    /**
     * 根据用户ID查询有权限的API列表
     * 
     * @param userId 用户ID
     * @return API列表
     */
    List<Long> selectApiIdListByUserId(Long userId);

    /**
     * 查询用户可访问的API
     * 
     * @param userId 用户ID
     * @param apiPath API路径
     * @param apiMethod 请求方法
     * @return 权限信息
     */
    ApiPermission checkUserApiPermission(Long userId, String apiPath, String apiMethod);

    /**
     * 为角色分配API权限
     * 
     * @param roleId 角色ID
     * @param apiIds API ID列表
     * @param permissionType 权限类型
     * @return 结果
     */
    int assignApiPermissionsToRole(Long roleId, Long[] apiIds, String permissionType);

    /**
     * 取消角色的API权限
     * 
     * @param roleId 角色ID
     * @param apiIds API ID列表
     * @return 结果
     */
    int revokeApiPermissionsFromRole(Long roleId, Long[] apiIds);

    /**
     * 复制API权限
     * 
     * @param sourceRoleId 源角色ID
     * @param targetRoleId 目标角色ID
     * @return 结果
     */
    int copyApiPermissions(Long sourceRoleId, Long targetRoleId);

    /**
     * 查询API权限统计信息
     * 
     * @param params 查询参数
     * @return 统计信息
     */
    Map<String, Object> selectApiPermissionStats(Map<String, Object> params);
}