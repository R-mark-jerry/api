package com.api.framework.web;

import com.api.common.constant.Constants;
import com.api.common.utils.ServletUtils;
import com.api.common.utils.StringUtils;
import com.api.common.utils.ip.AddressUtils;
import com.api.common.utils.ip.IpUtils;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletRequest;

/**
 * 安全服务工具类
 * 
 * @author api
 */
public class SecurityUtils
{
    /**
     * 获取用户ID
     * 
     * @return 用户ID
     */
    public static Long getUserId()
    {
        try
        {
            return getLoginUser().getUserId();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * 获取用户名
     * 
     * @return 用户名
     */
    public static String getUsername()
    {
        try
        {
            return getLoginUser().getUsername();
        }
        catch (Exception e)
        {
            return "";
        }
    }

    /**
     * 获取用户信息
     * 
     * @return 用户信息
     */
    public static LoginUser getLoginUser()
    {
        try
        {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null)
            {
                if (authentication.getPrincipal() instanceof LoginUser)
                {
                    return (LoginUser) authentication.getPrincipal();
                }
            }
            return null;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * 获取用户部门ID
     * 
     * @return 部门ID
     */
    public static Long getDeptId()
    {
        try
        {
            return getLoginUser().getDeptId();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * 获取用户账户
     * 
     * @return 用户账户
     */
    public static String getUsername()
    {
        return getLoginUser().getUsername();
    }

    /**
     * 获取用户邮箱
     * 
     * @return 用户邮箱
     */
    public static String getEmail()
    {
        return getLoginUser().getEmail();
    }

    /**
     * 获取登录IP地址
     * 
     * @return IP地址
     */
    public static String getIpAddr()
    {
        HttpServletRequest request = ServletUtils.getRequest();
        return IpUtils.getIpAddr(request);
    }

    /**
     * 获取登录地点
     * 
     * @return 登录地点
     */
    public static String getLoginLocation()
    {
        HttpServletRequest request = ServletUtils.getRequest();
        String ip = IpUtils.getIpAddr(request);
        return AddressUtils.getRealAddressByIP(ip);
    }

    /**
     * 获取浏览器类型
     * 
     * @return 浏览器类型
     */
    public static String getBrowser()
    {
        HttpServletRequest request = ServletUtils.getRequest();
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        return userAgent.getBrowser().getName();
    }

    /**
     * 获取操作系统
     * 
     * @return 操作系统
     */
    public static String getOs()
    {
        HttpServletRequest request = ServletUtils.getRequest();
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        return userAgent.getOperatingSystem().getName();
    }

    /**
     * 生成BCryptPasswordEncoder密码
     * 
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     * 
     * @param rawPassword 真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 是否为管理员
     * 
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }

    /**
     * 验证用户是否具备某权限
     * 
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    public static boolean hasPermi(String permission)
    {
        return getLoginUser().getPermissions().contains(Constants.ALL_PERMISSION) || getLoginUser().getPermissions().contains(StringUtils.trim(permission));
    }

    /**
     * 验证用户是否含有指定权限
     * 
     * @param permissions 权限列表
     * @return 用户是否含有某权限
     */
    public static boolean hasPermi(String... permissions)
    {
        if (getLoginUser() == null || StringUtils.isEmpty(permissions))
        {
            return false;
        }
        Set<String> authorities = getLoginUser().getPermissions();
        for (String permission : permissions)
        {
            if (StringUtils.isNotEmpty(permission) && authorities.contains(StringUtils.trim(permission)))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证用户是否含有指定权限
     * 
     * @param permissions 权限列表
     * @return 用户是否含有某权限
     */
    public static boolean hasPermi(Collection<String> permissions)
    {
        if (getLoginUser() == null || StringUtils.isEmpty(permissions))
        {
            return false;
        }
        Set<String> authorities = getLoginUser().getPermissions();
        for (String permission : permissions)
        {
            if (StringUtils.isNotEmpty(permission) && authorities.contains(StringUtils.trim(permission)))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证用户是否含有指定角色
     * 
     * @param role 角色标识
     * @return 用户是否含有某角色
     */
    public static boolean hasRole(String role)
    {
        if (getLoginUser() == null || StringUtils.isEmpty(role))
        {
            return false;
        }
        for (SysRole sysRole : getLoginUser().getUser().getRoles())
        {
            String roleKey = sysRole.getRoleKey();
            if (Constants.SUPER_ADMIN.equals(roleKey) || roleKey.equals(StringUtils.trim(role)))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证用户是否含有指定角色
     * 
     * @param roles 角色标识列表
     * @return 用户是否含有某角色
     */
    public static boolean hasRole(String... roles)
    {
        if (getLoginUser() == null || StringUtils.isEmpty(roles))
        {
            return false;
        }
        for (String role : roles)
        {
            if (hasRole(role))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证用户是否含有指定角色
     * 
     * @param roles 角色标识列表
     * @return 用户是否含有某角色
     */
    public static boolean hasRole(Collection<String> roles)
    {
        if (getLoginUser() == null || StringUtils.isEmpty(roles))
        {
            return false;
        }
        for (String role : roles)
        {
            if (hasRole(role))
            {
                return true;
            }
        }
        return false;
    }
}