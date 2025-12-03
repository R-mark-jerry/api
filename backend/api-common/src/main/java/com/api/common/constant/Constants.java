package com.api.common.constant;

/**
 * 通用常量信息
 *
 * @author API
 */
public class Constants {
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
     * 用户IP
     */
    public static final String USER_IP = "user_ip";

    /**
     * 登录时间
     */
    public static final String LOGIN_TIME = "login_time";

    /**
     * 当前页码
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
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
     * 帐号状态
     */
    public static final String ACCOUNT_STATUS = "account_status";

    /**
     * 帐号正常
     */
    public static final String ACCOUNT_NORMAL = "0";

    /**
     * 帐号禁用
     */
    public static final String ACCOUNT_DISABLE = "1";

    /**
     * 帐号删除
     */
    public static final String ACCOUNT_DELETE = "2";

    /**
     * 当前记录起始索引
     */
    public static final String PAGE = "page";

    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";

    /**
     * 排序字段
     */
    public static final String SOTR_FIELD = "sortField";

    /**
     * 排序方式
     */
    public static final String SORT_ORDER = "sortOrder";

    /**
     * 参数管理
     */
    public static final String PARAM_CONFIG = "sys_config";

    /**
     * 字典管理
     */
    public static final String SYS_DICT = "sys_dict";

    /**
     * 菜单管理
     */
    public static final String SYS_MENU = "sys_menu";

    /**
     * 定时任务
     */
    public static final String SYS_JOB = "sys_job";

    /**
     * 部门管理
     */
    public static final String SYS_DEPT = "sys_dept";

    /**
     * 通知公告
     */
    public static final String SYS_NOTICE = "sys_notice";

    /**
     * 在线用户
     */
    public static final String SYS_ONLINE = "sys_online";

    /**
     * 操作日志
     */
    public static final String SYS_OPERLOG = "sys_operlog";

    /**
     * 登录日志
     */
    public static final String SYS_LOGININFOR = "sys_logininfor";

    /**
     * 定时任务日志
     */
    public static final String SYS_JOB_LOG = "sys_job_log";

    /**
     * 服务监控
     */
    public static final String SERVER_MONITOR = "server_monitor";

    /**
     * 缓存监控
     */
    public static final String CACHE_MONITOR = "cache_monitor";

    /**
     * 在线构建器
     */
    public static final String SYS_TOOL_BUILD = "sys_tool_build";

    /**
     * 代码生成
     */
    public static final String SYS_TOOL_GEN = "sys_tool_gen";

    /**
     * 系统接口
     */
    public static final String SYS_TOOL_SWAGGER = "sys_tool_swagger";

    /**
     * 数据源监控
     */
    public static final String DRUID_MONITOR = "druid_monitor";
}