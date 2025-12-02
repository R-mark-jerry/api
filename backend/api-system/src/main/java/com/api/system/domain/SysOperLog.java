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
 * 操作日志记录表 oper_log
 * 
 * @author api
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_oper_log")
@Excel(name = "操作日志表")
public class SysOperLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 日志主键 */
    @TableId
    private Long operId;

    /** 操作模块 */
    @Excel(name = "操作模块", cellType = Excel.ColumnType.STRING)
    private String title;

    /** 业务类型（0其它 1新增 2修改 3删除） */
    @Excel(name = "业务类型", readConverterExp = BusinessType.class, cellType = Excel.ColumnType.STRING)
    private Integer businessType;

    /** 请求方法 */
    @Excel(name = "请求方法", cellType = Excel.ColumnType.STRING)
    private String method;

    /** 请求方式 */
    @Excel(name = "请求方式", cellType = Excel.ColumnType.STRING)
    private String requestMethod;

    /** 操作类别（0其它 1后台用户 2手机端用户） */
    @Excel(name = "操作类别", readConverterExp = OperatorType.class, cellType = Excel.ColumnType.STRING)
    private Integer operatorType;

    /** 操作人员 */
    @Excel(name = "操作人员", cellType = Excel.ColumnType.STRING)
    private String operName;

    /** 部门名称 */
    @Excel(name = "部门名称", cellType = Excel.ColumnType.STRING)
    private String deptName;

    /** 请求URL */
    @Excel(name = "请求地址", cellType = Excel.ColumnType.STRING)
    private String operUrl;

    /** 操作地址 */
    @Excel(name = "操作地址", cellType = Excel.ColumnType.STRING)
    private String operIp;

    /** 操作地点 */
    @Excel(name = "操作地点", cellType = Excel.ColumnType.STRING)
    private String operLocation;

    /** 请求参数 */
    @Excel(name = "请求参数", cellType = Excel.ColumnType.STRING)
    private String operParam;

    /** 返回参数 */
    @Excel(name = "返回参数", cellType = Excel.ColumnType.STRING)
    private String jsonResult;

    /** 操作状态（0正常 1异常） */
    @Excel(name = "状态", readConverterExp = Status.class, cellType = Excel.ColumnType.STRING)
    private String status;

    /** 错误消息 */
    @Excel(name = "错误消息", cellType = Excel.ColumnType.STRING)
    private String errorMsg;

    /** 操作时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", cellType = Excel.ColumnType.STRING)
    private Date operTime;

    /** 消耗时间 */
    @Excel(name = "消耗时间", cellType = Excel.ColumnType.STRING)
    private Long costTime;
}