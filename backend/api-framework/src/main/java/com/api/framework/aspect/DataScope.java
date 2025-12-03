package com.api.framework.aspect;

import java.lang.annotation.*;

/**
 * 数据权限过滤注解
 *
 * @author API
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {
    /**
     * 部门表的别名
     */
    public String deptAlias() default "";

    /**
     * 用户表的别名
     */
    public String userAlias() default "";

    /**
     * 权限字段名
     */
    public String permission() default "";
}