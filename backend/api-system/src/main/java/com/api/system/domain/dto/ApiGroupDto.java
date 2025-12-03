package com.api.system.domain.dto;

import com.api.system.domain.ApiGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * API分组数据传输对象
 * 
 * @author api
 * @date 2023-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ApiGroupDto extends ApiGroup {
    private static final long serialVersionUID = 1L;

    /** 子分组列表 */
    private List<ApiGroupDto> children;

    /** 是否有子节点 */
    private Boolean hasChildren;

    /** 层级深度 */
    private Integer level;
}