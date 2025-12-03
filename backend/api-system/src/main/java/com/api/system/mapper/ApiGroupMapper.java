package com.api.system.mapper;

import com.api.system.domain.ApiGroup;
import com.api.system.domain.vo.ApiGroupVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * API分组Mapper接口
 * 
 * @author api
 * @date 2023-01-01
 */
public interface ApiGroupMapper {
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
     * 删除API分组
     * 
     * @param groupId API分组主键
     * @return 结果
     */
    int deleteApiGroupByGroupId(Long groupId);

    /**
     * 批量删除API分组
     * 
     * @param groupIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteApiGroupByGroupIds(Long[] groupIds);

    /**
     * 根据父分组ID查询子分组数量
     * 
     * @param parentId 父分组ID
     * @return 子分组数量
     */
    int selectChildGroupCountByParentId(Long parentId);

    /**
     * 根据分组ID查询API数量
     * 
     * @param groupId 分组ID
     * @return API数量
     */
    int selectApiCountByGroupId(Long groupId);

    /**
     * 校验分组编码是否唯一
     * 
     * @param groupCode 分组编码
     * @param groupId 分组ID
     * @return 结果
     */
    ApiGroup checkGroupCodeUnique(@Param("groupCode") String groupCode, @Param("groupId") Long groupId);

    /**
     * 根据ID查询所有子分组
     * 
     * @param groupId 分组ID
     * @return 子分组列表
     */
    List<ApiGroupVo> selectChildrenGroupById(Long groupId);

    /**
     * 根据分组ID列表查询分组信息
     * 
     * @param groupIds 分组ID列表
     * @return 分组信息列表
     */
    List<ApiGroupVo> selectApiGroupByIds(Long[] groupIds);
}