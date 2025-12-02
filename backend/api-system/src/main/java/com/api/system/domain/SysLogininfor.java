package com.api.system.domain;

import com.api.common.annotation.Excel;
import com.api.common.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 系统访问记录表 sys_logininfor
 * 
 * @author api
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_logininfor")
@Excel(name = "登录日志")
public class SysLogininfor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId
    private Long infoId;

    /** 用户账号 */
    @Excel(name = "用户账号", cellType = Excel.ColumnType.STRING)
    private String userName;

    /** 登录IP地址 */
    @Excel(name = "登录IP地址", cellType = Excel.ColumnType.STRING)
    private String ipaddr;

    /** 登录地点 */
    @Excel(name = "登录地点", cellType = Excel.ColumnType.STRING)
    private String loginLocation;

    /** 浏览器类型 */
    @Excel(name = "浏览器类型", cellType = Excel.ColumnType.STRING)
    private String browser;

    /** 操作系统 */
    @Excel(name = "操作系统", cellType = Excel.ColumnType.STRING)
    private String os;

    /** 登录状态（0成功 1失败） */
    @Excel(name = "登录状态", readConverterExp = Status.class, cellType = Excel.ColumnType.STRING)
    private String status;

    /** 提示消息 */
    @Excel(name = "提示消息", cellType = Excel.ColumnType.STRING)
    private String msg;

    /** 访问时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "访问时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", cellType = Excel.ColumnType.STRING)
    private Date loginTime;
}