package com.api.admin.controller;

import com.api.common.core.controller.BaseController;
import com.api.common.core.domain.AjaxResult;
import com.api.common.utils.StringUtils;
import com.api.framework.jwt.JwtUtils;
import com.api.framework.web.LoginUser;
import com.api.system.domain.SysUser;
import com.api.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录验证
 * 
 * @author api
 */
@RestController
public class SysLoginController extends BaseController
{
    @Autowired
    private ISysUserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 登录方法
     * 
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = jwtUtils.createToken(loginUser);
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息
     * 
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser user = loginUser.getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     * 
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        // 用户信息
        SysUser user = loginUser.getUser();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(user.getUserId());
        return AjaxResult.success(menuService.buildMenus(menus));
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public AjaxResult logout()
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (StringUtils.isNotNull(loginUser))
        {
            String userName = loginUser.getUsername();
            // 删除用户缓存记录
            jwtUtils.logout(loginUser);
            // 记录用户退出日志
            recordLogininfor(userName, Constants.LOGOUT, "退出成功");
        }
        return AjaxResult.success("退出成功");
    }

    /**
     * 记录登录信息
     * 
     * @param username 用户名
     * @param status 状态
     * @param message 消息
     */
    public void recordLogininfor(String username, String status, String message)
    {
        // 封装对象
        SysLogininfor logininfor = new SysLogininfor();
        logininfor.setUserName(username);
        logininfor.setIpaddr(IpUtils.getIpAddr(ServletUtils.getRequest()));
        logininfor.setMsg(message);
        // 日志状态
        if (StringUtils.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER))
        {
            logininfor.setStatus(Constants.SUCCESS);
        }
        else if (Constants.LOGIN_FAIL.equals(status))
        {
            logininfor.setStatus(Constants.FAIL);
        }
        // 插入数据
        SpringUtils.getBean(ISysLogininforService.class).insertLogininfor(logininfor);
    }
}

/**
 * 登录身份认证
 */
@Data
class LoginBody
{
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 验证码
     */
    private String code;

    /**
     * 唯一标识
     */
    private String uuid = "";
}