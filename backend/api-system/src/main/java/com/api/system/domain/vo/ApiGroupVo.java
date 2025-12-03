package com.api.system.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * API分组视图对象
 * 
 * @author api
 * @date 2023-01-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiGroupVo {
    private static final long serialVersionUID = 1L;

    /** 分组ID */
    private Long groupId;

    /** 分组名称 */
    private String groupName;

    /** 分组编码 */
    private String groupCode;

    /** 父分组ID */
    private Long parentId;

    /** 显示顺序 */
    private Integer orderNum;

    /** 分组描述 */
    private String description;

    /** 状态（0正常 1停用） */
    private String status;

    /** 状态描述 */
    private String statusDesc;

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

    /** 子分组列表 */
    private List<ApiGroupVo> children;

    /** 父分组名称 */
    private String parentName;

    /** 是否有子节点 */
    private Boolean hasChildren;

    /** 层级深度 */
    private Integer level;

    /** API数量 */
    private Integer apiCount;

    /** 是否可以编辑 */
    private Boolean canEdit;

    /** 是否可以删除 */
    private Boolean canDelete;
}