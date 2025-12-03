package com.api.system.domain;

import com.api.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * API分组对象 api_group
 * 
 * @author api
 * @date 2023-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ApiGroup extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 分组ID */
    private Long groupId;

    /** 分组名称 */
    @NotBlank(message = "分组名称不能为空")
    @Size(min = 0, max = 100, message = "分组名称长度不能超过100个字符")
    private String groupName;

    /** 分组编码 */
    @NotBlank(message = "分组编码不能为空")
    @Size(min = 0, max = 50, message = "分组编码长度不能超过50个字符")
    private String groupCode;

    /** 父分组ID */
    private Long parentId;

    /** 显示顺序 */
    private Integer orderNum;

    /** 分组描述 */
    @Size(min = 0, max = 500, message = "分组描述长度不能超过500个字符")
    private String description;

    /** 状态（0正常 1停用） */
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 子分组 */
    private List<ApiGroup> children;

    /** 父分组名称 */
    private String parentName;
}