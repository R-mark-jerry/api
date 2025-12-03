package com.api.system.domain.vo;

import com.api.system.domain.ApiParam;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * API信息视图对象
 * 
 * @author api
 * @date 2023-01-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiInfoVo {
    private static final long serialVersionUID = 1L;

    /** API ID */
    private Long apiId;

    /** API名称 */
    private String apiName;

    /** API路径 */
    private String apiPath;

    /** 请求方法 */
    private String apiMethod;

    /** 所属分组ID */
    private Long groupId;

    /** 分组名称 */
    private String groupName;

    /** API版本 */
    private String apiVersion;

    /** API描述 */
    private String apiDescription;

    /** 请求示例 */
    private String requestExample;

    /** 响应示例 */
    private String responseExample;

    /** 状态（0正常 1停用 2调试中） */
    private String status;

    /** 状态描述 */
    private String statusDesc;

    /** 是否公开（0否 1是） */
    private String isPublic;

    /** 是否公开描述 */
    private String isPublicDesc;

    /** 调用次数 */
    private Long callCount;

    /** 成功次数 */
    private Long successCount;

    /** 错误次数 */
    private Long errorCount;

    /** 平均响应时间(毫秒) */
    private BigDecimal avgResponseTime;

    /** 成功率 */
    private BigDecimal successRate;

    /** 错误率 */
    private BigDecimal errorRate;

    /** 今日调用次数 */
    private Long todayCallCount;

    /** 今日成功次数 */
    private Long todaySuccessCount;

    /** 今日错误次数 */
    private Long todayErrorCount;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 创建者 */
    private Long createBy;

    /** 创建时间 */
    private Date createTime;

    /** 更新者 */
    private Long updateBy;

    /** 更新时间 */
    private Date updateTime;

    /** 备注 */
    private String remark;

    /** 参数列表 */
    private List<ApiParam> paramList;

    /** 角色ID列表 */
    private List<Long> roleIds;

    /** 是否可以编辑 */
    private Boolean canEdit;

    /** 是否可以删除 */
    private Boolean canDelete;

    /** 是否可以测试 */
    private Boolean canTest;

    /** 请求方法颜色 */
    private String methodColor;
}