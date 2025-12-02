package com.api.common.core.page;

import com.api.common.constant.Constants;
import com.api.common.utils.ServletUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 表格数据处理
 * 
 * @author api
 */
public class TableSupport
{
    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc"
     */
    public static final String IS_ASC = "isAsc";

    /**
     * 分页参数合理化
     */
    public static PageDomain getPageDomain()
    {
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(ServletUtils.getParameterToInt(PAGE_NUM));
        pageDomain.setPageSize(ServletUtils.getParameterToInt(PAGE_SIZE));
        pageDomain.setOrderByColumn(ServletUtils.getParameter(ORDER_BY_COLUMN));
        pageDomain.setIsAsc(ServletUtils.getParameter(IS_ASC));
        return pageDomain;
    }

    public static PageDomain buildPageRequest()
    {
        return getPageDomain();
    }

    /**
     * 构建分页对象
     * 
     * @param <T> 实体类型
     * @return 分页对象
     */
    public static <T> Page<T> buildPage()
    {
        PageDomain pageDomain = getPageDomain();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (pageNum == null || pageNum <= 0)
        {
            pageNum = 1;
        }
        if (pageSize == null || pageSize <= 0)
        {
            pageSize = 10;
        }
        return new Page<>(pageNum, pageSize);
    }

    /**
     * 构建分页对象
     * 
     * @param pageNum 页码
     * @param pageSize 页大小
     * @param <T> 实体类型
     * @return 分页对象
     */
    public static <T> Page<T> buildPage(Integer pageNum, Integer pageSize)
    {
        if (pageNum == null || pageNum <= 0)
        {
            pageNum = 1;
        }
        if (pageSize == null || pageSize <= 0)
        {
            pageSize = 10;
        }
        return new Page<>(pageNum, pageSize);
    }

    /**
     * 构建分页对象
     * 
     * @param pageDomain 分页参数
     * @param <T> 实体类型
     * @return 分页对象
     */
    public static <T> Page<T> buildPage(PageDomain pageDomain)
    {
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (pageNum == null || pageNum <= 0)
        {
            pageNum = 1;
        }
        if (pageSize == null || pageSize <= 0)
        {
            pageSize = 10;
        }
        return new Page<>(pageNum, pageSize);
    }

    /**
     * 设置分页排序
     * 
     * @param page 分页对象
     * @param pageDomain 分页参数
     * @param <T> 实体类型
     * @return 分页对象
     */
    public static <T> Page<T> setPageOrder(Page<T> page, PageDomain pageDomain)
    {
        String orderByColumn = pageDomain.getOrderByColumn();
        String isAsc = pageDomain.getIsAsc();
        if (orderByColumn != null && !orderByColumn.isEmpty())
        {
            if (isAsc != null && "desc".equals(isAsc.toLowerCase()))
            {
                page.setDesc(orderByColumn);
            }
            else
            {
                page.setAsc(orderByColumn);
            }
        }
        return page;
    }

    /**
     * 默认分页对象
     * 
     * @param <T> 实体类型
     * @return 分页对象
     */
    public static <T> Page<T> getDefaultPage()
    {
        return new Page<>(1, 10);
    }
}