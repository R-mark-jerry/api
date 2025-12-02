package com.api.common.constant;

/**
 * 通用常量信息
 * 
 * @author api
 */
public class Constants
{
    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "success";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "fail";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 注册
     */
    public static final String REGISTER = "Register";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 用户ID
     */
    public static final String USER_ID = "user_id";

    /**
     * 用户名
     */
    public static final String USERNAME = "username";

    /**
     * 用户KEY
     */
    public static final String USER_KEY = "user_key";

    /**
     * 登录时间
     */
    public static final String LOGIN_TIME = "login_time";

    /**
     * 过期时间
     */
    public static final String EXPIRE_TIME = "expire_time";

    /**
     * 权限
     */
    public static final String PERMISSIONS = "permissions";

    /**
     * 角色权限
     */
    public static final String ROLE_PERMISSIONS = "role_permissions";

    /**
     * 数据权限
     */
    public static final String DATA_SCOPE = "data_scope";

    /**
     * 当前页码
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示条数
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
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 限流 redis key
     */
    public static final String RATE_LIMIT_KEY = "rate_limit:";

    /**
     * 登录账户密码错误次数 redis key
     */
    public static final String PWD_ERR_CNT_KEY = "pwd_err_cnt:";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

    /**
     * 默认为匿名角色
     */
    public static final String DEFAULT_ROLE_NAME = "anonymous";

    /**
     * 默认为匿名权限
     */
    public static final String DEFAULT_PERM_NAME = "*:*:*";
}