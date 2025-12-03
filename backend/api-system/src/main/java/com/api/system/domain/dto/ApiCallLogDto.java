package com.api.system.domain.dto;

import com.api.system.domain.ApiCallLog;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * API调用日志数据传输对象
 * 
 * @author api
 * @date 2023-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ApiCallLogDto extends ApiCallLog {
    private static final long serialVersionUID = 1L;

    /** 分组名称 */
    private String groupName;

    /** API名称 */
    private String apiName;

    /** 请求参数（格式化后的） */
    private String formatRequestParams;

    /** 响应数据（格式化后的） */
    private String formatResponseData;

    /** 响应时间（带单位） */
    private String formatResponseTime;

    /** 状态描述 */
    private String statusDesc;

    /** 成功率 */
    private BigDecimal successRate;

    /** 错误率 */
    private BigDecimal errorRate;

    /** 是否成功描述 */
    private String successDesc;

    /** 调用时间范围（开始时间） */
    private Date startTime;

    /** 调用时间范围（结束时间） */
    private Date endTime;

    /** 响应状态码范围（最小值） */
    private Integer minResponseStatus;

    /** 响应状态码范围（最大值） */
    private Integer maxResponseStatus;
}