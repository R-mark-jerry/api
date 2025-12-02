package com.api.common.utils;

import com.api.common.constant.Constants;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 客户端工具类
 * 
 * @author api
 */
public class ServletUtils
{
    /**
     * 获取request
     */
    public static HttpServletRequest getRequest()
    {
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取response
     */
    public static HttpServletResponse getResponse()
    {
        return getRequestAttributes().getResponse();
    }

    /**
     * 获取session
     */
    public static HttpSession getSession()
    {
        return getRequest().getSession();
    }

    public static ServletRequestAttributes getRequestAttributes()
    {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

    /**
     * 将字符串渲染到客户端
     * 
     * @param response 渲染对象
     * @param string 待渲染的字符串
     * @return null
     */
    public static String renderString(HttpServletResponse response, String string)
    {
        try
        {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 是否是Ajax异步请求
     * 
     * @param request
     */
    public static boolean isAjaxRequest(HttpServletRequest request)
    {
        String accept = request.getHeader("accept");
        if (accept != null && accept.contains("application/json"))
        {
            return true;
        }

        String xRequestedWith = request.getHeader("X-Requested-With");
        if (xRequestedWith != null && xRequestedWith.contains("XMLHttpRequest"))
        {
            return true;
        }

        String uri = request.getRequestURI();
        if (StringUtils.inStringIgnoreCase(uri, ".json", ".xml"))
        {
            return true;
        }

        String ajax = request.getParameter("__ajax");
        return StringUtils.inStringIgnoreCase(ajax, "json", "xml");
    }

    /**
     * 获取String参数
     */
    public static String getParameter(String name)
    {
        return getRequest().getParameter(name);
    }

    /**
     * 获取String参数
     */
    public static String getParameter(String name, String defaultValue)
    {
        String value = getRequest().getParameter(name);
        return StringUtils.isEmpty(value) ? defaultValue : value;
    }

    /**
     * 获取Integer参数
     */
    public static Integer getParameterToInt(String name)
    {
        return StringUtils.toInt(getRequest().getParameter(name));
    }

    /**
     * 获取Integer参数
     */
    public static Integer getParameterToInt(String name, Integer defaultValue)
    {
        String value = getRequest().getParameter(name);
        return StringUtils.isEmpty(value) ? defaultValue : StringUtils.toInt(value);
    }

    /**
     * 获取Boolean参数
     */
    public static Boolean getParameterToBool(String name)
    {
        return StringUtils.toBool(getRequest().getParameter(name));
    }

    /**
     * 获取Boolean参数
     */
    public static Boolean getParameterToBool(String name, Boolean defaultValue)
    {
        String value = getRequest().getParameter(name);
        return StringUtils.isEmpty(value) ? defaultValue : StringUtils.toBool(value);
    }

    /**
     * 获取Long参数
     */
    public static Long getParameterToLong(String name)
    {
        return StringUtils.toLong(getRequest().getParameter(name));
    }

    /**
     * 获取Long参数
     */
    public static Long getParameterToLong(String name, Long defaultValue)
    {
        String value = getRequest().getParameter(name);
        return StringUtils.isEmpty(value) ? defaultValue : StringUtils.toLong(value);
    }

    /**
     * 获取Double参数
     */
    public static Double getParameterToDouble(String name)
    {
        return StringUtils.toDouble(getRequest().getParameter(name));
    }

    /**
     * 获取Double参数
     */
    public static Double getParameterToDouble(String name, Double defaultValue)
    {
        String value = getRequest().getParameter(name);
        return StringUtils.isEmpty(value) ? defaultValue : StringUtils.toDouble(value);
    }

    /**
     * content-type转义
     * 
     * @param value 参数
     * @return 转义后字符串
     */
    public static String escape(String value)
    {
        if (StringUtils.isEmpty(value))
        {
            return value;
        }
        try
        {
            return URLEncoder.encode(value, Constants.UTF8);
        }
        catch (UnsupportedEncodingException e)
        {
            return value;
        }
    }

    /**
     * 获取浏览器版本信息
     * 
     * @param request 请求对象
     * @return 浏览器版本信息
     */
    public static String getBrowser(HttpServletRequest request)
    {
        String userAgent = request.getHeader("User-Agent");
        if (StringUtils.isEmpty(userAgent))
        {
            return "未知";
        }
        if (userAgent.contains("MSIE"))
        {
            return "IE";
        }
        if (userAgent.contains("Firefox"))
        {
            return "Firefox";
        }
        if (userAgent.contains("Chrome"))
        {
            return "Chrome";
        }
        if (userAgent.contains("Safari"))
        {
            return "Safari";
        }
        if (userAgent.contains("Camino"))
        {
            return "Camino";
        }
        if (userAgent.contains("Konqueror"))
        {
            return "Konqueror";
        }
        return "未知";
    }

    /**
     * 获取操作系统信息
     * 
     * @param request 请求对象
     * @return 操作系统信息
     */
    public static String getOs(HttpServletRequest request)
    {
        String userAgent = request.getHeader("User-Agent");
        if (StringUtils.isEmpty(userAgent))
        {
            return "未知";
        }
        if (userAgent.contains("Windows"))
        {
            return "Windows";
        }
        if (userAgent.contains("Mac"))
        {
            return "Mac";
        }
        if (userAgent.contains("X11"))
        {
            return "Unix";
        }
        if (userAgent.contains("Android"))
        {
            return "Android";
        }
        if (userAgent.contains("iPhone"))
        {
            return "iPhone";
        }
        return "未知";
    }
}