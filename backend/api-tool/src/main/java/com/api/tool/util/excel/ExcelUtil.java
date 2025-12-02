package com.api.tool.util.excel;

import com.api.common.annotation.Excel;
import com.api.common.exception.ServiceException;
import com.api.common.utils.DateUtils;
import com.api.common.utils.StringUtils;
import com.api.common.utils.file.FileUtils;
import com.api.common.utils.file.TypeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Excel相关处理
 * 
 * @author api
 */
@Slf4j
public class ExcelUtil<T>
{
    /**
     * Excel sheet最大行数，默认65536
     */
    public static final int sheetSize = 65536;

    private static final int COLUMN_SIZE = 255;

    /**
     * 导出Excel（单个sheet）
     * 
     * @param list 导出数据集合
     * @param sheetName 工作表的名称
     * @param clazz 实体class
     */
    public void exportExcel(List<T> list, String sheetName, Class<T> clazz)
    {
        this.exportExcel(list, Excel.Type.EXPORT, sheetName, sheetName, clazz);
    }

    /**
     * 导出Excel（多个sheet）
     * 
     * @param list 导出数据集合
     * @param sheetName 工作表的名称
     * @param clazz 实体class
     */
    public void exportExcel(List<Map<String, Object>> list, String sheetName, Class<T> clazz)
    {
        this.exportExcel(list, Excel.Type.EXPORT, sheetName, sheetName, clazz);
    }

    /**
     * 导出Excel（多个sheet）
     * 
     * @param list 导出数据集合
     * @param sheetName 工作表的名称
     * @param clazz 实体class
     */
    public void exportExcel(List<Map<String, Object>> list, Excel.Type type, String sheetName, String title, Class<T> clazz)
    {
        this.export(list, type, sheetName, title, clazz);
    }

