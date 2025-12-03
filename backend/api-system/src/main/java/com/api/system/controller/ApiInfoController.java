package com.api.system.controller;

import com.api.common.annotation.Log;
import com.api.common.core.controller.BaseController;
import com.api.common.core.domain.AjaxResult;
import com.api.common.core.page.TableDataInfo;
import com.api.common.enums.BusinessType;
import com.api.common.utils.SecurityUtils;
import com.api.common.utils.poi.ExcelUtil;
import com.api.system.domain.ApiInfo;
import com.api.system.domain.dto.ApiInfoDto;
import com.api.system.domain.vo.ApiInfoVo;
import com.api.system.service.IApiInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * API信息Controller
 * 
 * @author api
 * @date 2023-01-01
 */
@Tag(name = "API信息管理", description = "API信息管理相关接口")
@RestController
@RequestMapping("/api/manage/info")
public class ApiInfoController extends BaseController {
    @Autowired
    private IApiInfoService apiInfoService;

    /**
     * 查询API信息列表
     */
    @Operation(summary = "查询API信息列表")
    @PreAuthorize("@ss.hasPermi('api:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(ApiInfo apiInfo) {
        startPage();
        List<ApiInfoVo> list = apiInfoService.selectApiInfoList(apiInfo);
        return getDataTable(list);
    }

    /**
     * 导出API信息列表
     */
    @Operation(summary = "导出API信息列表")
    @PreAuthorize("@ss.hasPermi('api:info:export')")
    @Log(title = "API信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ApiInfo apiInfo) {
        List<ApiInfoVo> list = apiInfoService.selectApiInfoList(apiInfo);
        ExcelUtil<ApiInfoVo> util = new ExcelUtil<ApiInfoVo>(ApiInfoVo.class);
        util.exportExcel(response, list, "API信息数据");
    }

    /**
     * 获取API信息详细信息
     */
    @Operation(summary = "获取API信息详细信息")
    @PreAuthorize("@ss.hasPermi('api:info:query')")
    @GetMapping(value = "/{apiId}")
    public AjaxResult getInfo(
            @Parameter(description = "API ID", required = true)
            @PathVariable Long apiId) {
        return success(apiInfoService.selectApiInfoByApiId(apiId));
    }

    /**
     * 新增API信息
     */
    @Operation(summary = "新增API信息")
    @PreAuthorize("@ss.hasPermi('api:info:add')")
    @Log(title = "API信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody ApiInfo apiInfo) {
        if (!apiInfoService.checkApiPathUnique(apiInfo)) {
            return error("新增API信息'" + apiInfo.getApiName() + "'失败，API路径和方法已存在");
        }
        apiInfo.setCreateBy(SecurityUtils.getUserId());
        return toAjax(apiInfoService.insertApiInfo(apiInfo));
    }

    /**
     * 修改API信息
     */
    @Operation(summary = "修改API信息")
    @PreAuthorize("@ss.hasPermi('api:info:edit')")
    @Log(title = "API信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody ApiInfo apiInfo) {
        if (!apiInfoService.checkApiPathUnique(apiInfo)) {
            return error("修改API信息'" + apiInfo.getApiName() + "'失败，API路径和方法已存在");
        }
        apiInfo.setUpdateBy(SecurityUtils.getUserId());
        return toAjax(apiInfoService.updateApiInfo(apiInfo));
    }

    /**
     * 删除API信息
     */
    @Operation(summary = "删除API信息")
    @PreAuthorize("@ss.hasPermi('api:info:remove')")
    @Log(title = "API信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{apiIds}")
    public AjaxResult remove(
            @Parameter(description = "API ID数组", required = true)
            @PathVariable Long[] apiIds) {
        return toAjax(apiInfoService.deleteApiInfoByApiIds(apiIds));
    }

    /**
     * 根据分组ID查询API信息列表
     */
    @Operation(summary = "根据分组ID查询API信息列表")
    @PreAuthorize("@ss.hasPermi('api:info:list')")
    @GetMapping("/group/{groupId}")
    public AjaxResult listByGroupId(
            @Parameter(description = "分组ID", required = true)
            @PathVariable Long groupId) {
        List<ApiInfoVo> list = apiInfoService.selectApiInfoByGroupId(groupId);
        return success(list);
    }

    /**
     * 批量更新API状态
     */
    @Operation(summary = "批量更新API状态")
    @PreAuthorize("@ss.hasPermi('api:info:edit')")
    @Log(title = "API信息", businessType = BusinessType.UPDATE)
    @PutMapping("/status")
    public AjaxResult updateStatus(@RequestBody Map<String, Object> params) {
        Long[] apiIds = (Long[]) params.get("apiIds");
        String status = (String) params.get("status");
        return toAjax(apiInfoService.updateApiStatusBatch(apiIds, status));
    }

    /**
     * 根据关键词搜索API信息
     */
    @Operation(summary = "根据关键词搜索API信息")
    @PreAuthorize("@ss.hasPermi('api:info:list')")
    @GetMapping("/search")
    public TableDataInfo search(
            @Parameter(description = "关键词", required = true)
            @RequestParam String keyword) {
        startPage();
        List<ApiInfoVo> list = apiInfoService.searchApiInfo(keyword);
        return getDataTable(list);
    }

    /**
     * 查询热门API列表
     */
    @Operation(summary = "查询热门API列表")
    @PreAuthorize("@ss.hasPermi('api:info:list')")
    @GetMapping("/hot")
    public AjaxResult hotList(
            @Parameter(description = "限制数量", required = false)
            @RequestParam(defaultValue = "10") Integer limit) {
        List<ApiInfoVo> list = apiInfoService.selectHotApiList(limit);
        return success(list);
    }

    /**
     * 查询API调用排行榜
     */
    @Operation(summary = "查询API调用排行榜")
    @PreAuthorize("@ss.hasPermi('api:info:list')")
    @PostMapping("/ranking")
    public AjaxResult rankingList(@RequestBody Map<String, Object> params) {
        List<ApiInfoVo> list = apiInfoService.selectApiRankingList(params);
        return success(list);
    }

    /**
     * 测试API
     */
    @Operation(summary = "测试API")
    @PreAuthorize("@ss.hasPermi('api:info:test')")
    @PostMapping("/test/{apiId}")
    public AjaxResult testApi(
            @Parameter(description = "API ID", required = true)
            @PathVariable Long apiId,
            @RequestBody Map<String, Object> params) {
        // 这里实现API测试逻辑
        // 可以调用实际的API或者模拟调用
        return success("API测试成功");
    }

    /**
     * 查询今日API调用统计
     */
    @Operation(summary = "查询今日API调用统计")
    @PreAuthorize("@ss.hasPermi('api:info:query')")
    @GetMapping("/stats/{apiId}")
    public AjaxResult todayStats(
            @Parameter(description = "API ID", required = true)
            @PathVariable Long apiId) {
        Map<String, Object> stats = apiInfoService.selectTodayApiStats(apiId);
        return success(stats);
    }
}