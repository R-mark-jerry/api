package com.api.framework.aspect;

import com.api.common.utils.StringUtils;
import com.api.common.utils.DateUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 操作日志记录处理
 *
 * @author API
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        handleLog(joinPoint, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e        异常
     */
    @AfterThrowing(value = "@annotation(controllerLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
        try {
            // 获取当前的用户
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = joinPoint.getSignature().getName();
            
            // 获取请求的参数
            String method = className + "." + methodName + "()";
            String params = Arrays.toString(joinPoint.getArgs());
            
            // 打印日志
            log.info("请求方法: {}", method);
            log.info("请求参数: {}", params);
            
            if (e != null) {
                log.error("请求异常: {}", e.getMessage());
            } else {
                log.info("返回结果: {}", jsonResult);
            }
            
            // 这里可以保存到数据库
            // saveLogToDatabase(method, params, e, jsonResult);
            
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("异常信息:{}", exp.getMessage(), exp);
        }
    }

    /**
     * 保存日志到数据库
     */
    private void saveLogToDatabase(String method, String params, Exception e, Object jsonResult) {
        // TODO: 实现保存日志到数据库的逻辑
        log.info("保存日志到数据库 - 方法: {}, 参数: {}, 异常: {}, 结果: {}", 
                method, params, e != null ? e.getMessage() : "", jsonResult);
    }
}