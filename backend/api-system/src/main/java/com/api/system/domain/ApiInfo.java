package com.api.system.domain;

import com.api.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

/**
 * API信息对象 api_info
 * 
 * @author api
 * @date 2023-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ApiInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** API ID */
    private Long apiId;

    /** API名称 */
    @NotBlank(message = "API名称不能为空")
    @Size(min = 0, max = 100, message = "API名称长度不能超过100个字符")
    private String apiName;

    /** API路径 */
    @NotBlank(message = "API路径不能为空")
    @Size(min = 0, max = 200, message = "API路径长度不能超过200个字符")
    private String apiPath;

    /** 请求方法 */
    @NotBlank(message = "请求方法不能为空")
    @Size(min = 0, max = 10, message = "请求方法长度不能超过10个字符")
    private String apiMethod;

    /** 所属分组ID */
    private Long groupId;

    /** API版本 */
    @Size(min = 0, max = 20, message = "API版本长度不能超过20个字符")
    private String apiVersion;

    /** API描述 */
    @Size(min = 0, max = 500, message = "API描述长度不能超过500个字符")
    private String apiDescription;

    /** 请求示例 */
    private String requestExample;

    /** 响应示例 */
    private String responseExample;

    /** 状态（0正常 1停用 2调试中） */
    private String status;

    /** 是否公开（0否 1是） */
    private String isPublic;

    /** 调用次数 */
    private Long callCount;

    /** 成功次数 */
    private Long successCount;

    /** 错误次数 */
    private Long errorCount;

    /** 平均响应时间(毫秒) */
    private BigDecimal avgResponseTime;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 分组名称 */
    private String groupName;

    /** API参数列表 */
    private List<ApiParam> paramList;

    /** API权限列表 */
    private List<ApiPermission> permissionList;
}