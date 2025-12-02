package com.api.common.core.page;

import lombok.Data;

/**
 * 分页数据
 * 
 * @author api
 */
@Data
public class PageDomain
{
    /** 当前记录起始索引 */
    private Integer pageNum;

    /** 每页显示记录数 */
    private Integer pageSize;

    /** 排序列 */
    private String orderByColumn;

    /** 排序的方向desc或者asc */
    private String isAsc = "asc";

    /** 分页参数 */
    private String params;

    public String getOrderBy()
    {
        if (isEmpty(orderByColumn))
        {
            return "";
        }
        return orderByColumn + " " + isAsc;
    }

    public boolean isEmpty(Object str)
    {
        return str == null || "".equals(str);
    }
}