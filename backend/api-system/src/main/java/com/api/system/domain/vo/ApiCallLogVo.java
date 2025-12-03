package com.api.system.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * API调用日志视图对象
 * 
 * @author api
 * @date 2023-01-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiCallLogVo {
    private static final long serialVersionUID = 1L;

    /** 日志ID */
    private Long logId;

    /** API ID */
    private Long apiId;

    /** API名称 */
    private String apiName;

    /** API路径 */
    private String apiPath;

    /** 分组名称 */
    private String groupName;

    /** 调用用户ID */
    private Long userId;

    /** 调用用户名 */
    private String userName;

    /** 请求IP */
    private String requestIp;

    /** 请求方法 */
    private String requestMethod;

    /** 请求URL */
    private String requestUrl;

    /** 请求参数 */
    private String requestParams;

    /** 请求参数（格式化后的） */
    private String formatRequestParams;

    /** 响应状态码 */
    private Integer responseStatus;

    /** 响应数据 */
    private String responseData;

    /** 响应数据（格式化后的） */
    private String formatResponseData;

    /** 响应时间(毫秒) */
    private BigDecimal responseTime;

    /** 响应时间（带单位） */
    private String formatResponseTime;

    /** 是否成功（0失败 1成功） */
    private String isSuccess;

    /** 是否成功描述 */
    private String successDesc;

    /** 错误信息 */
    private String errorMessage;

    /** 调用时间 */
    private Date callTime;

    /** 状态描述 */
    private String statusDesc;

    /** 请求方法颜色 */
    private String methodColor;

    /** 响应状态码颜色 */
    private String statusColor;
}