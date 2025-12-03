package com.api.system.service;

import com.api.system.domain.ApiGroup;
import com.api.system.domain.dto.ApiGroupDto;
import com.api.system.domain.vo.ApiGroupVo;

import java.util.List;

/**
 * API分组Service接口
 * 
 * @author api
 * @date 2023-01-01
 */
public interface IApiGroupService {
    /**
     * 查询API分组
     * 
     * @param groupId API分组主键
     * @return API分组
     */
    ApiGroupVo selectApiGroupByGroupId(Long groupId);

    /**
     * 查询API分组列表
     * 
     * @param apiGroup API分组
     * @return API分组集合
     */
    List<ApiGroupVo> selectApiGroupList(ApiGroup apiGroup);

    /**
     * 查询API分组树结构
     * 
     * @param apiGroup API分组
     * @return API分组树结构集合
     */
    List<ApiGroupVo> selectApiGroupTree(ApiGroup apiGroup);

    /**
     * 新增API分组
     * 
     * @param apiGroup API分组
     * @return 结果
     */
    int insertApiGroup(ApiGroup apiGroup);

    /**
     * 修改API分组
     * 
     * @param apiGroup API分组
     * @return 结果
     */
    int updateApiGroup(ApiGroup apiGroup);

    /**
     * 批量删除API分组
     * 
     * @param groupIds 需要删除的API分组主键集合
     * @return 结果
     */
    int deleteApiGroupByGroupIds(Long[] groupIds);

    /**
     * 删除API分组信息
     * 
     * @param groupId API分组主键
     * @return 结果
     */
    int deleteApiGroupByGroupId(Long groupId);

    /**
     * 校验分组编码是否唯一
     * 
     * @param apiGroup API分组信息
     * @return 结果
     */
    boolean checkGroupCodeUnique(ApiGroup apiGroup);

    /**
     * 构建前端所需要树结构
     * 
     * @param groups 分组列表
     * @return 树结构列表
     */
    List<ApiGroupVo> buildApiGroupTree(List<ApiGroupVo> groups);

    /**
     * 根据分组ID查询所有子分组
     * 
     * @param groupId 分组ID
     * @return 子分组列表
     */
    List<ApiGroupVo> selectChildrenGroupById(Long groupId);

    /**
     * 查询分组是否存在子分组
     * 
     * @param groupId 分组ID
     * @return 结果 true 存在 false 不存在
     */
    boolean hasChildByGroupId(Long groupId);

    /**
     * 查询分组是否存在API
     * 
     * @param groupId 分组ID
     * @return 结果 true 存在 false 不存在
     */
    boolean checkGroupExistApi(Long groupId);

    /**
     * 根据分组ID列表查询分组信息
     * 
     * @param groupIds 分组ID列表
     * @return 分组信息列表
     */
    List<ApiGroupVo> selectApiGroupByIds(Long[] groupIds);
}