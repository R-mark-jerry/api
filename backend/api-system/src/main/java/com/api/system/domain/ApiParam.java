package com.api.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * API参数对象 api_param
 * 
 * @author api
 * @date 2023-01-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("api_param")
public class ApiParam {
    private static final long serialVersionUID = 1L;

    /** 参数ID */
    private Long paramId;

    /** API ID */
    private Long apiId;

    /** 参数名称 */
    @NotBlank(message = "参数名称不能为空")
    @Size(min = 0, max = 100, message = "参数名称长度不能超过100个字符")
    private String paramName;

    /** 参数类型(header/path/query/body) */
    @NotBlank(message = "参数类型不能为空")
    @Size(min = 0, max = 20, message = "参数类型长度不能超过20个字符")
    private String paramType;

    /** 数据类型(string/number/boolean/object/array) */
    @NotBlank(message = "数据类型不能为空")
    @Size(min = 0, max = 20, message = "数据类型长度不能超过20个字符")
    private String dataType;

    /** 是否必填（0否 1是） */
    private String required;

    /** 默认值 */
    @Size(min = 0, max = 200, message = "默认值长度不能超过200个字符")
    private String defaultValue;

    /** 参数描述 */
    @Size(min = 0, max = 500, message = "参数描述长度不能超过500个字符")
    private String paramDescription;

    /** 显示顺序 */
    private Integer orderNum;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;
}