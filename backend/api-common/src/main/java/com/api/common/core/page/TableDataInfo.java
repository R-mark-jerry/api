package com.api.common.core.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

/**
 * 表格分页数据对象
 * 
 * @author api
 */
@Data
public class TableDataInfo
{
    /** 总记录数 */
    private long total;

    /** 当前页数据 */
    private List<?> rows;

    /** 当前页码 */
    private int pageNum;

    /** 每页大小 */
    private int pageSize;

    /** 总页数 */
    private int pages;

    /**
     * 表格数据对象
     */
    public TableDataInfo()
    {
    }

    /**
     * 分页
     * 
     * @param list 列表数据
     * @param pageNum 当前页码
     * @param pageSize 每页大小
     * @param total 总记录数
     */
    public TableDataInfo(List<?> list, int pageNum, int pageSize, long total)
    {
        this.rows = list;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.pages = (int) Math.ceil((double) total / pageSize);
    }

    /**
     * 分页
     * 
     * @param page MyBatis Plus分页对象
     */
    public TableDataInfo(IPage<?> page)
    {
        this.rows = page.getRecords();
        this.pageNum = (int) page.getCurrent();
        this.pageSize = (int) page.getSize();
        this.total = page.getTotal();
        this.pages = (int) page.getPages();
    }

    /**
     * 空数据
     * 
     * @param pageNum 当前页码
     * @param pageSize 每页大小
     * @return 空数据表格
     */
    public static TableDataInfo empty(int pageNum, int pageSize)
    {
        return new TableDataInfo(null, pageNum, pageSize, 0);
    }

    /**
     * 空数据
     * 
     * @return 空数据表格
     */
    public static TableDataInfo empty()
    {
        return new TableDataInfo(null, 1, 10, 0);
    }
}