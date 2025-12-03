package com.api.system.service.impl;

import com.api.common.constant.Constants;
import com.api.common.constant.UserConstants;
import com.api.common.core.domain.AjaxResult;
import com.api.common.core.text.Convert;
import com.api.common.exception.ServiceException;
import com.api.common.utils.DateUtils;
import com.api.common.utils.SecurityUtils;
import com.api.common.utils.StringUtils;
import com.api.system.domain.ApiGroup;
import com.api.system.domain.dto.ApiGroupDto;
import com.api.system.domain.vo.ApiGroupVo;
import com.api.system.mapper.ApiGroupMapper;
import com.api.system.service.IApiGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * API分组Service业务层处理
 * 
 * @author api
 * @date 2023-01-01
 */
@Service
public class ApiGroupServiceImpl implements IApiGroupService {
    @Autowired
    private ApiGroupMapper apiGroupMapper;

    /**
     * 查询API分组
     * 
     * @param groupId API分组主键
     * @return API分组
     */
    @Override
    public ApiGroupVo selectApiGroupByGroupId(Long groupId) {
        return apiGroupMapper.selectApiGroupByGroupId(groupId);
    }

    /**
     * 查询API分组列表
     * 
     * @param apiGroup API分组
     * @return API分组
     */
    @Override
    public List<ApiGroupVo> selectApiGroupList(ApiGroup apiGroup) {
        return apiGroupMapper.selectApiGroupList(apiGroup);
    }

    /**
     * 查询API分组树结构
     * 
     * @param apiGroup API分组
     * @return API分组树结构集合
     */
    @Override
    public List<ApiGroupVo> selectApiGroupTree(ApiGroup apiGroup) {
        List<ApiGroupVo> groups = apiGroupMapper.selectApiGroupList(apiGroup);
        return buildApiGroupTree(groups);
    }

    /**
     * 新增API分组
     * 
     * @param apiGroup API分组
     * @return 结果
     */
    @Override
    public int insertApiGroup(ApiGroup apiGroup) {
        ApiGroup info = apiGroupMapper.checkGroupCodeUnique(apiGroup.getGroupCode(), null);
        if (StringUtils.isNotNull(info)) {
            throw new ServiceException("新增API分组'" + apiGroup.getGroupName() + "'失败，分组编码已存在");
        }
        apiGroup.setCreateBy(SecurityUtils.getUserId());
        apiGroup.setCreateTime(DateUtils.getNowDate());
        return apiGroupMapper.insertApiGroup(apiGroup);
    }

    /**
     * 修改API分组
     * 
     * @param apiGroup API分组
     * @return 结果
     */
    @Override
    public int updateApiGroup(ApiGroup apiGroup) {
        ApiGroup info = apiGroupMapper.checkGroupCodeUnique(apiGroup.getGroupCode(), apiGroup.getGroupId());
        if (StringUtils.isNotNull(info)) {
            throw new ServiceException("修改API分组'" + apiGroup.getGroupName() + "'失败，分组编码已存在");
        }
        apiGroup.setUpdateBy(SecurityUtils.getUserId());
        apiGroup.setUpdateTime(DateUtils.getNowDate());
        return apiGroupMapper.updateApiGroup(apiGroup);
    }

    /**
     * 批量删除API分组
     * 
     * @param groupIds 需要删除的API分组主键
     * @return 结果
     */
    @Override
    public int deleteApiGroupByGroupIds(Long[] groupIds) {
        return apiGroupMapper.deleteApiGroupByGroupIds(groupIds);
    }

    /**
     * 删除API分组信息
     * 
     * @param groupId API分组主键
     * @return 结果
     */
    @Override
    public int deleteApiGroupByGroupId(Long groupId) {
        return apiGroupMapper.deleteApiGroupByGroupId(groupId);
    }

    /**
     * 校验分组编码是否唯一
     * 
     * @param apiGroup API分组信息
     * @return 结果
     */
    @Override
    public boolean checkGroupCodeUnique(ApiGroup apiGroup) {
        Long groupId = StringUtils.isNull(apiGroup.getGroupId()) ? -1L : apiGroup.getGroupId();
        ApiGroup info = apiGroupMapper.checkGroupCodeUnique(apiGroup.getGroupCode(), groupId);
        if (StringUtils.isNotNull(info) && info.getGroupId().longValue() != groupId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 构建前端所需要树结构
     * 
     * @param groups 分组列表
     * @return 树结构列表
     */
    @Override
    public List<ApiGroupVo> buildApiGroupTree(List<ApiGroupVo> groups) {
        List<ApiGroupVo> returnList = new ArrayList<ApiGroupVo>();
        List<Long> tempList = groups.stream().map(ApiGroupVo::getGroupId).collect(Collectors.toList());
        for (Iterator<ApiGroupVo> iterator = groups.iterator(); iterator.hasNext();) {
            ApiGroupVo group = (ApiGroupVo) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(group.getParentId())) {
                recursionFn(groups, group);
                returnList.add(group);
            }
        }
        if (returnList.isEmpty()) {
            returnList = groups;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<ApiGroupVo> list, ApiGroupVo t) {
        // 得到子节点列表
        List<ApiGroupVo> childList = getChildList(list, t);
        t.setChildren(childList);
        for (ApiGroupVo tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<ApiGroupVo> getChildList(List<ApiGroupVo> list, ApiGroupVo t) {
        List<ApiGroupVo> tlist = new ArrayList<ApiGroupVo>();
        Iterator<ApiGroupVo> it = list.iterator();
        while (it.hasNext()) {
            ApiGroupVo n = (ApiGroupVo) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getGroupId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<ApiGroupVo> list, ApiGroupVo t) {
        return getChildList(list, t).size() > 0;
    }

    /**
     * 根据分组ID查询所有子分组
     * 
     * @param groupId 分组ID
     * @return 子分组列表
     */
    @Override
    public List<ApiGroupVo> selectChildrenGroupById(Long groupId) {
        return apiGroupMapper.selectChildrenGroupById(groupId);
    }

    /**
     * 查询分组是否存在子分组
     * 
     * @param groupId 分组ID
     * @return 结果
     */
    @Override
    public boolean hasChildByGroupId(Long groupId) {
        int result = apiGroupMapper.selectChildGroupCountByParentId(groupId);
        return result > 0;
    }

    /**
     * 查询分组是否存在API
     * 
     * @param groupId 分组ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkGroupExistApi(Long groupId) {
        int result = apiGroupMapper.selectApiCountByGroupId(groupId);
        return result > 0;
    }

    /**
     * 根据分组ID列表查询分组信息
     * 
     * @param groupIds 分组ID列表
     * @return 分组信息列表
     */
    @Override
    public List<ApiGroupVo> selectApiGroupByIds(Long[] groupIds) {
        return apiGroupMapper.selectApiGroupByIds(groupIds);
    }
}