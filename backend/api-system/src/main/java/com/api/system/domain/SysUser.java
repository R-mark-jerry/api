package com.api.system.domain;

import com.api.common.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * 用户对象 sys_user
 * 
 * @author api
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class SysUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    @TableId
    private Long userId;

    /** 用户账号 */
    @NotBlank(message = "用户账号不能为空")
    @Size(min = 0, max = 30, message = "用户账号长度不能超过30个字符")
    private String userName;

    /** 用户昵称 */
    @Size(min = 0, max = 30, message = "用户昵称长度不能超过30个字符")
    private String nickName;

    /** 用户类型（00系统用户） */
    private String userType;

    /** 用户邮箱 */
    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    private String email;

    /** 手机号码 */
    @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
    private String phonenumber;

    /** 用户性别（0男 1女 2未知） */
    private String sex;

    /** 用户头像 */
    private String avatar;

    /** 密码 */
    private String password;

    /** 帐号状态（0正常 1停用） */
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 最后登录IP */
    private String loginIp;

    /** 最后登录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginDate;

    /** 部门对象 */
    @TableField(exist = false)
    private SysDept dept;

    /** 角色对象 */
    @TableField(exist = false)
    private Long[] roleIds;

    /** 角色组 */
    @TableField(exist = false)
    private Long[] roleIdArray;

    /** 岗位组 */
    @TableField(exist = false)
    private Long[] postIds;

    /** 角色ID */
    @TableField(exist = false)
    private Long roleId;

    /** 部门ID */
    @TableField(exist = false)
    private Long deptId;

    /** 部门名称 */
    @TableField(exist = false)
    private String deptName;

    /** 权限列表 */
    @TableField(exist = false)
    private Set<String> permissions;

    /** 角色列表 */
    @TableField(exist = false)
    private List<SysRole> roles;
}