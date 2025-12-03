package com.api.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

/**
 * API权限对象 api_permission
 * 
 * @author api
 * @date 2023-01-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("api_permission")
public class ApiPermission {
    private static final long serialVersionUID = 1L;

    /** 权限ID */
    private Long permissionId;

    /** API ID */
    private Long apiId;

    /** 角色ID */
    private Long roleId;

    /** 权限类型（0允许 1禁止） */
    private String permissionType;

    /** 创建者 */
    private Long createBy;

    /** 创建时间 */
    private Date createTime;

    /** 更新者 */
    private Long updateBy;

    /** 更新时间 */
    private Date updateTime;

    /** API名称 */
    private String apiName;

    /** 角色名称 */
    private String roleName;
}