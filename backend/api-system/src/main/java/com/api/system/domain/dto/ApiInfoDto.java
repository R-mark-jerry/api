package com.api.system.domain.dto;

import com.api.system.domain.ApiInfo;
import com.api.system.domain.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

/**
 * API信息数据传输对象
 * 
 * @author api
 * @date 2023-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ApiInfoDto extends ApiInfo {
    private static final long serialVersionUID = 1L;

    /** 分组名称 */
    private String groupName;

    /** 参数列表 */
    private List<ApiParam> paramList;

    /** 角色ID列表（用于权限设置） */
    private List<Long> roleIds;

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

    /** 是否可以编辑 */
    private Boolean canEdit;

    /** 是否可以删除 */
    private Boolean canDelete;

    /** 是否可以测试 */
    private Boolean canTest;
}