    /**
     * 导出Excel
     * 
     * @param list 导出数据集合
     * @param type 导出类型（EXPORT:导出数据；IMPORT：导入模板）
     * @param sheetName 工作表的名称
     * @param title 标题
     * @param clazz 实体class
     */
    public void exportExcel(List<Map<String, Object>> list, Excel.Type type, String sheetName, String title, Class<T> clazz)
    {
        OutputStream out = null;
        try
        {
            // 取出一共有多少个sheet
            double sheetNo = Math.ceil(list.size() / (double) sheetSize);
            for (int index = 0; index < sheetNo; index++)
            {
                // 产生工作薄对象
                Workbook workbook = type == Excel.Type.EXPORT ? new SXSSFWorkbook() : new XSSFWorkbook();
                out = FileUtils.getOutputStream(title + "_" + index + ".xlsx");

                List<Map<String, Object>> data;
                if (type == Excel.Type.EXPORT)
                {
                    // 分页查询数据
                    int fromIndex = index * sheetSize;
                    int toIndex = (index + 1) * sheetSize;
                    data = list.subList(fromIndex, toIndex > list.size() ? list.size() : toIndex);
                }
                else
                {
                    data = list;
                }

                // 写入数据
                writeSheet(workbook, data, type, sheetName + "_" + index, clazz, out);
            }
        }
        catch (Exception e)
        {
            log.error("导出Excel异常{}", e.getMessage());
        }
        finally
        {
            FileUtils.close(out);
        }
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     * 
     * @param list 导出数据集合
     * @param sheetName 工作表的名称
     * @param clazz 实体class
     */
    public void importTemplateExcel(List<T> list, String sheetName, Class<T> clazz)
    {
        this.exportExcel(list, Excel.Type.IMPORT, sheetName, null, clazz);
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     * 
     * @param response 响应对象
     * @param list 导出数据集合
     * @param fileName 文件名
     */
    public void exportExcel(HttpServletResponse response, List<T> list, String fileName)
    {
        exportExcel(response, list, fileName, null);
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     * 
     * @param response 响应对象
     * @param list 导出数据集合
     * @param fileName 文件名
     * @param clazz 实体class
     */
    public void exportExcel(HttpServletResponse response, List<T> list, String fileName, Class<T> clazz)
    {
        try
        {
            String sheetName = fileName;
            if (clazz != null)
            {
                Excel excelAnnotation = clazz.getAnnotation(Excel.class);
                if (StringUtils.isNotEmpty(excelAnnotation.name()))
                {
                    sheetName = excelAnnotation.name();
                }
            }

            response.reset();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + FileUtils.setFileDownloadHeader(fileName + ".xlsx") + "");
            response.setCharacterEncoding("utf-8");

            this.exportExcel(list, sheetName, clazz);
        }
        catch (Exception e)
        {
            log.error("导出Excel异常{}", e.getMessage());
        }
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     * 
     * @param response 响应对象
     * @param list 导出数据集合
     * @param fileName 文件名
     * @param response
     */
    public void exportExcel(HttpServletResponse response, List<Map<String, Object>> list, String fileName)
    {
        try
        {
            response.reset();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + FileUtils.setFileDownloadHeader(fileName + ".xlsx") + "");
            response.setCharacterEncoding("utf-8");

            this.exportExcel(list, fileName);
        }
        catch (Exception e)
        {
            log.error("导出Excel异常{}", e.getMessage());
        }
    }

    /**
     * 解析导入
     * 
     * @param excel 文件
     * @return 导入数据列表
     */
    public List<T> importExcel(MultipartFile excel, Class<T> clazz)
    {
        return this.importExcel(StringUtils.EMPTY, excel, clazz);
    }

    /**
     * 解析导入
     * 
     * @param sheetName 表单名
     * @param excel 文件
     * @return 导入数据列表
     */
    public List<T> importExcel(String sheetName, MultipartFile excel, Class<T> clazz)
    {
        List<T> list = new ArrayList<>();
        try
        {
            Workbook workbook = WorkbookFactory.create(excel.getInputStream());
            Sheet sheet = workbook.getSheet(sheetName);
            if (StringUtils.isEmpty(sheetName))
            {
                sheet = workbook.getSheetAt(0);
            }

            // 获取标题行
            Row row = sheet.getRow(0);
            if (row == null)
            {
                throw new ServiceException("导入的Excel文件没有数据行");
            }

            // 定义需要返回的列
            List<Object> fields = new ArrayList<>();
            for (int i = 0; i < row.getPhysicalNumberOfCells(); i++)
            {
                Cell cell = row.getCell(i);
                if (cell != null && cell.getStringCellValue() != null)
                {
                    fields.add(cell.getStringCellValue());
                }
            }

            // 有数据时才处理 得到类的所有field.
            Field[] allFields = clazz.getDeclaredFields();
            Map<Integer, Field> fieldsMap = new HashMap<>(16);
            for (Field field : allFields)
            {
                Excel attr = field.getAnnotation(Excel.class);
                if (attr != null && (attr.type() == Excel.Type.ALL || attr.type() == Excel.Type.IMPORT))
                {
                    int column = cellIndex(attr.name(), fields);
                    if (column >= 0)
                    {
                        fieldsMap.put(column, field);
                    }
                }
            }

            for (int i = 1; i <= sheet.getLastRowNum(); i++)
            {
                // 从第2行开始取数据，默认第一行是标题
                Row row1 = sheet.getRow(i);
                if (row1 == null)
                {
                    continue;
                }

                T entity = null;
                for (Map.Entry<Integer, Field> entry : fieldsMap.entrySet())
                {
                    Object val = this.getCellValue(row1, entry.getKey());
                    if (val != null)
                    {
                        if (entity == null)
                        {
                            entity = clazz.getDeclaredConstructor().newInstance();
                        }
                        Field field = entry.getValue();
                        field.setAccessible(true);
                        field.set(entity, val);
                    }
                }
                if (entity != null)
                {
                    list.add(entity);
                }
            }
        }
        catch (Exception e)
        {
            log.error("导入Excel异常{}", e.getMessage());
            throw new ServiceException("导入Excel失败，请联系管理员");
        }
        return list;
    }

    /**
     * 获取cell值
     * 
     * @param row 行
     * @param column 列
     * @return 值
     */
    private Object getCellValue(Row row, int column)
    {
        try
        {
            Cell cell = row.getCell(column);
            if (cell == null)
            {
                return "";
            }
            if (cell.getCellType() == CellType.NUMERIC)
            {
                return cell.getNumericCellValue();
            }
            else if (cell.getCellType() == CellType.STRING)
            {
                return cell.getStringCellValue();
            }
            else if (cell.getCellType() == CellType.BOOLEAN)
            {
                return cell.getBooleanCellValue();
            }
            else if (cell.getCellType() == CellType.ERROR)
            {
                return "";
            }
        }
        catch (Exception e)
        {
            log.error("获取cell值异常{}", e.getMessage());
        }
        return "";
    }

    /**
     * 获取单元格值
     * 
     * @param row 行
     * @param column 列
     * @return 单元格值
     */
    private Object getCellValue(Row row, int column, Class<?> attrType)
    {
        Object val = "";
        try
        {
            Cell cell = row.getCell(column);
            if (cell != null)
            {
                if (cell.getCellType() == CellType.NUMERIC)
                {
                    val = cell.getNumericCellValue();
                }
                else if (cell.getCellType() == CellType.STRING)
                {
                    val = cell.getStringCellValue();
                }
                else if (cell.getCellType() == CellType.BOOLEAN)
                {
                    val = cell.getBooleanCellValue();
                }
                else if (cell.getCellType() == CellType.ERROR)
                {
                    val = "";
                }
            }
        }
        catch (Exception e)
        {
            val = "";
        }
        return TypeUtils.castToAttrType(val, attrType);
    }

    /**
     * 设置单元格的值
     * 
     * @param cell 单元格
     * @param val 值
     */
    private void setCellValue(Cell cell, Object val)
    {
        if (val == null)
        {
            cell.setCellValue("");
        }
        else if (val instanceof String)
        {
            cell.setCellValue((String) val);
        }
        else if (val instanceof Integer)
        {
            cell.setCellValue((Integer) val);
        }
        else if (val instanceof Long)
        {
            cell.setCellValue((Long) val);
        }
        else if (val instanceof Double)
        {
            cell.setCellValue((Double) val);
        }
        else if (val instanceof Float)
        {
            cell.setCellValue((Float) val);
        }
        else if (val instanceof BigDecimal)
        {
            cell.setCellValue(((BigDecimal) val).doubleValue());
        }
        else if (val instanceof Date)
        {
            cell.setCellValue((Date) val);
        }
        else
        {
            cell.setCellValue(val.toString());
        }
    }

    /**
     * 设置单元格样式
     * 
     * @param workbook 工作薄对象
     * @return 样式
     */
    private CellStyle setCellStyle(Workbook workbook)
    {
        CellStyle style = workbook.createCellStyle();
        // 设置边框
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        // 设置居中
        style.setAlignment(HorizontalAlignment.CENTER);
        // 设置字体
        Font font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);
        return style;
    }

    /**
     * 设置单元格样式
     * 
     * @param workbook 工作薄对象
     * @return 样式
     */
    private CellStyle setHeadCellStyle(Workbook workbook)
    {
        CellStyle style = workbook.createCellStyle();
        // 设置边框
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        // 设置居中
        style.setAlignment(HorizontalAlignment.CENTER);
        // 设置字体
        Font font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        style.setFont(font);
        // 设置背景色
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    /**
     * 写入数据到工作表
     * 
     * @param workbook 工作薄对象
     * @param list 数据列表
     * @param sheetName 工作表名称
     * @param clazz 实体class
     * @param out 输出流
     */
    public void writeSheet(Workbook workbook, List<Map<String, Object>> list, Excel.Type type, String sheetName, Class<?> clazz, OutputStream out)
    {
        // 取出一共有多少个sheet
        double sheetNo = Math.ceil(list.size() / (double) sheetSize);
        for (int index = 0; index < sheetNo; index++)
        {
            // 产生工作薄对象
            Sheet sheet = workbook.createSheet(sheetName + "_" + index);
            
            // 写入数据
            if (type == Excel.Type.EXPORT)
            {
                // 创建标题行
                Row row = sheet.createRow(0);
                CellStyle headStyle = setHeadCellStyle(workbook);
                // 获取实体class
                Field[] allFields = clazz.getDeclaredFields();
                Map<Integer, Field> fieldsMap = new HashMap<>(16);
                for (Field field : allFields)
                {
                    Excel attr = field.getAnnotation(Excel.class);
                    if (attr != null && (attr.type() == Excel.Type.ALL || attr.type() == Excel.Type.EXPORT))
                    {
                        int column = cellIndex(attr.name(), fields);
                        if (column >= 0)
                        {
                            fieldsMap.put(column, field);
                        }
                    }
                }
                
                // 设置标题
                for (Map.Entry<Integer, Field> entry : fieldsMap.entrySet())
                {
                    Cell cell = row.createCell(entry.getKey());
                    cell.setCellValue(entry.getValue().getAnnotation(Excel.class).name());
                    cell.setCellStyle(headStyle);
                }
                
                // 写入数据
                CellStyle dataStyle = setCellStyle(workbook);
                for (int i = 0; i < list.size(); i++)
                {
                    Row datarow = sheet.createRow(i + 1);
                    Map<String, Object> obj = list.get(i);
                    for (Map.Entry<Integer, Field> entry : fieldsMap.entrySet())
                    {
                        Object val = obj.get(entry.getValue().getAnnotation(Excel.class).name());
                        Cell cell = datarow.createCell(entry.getKey());
                        setCellValue(cell, val);
                        cell.setCellStyle(dataStyle);
                    }
                }
            }
            else
            {
                // 创建导入模板
                Row row = sheet.createRow(0);
                CellStyle headStyle = setHeadCellStyle(workbook);
                // 获取实体class
                Field[] allFields = clazz.getDeclaredFields();
                Map<Integer, Field> fieldsMap = new HashMap<>(16);
                for (Field field : allFields)
                {
                    Excel attr = field.getAnnotation(Excel.class);
                    if (attr != null && (attr.type() == Excel.Type.ALL || attr.type() == Excel.Type.IMPORT))
                    {
                        int column = cellIndex(attr.name(), fields);
                        if (column >= 0)
                        {
                            fieldsMap.put(column, field);
                        }
                    }
                }
                
                // 设置标题
                for (Map.Entry<Integer, Field> entry : fieldsMap.entrySet())
                {
                    Cell cell = row.createCell(entry.getKey());
                    cell.setCellValue(entry.getValue().getAnnotation(Excel.class).name());
                    cell.setCellStyle(headStyle);
                }
            }
        }
        
        try
        {
            workbook.write(out);
        }
        catch (Exception e)
        {
            log.error("关闭工作薄失败", e);
        }
    }

    /**
     * 获取字段名对应的列号
     * 
     * @param fieldNames 字段名列表
     * @param fieldName 字段名
     * @return 列号
     */
    private int cellIndex(String fieldName, List<Object> fieldNames)
    {
        for (int i = 0; i < fieldNames.size(); i++)
        {
            if (fieldName.equals(fieldNames.get(i)))
            {
                return i;
            }
        }
        return -1;
    }
}