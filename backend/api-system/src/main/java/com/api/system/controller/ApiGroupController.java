package com.api.system.controller;

import com.api.common.annotation.Log;
import com.api.common.core.controller.BaseController;
import com.api.common.core.domain.AjaxResult;
import com.api.common.core.page.TableDataInfo;
import com.api.common.enums.BusinessType;
import com.api.common.utils.SecurityUtils;
import com.api.common.utils.poi.ExcelUtil;
import com.api.system.domain.ApiGroup;
import com.api.system.domain.vo.ApiGroupVo;
import com.api.system.service.IApiGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * API分组Controller
 * 
 * @author api
 * @date 2023-01-01
 */
@Tag(name = "API分组管理", description = "API分组管理相关接口")
@RestController
@RequestMapping("/api/manage/group")
public class ApiGroupController extends BaseController {
    @Autowired
    private IApiGroupService apiGroupService;

    /**
     * 查询API分组列表
     */
    @Operation(summary = "查询API分组列表")
    @PreAuthorize("@ss.hasPermi('api:group:list')")
    @GetMapping("/list")
    public TableDataInfo list(ApiGroup apiGroup) {
        startPage();
        List<ApiGroupVo> list = apiGroupService.selectApiGroupList(apiGroup);
        return getDataTable(list);
    }

    /**
     * 导出API分组列表
     */
    @Operation(summary = "导出API分组列表")
    @PreAuthorize("@ss.hasPermi('api:group:export')")
    @Log(title = "API分组", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ApiGroup apiGroup) {
        List<ApiGroupVo> list = apiGroupService.selectApiGroupList(apiGroup);
        ExcelUtil<ApiGroupVo> util = new ExcelUtil<ApiGroupVo>(ApiGroupVo.class);
        util.exportExcel(response, list, "API分组数据");
    }

    /**
     * 获取API分组详细信息
     */
    @Operation(summary = "获取API分组详细信息")
    @PreAuthorize("@ss.hasPermi('api:group:query')")
    @GetMapping(value = "/{groupId}")
    public AjaxResult getInfo(
            @Parameter(description = "分组ID", required = true)
            @PathVariable Long groupId) {
        return success(apiGroupService.selectApiGroupByGroupId(groupId));
    }

    /**
     * 获取API分组树形列表
     */
    @Operation(summary = "获取API分组树形列表")
    @PreAuthorize("@ss.hasPermi('api:group:list')")
    @GetMapping("/tree")
    public AjaxResult tree(ApiGroup apiGroup) {
        List<ApiGroupVo> groups = apiGroupService.selectApiGroupTree(apiGroup);
        return success(groups);
    }

    /**
     * 新增API分组
     */
    @Operation(summary = "新增API分组")
    @PreAuthorize("@ss.hasPermi('api:group:add')")
    @Log(title = "API分组", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody ApiGroup apiGroup) {
        if (!apiGroupService.checkGroupCodeUnique(apiGroup)) {
            return error("新增API分组'" + apiGroup.getGroupName() + "'失败，分组编码已存在");
        }
        apiGroup.setCreateBy(SecurityUtils.getUserId());
        return toAjax(apiGroupService.insertApiGroup(apiGroup));
    }

    /**
     * 修改API分组
     */
    @Operation(summary = "修改API分组")
    @PreAuthorize("@ss.hasPermi('api:group:edit')")
    @Log(title = "API分组", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody ApiGroup apiGroup) {
        if (!apiGroupService.checkGroupCodeUnique(apiGroup)) {
            return error("修改API分组'" + apiGroup.getGroupName() + "'失败，分组编码已存在");
        }
        apiGroup.setUpdateBy(SecurityUtils.getUserId());
        return toAjax(apiGroupService.updateApiGroup(apiGroup));
    }

    /**
     * 删除API分组
     */
    @Operation(summary = "删除API分组")
    @PreAuthorize("@ss.hasPermi('api:group:remove')")
    @Log(title = "API分组", businessType = BusinessType.DELETE)
    @DeleteMapping("/{groupIds}")
    public AjaxResult remove(
            @Parameter(description = "分组ID数组", required = true)
            @PathVariable Long[] groupIds) {
        return toAjax(apiGroupService.deleteApiGroupByGroupIds(groupIds));
    }

    /**
     * 根据分组ID查询所有子分组
     */
    @Operation(summary = "根据分组ID查询所有子分组")
    @PreAuthorize("@ss.hasPermi('api:group:list')")
    @GetMapping("/children/{groupId}")
    public AjaxResult listChildren(
            @Parameter(description = "分组ID", required = true)
            @PathVariable Long groupId) {
        List<ApiGroupVo> children = apiGroupService.selectChildrenGroupById(groupId);
        return success(children);
    }

    /**
     * 查询分组是否存在子分组
     */
    @Operation(summary = "查询分组是否存在子分组")
    @PreAuthorize("@ss.hasPermi('api:group:list')")
    @GetMapping("/hasChild/{groupId}")
    public AjaxResult hasChild(
            @Parameter(description = "分组ID", required = true)
            @PathVariable Long groupId) {
        boolean result = apiGroupService.hasChildByGroupId(groupId);
        return success(result);
    }

    /**
     * 查询分组是否存在API
     */
    @Operation(summary = "查询分组是否存在API")
    @PreAuthorize("@ss.hasPermi('api:group:list')")
    @GetMapping("/hasApi/{groupId}")
    public AjaxResult hasApi(
            @Parameter(description = "分组ID", required = true)
            @PathVariable Long groupId) {
        boolean result = apiGroupService.checkGroupExistApi(groupId);
        return success(result);
    }
}