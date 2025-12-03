package com.api.system.controller;

import com.api.common.annotation.Log;
import com.api.common.core.controller.BaseController;
import com.api.common.core.domain.AjaxResult;
import com.api.common.core.page.TableDataInfo;
import com.api.common.enums.BusinessType;
import com.api.common.utils.poi.ExcelUtil;
import com.api.system.domain.ApiCallLog;
import com.api.system.domain.dto.ApiCallLogDto;
import com.api.system.domain.vo.ApiCallLogVo;
import com.api.system.service.IApiCallLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * API调用日志Controller
 * 
 * @author api
 * @date 2023-01-01
 */
@Tag(name = "API调用日志管理", description = "API调用日志管理相关接口")
@RestController
@RequestMapping("/api/manage/log")
public class ApiCallLogController extends BaseController {
    @Autowired
    private IApiCallLogService apiCallLogService;

    /**
     * 查询API调用日志列表
     */
    @Operation(summary = "查询API调用日志列表")
    @PreAuthorize("@ss.hasPermi('api:log:list')")
    @GetMapping("/list")
    public TableDataInfo list(ApiCallLog apiCallLog) {
        startPage();
        List<ApiCallLogVo> list = apiCallLogService.selectApiCallLogList(apiCallLog);
        return getDataTable(list);
    }

    /**
     * 导出API调用日志列表
     */
    @Operation(summary = "导出API调用日志列表")
    @PreAuthorize("@ss.hasPermi('api:log:export')")
    @Log(title = "API调用日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ApiCallLog apiCallLog) {
        List<ApiCallLogVo> list = apiCallLogService.selectApiCallLogList(apiCallLog);
        ExcelUtil<ApiCallLogVo> util = new ExcelUtil<ApiCallLogVo>(ApiCallLogVo.class);
        util.exportExcel(response, list, "API调用日志数据");
    }

    /**
     * 获取API调用日志详细信息
     */
    @Operation(summary = "获取API调用日志详细信息")
    @PreAuthorize("@ss.hasPermi('api:log:query')")
    @GetMapping(value = "/{logId}")
    public AjaxResult getInfo(
            @Parameter(description = "日志ID", required = true)
            @PathVariable Long logId) {
        return success(apiCallLogService.selectApiCallLogByLogId(logId));
    }

    /**
     * 删除API调用日志
     */
    @Operation(summary = "删除API调用日志")
    @PreAuthorize("@ss.hasPermi('api:log:remove')")
    @Log(title = "API调用日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{logIds}")
    public AjaxResult remove(
            @Parameter(description = "日志ID数组", required = true)
            @PathVariable Long[] logIds) {
        return toAjax(apiCallLogService.deleteApiCallLogByLogIds(logIds));
    }

    /**
     * 根据API ID查询调用日志列表
     */
    @Operation(summary = "根据API ID查询调用日志列表")
    @PreAuthorize("@ss.hasPermi('api:log:list')")
    @GetMapping("/api/{apiId}")
    public TableDataInfo listByApiId(
            @Parameter(description = "API ID", required = true)
            @PathVariable Long apiId) {
        startPage();
        List<ApiCallLogVo> list = apiCallLogService.selectApiCallLogByApiId(apiId);
        return getDataTable(list);
    }

    /**
     * 根据用户ID查询调用日志列表
     */
    @Operation(summary = "根据用户ID查询调用日志列表")
    @PreAuthorize("@ss.hasPermi('api:log:list')")
    @GetMapping("/user/{userId}")
    public TableDataInfo listByUserId(
            @Parameter(description = "用户ID", required = true)
            @PathVariable Long userId) {
        startPage();
        List<ApiCallLogVo> list = apiCallLogService.selectApiCallLogByUserId(userId);
        return getDataTable(list);
    }

    /**
     * 根据时间范围查询调用日志列表
     */
    @Operation(summary = "根据时间范围查询调用日志列表")
    @PreAuthorize("@ss.hasPermi('api:log:list')")
    @PostMapping("/timeRange")
    public TableDataInfo listByTimeRange(@RequestBody Map<String, Object> params) {
        startPage();
        Date startTime = (Date) params.get("startTime");
        Date endTime = (Date) params.get("endTime");
        List<ApiCallLogVo> list = apiCallLogService.selectApiCallLogByTimeRange(startTime, endTime);
        return getDataTable(list);
    }

    /**
     * 查询API调用统计信息
     */
    @Operation(summary = "查询API调用统计信息")
    @PreAuthorize("@ss.hasPermi('api:log:query')")
    @PostMapping("/stats")
    public AjaxResult stats(@RequestBody Map<String, Object> params) {
        Map<String, Object> stats = apiCallLogService.selectApiCallStats(params);
        return success(stats);
    }

    /**
     * 查询API调用趋势数据
     */
    @Operation(summary = "查询API调用趋势数据")
    @PreAuthorize("@ss.hasPermi('api:log:query')")
    @PostMapping("/trend")
    public AjaxResult trend(@RequestBody Map<String, Object> params) {
        List<Map<String, Object>> trend = apiCallLogService.selectApiCallTrend(params);
        return success(trend);
    }

    /**
     * 查询API调用排行榜
     */
    @Operation(summary = "查询API调用排行榜")
    @PreAuthorize("@ss.hasPermi('api:log:query')")
    @PostMapping("/ranking")
    public AjaxResult ranking(@RequestBody Map<String, Object> params) {
        List<Map<String, Object>> ranking = apiCallLogService.selectApiCallRanking(params);
        return success(ranking);
    }

    /**
     * 查询API错误统计
     */
    @Operation(summary = "查询API错误统计")
    @PreAuthorize("@ss.hasPermi('api:log:query')")
    @PostMapping("/errorStats")
    public AjaxResult errorStats(@RequestBody Map<String, Object> params) {
        List<Map<String, Object>> errorStats = apiCallLogService.selectApiErrorStats(params);
        return success(errorStats);
    }

    /**
     * 查询API响应时间统计
     */
    @Operation(summary = "查询API响应时间统计")
    @PreAuthorize("@ss.hasPermi('api:log:query')")
    @PostMapping("/responseTimeStats")
    public AjaxResult responseTimeStats(@RequestBody Map<String, Object> params) {
        List<Map<String, Object>> responseTimeStats = apiCallLogService.selectApiResponseTimeStats(params);
        return success(responseTimeStats);
    }

    /**
     * 清空指定时间之前的日志
     */
    @Operation(summary = "清空指定时间之前的日志")
    @PreAuthorize("@ss.hasPermi('api:log:remove')")
    @Log(title = "API调用日志", businessType = BusinessType.DELETE)
    @PostMapping("/clean")
    public AjaxResult clean(@RequestBody Map<String, Object> params) {
        Date beforeTime = (Date) params.get("beforeTime");
        return toAjax(apiCallLogService.deleteApiCallLogBeforeTime(beforeTime));
    }
}