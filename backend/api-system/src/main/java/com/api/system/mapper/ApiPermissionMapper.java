package com.api.system.mapper;

import com.api.system.domain.ApiPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * API权限Mapper接口
 * 
 * @author api
 * @date 2023-01-01
 */
public interface ApiPermissionMapper {
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
     * 删除API权限
     * 
     * @param permissionId API权限主键
     * @return 结果
     */
    int deleteApiPermissionByPermissionId(Long permissionId);

    /**
     * 批量删除API权限
     * 
     * @param permissionIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteApiPermissionByPermissionIds(Long[] permissionIds);

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
    int insertApiPermissionBatch(@Param("apiPermissions") List<ApiPermission> apiPermissions);

    /**
     * 批量删除API权限
     * 
     * @param apiPermissions API权限列表
     * @return 结果
     */
    int deleteApiPermissionBatch(@Param("apiPermissions") List<ApiPermission> apiPermissions);

    /**
     * 校验API和角色权限是否已存在
     * 
     * @param apiId API ID
     * @param roleId 角色ID
     * @return API权限
     */
    ApiPermission checkApiRolePermission(@Param("apiId") Long apiId, @Param("roleId") Long roleId);

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
    ApiPermission checkUserApiPermission(@Param("userId") Long userId, 
                                    @Param("apiPath") String apiPath, 
                                    @Param("apiMethod") String apiMethod);
}