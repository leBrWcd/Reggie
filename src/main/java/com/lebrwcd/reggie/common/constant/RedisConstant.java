package com.lebrwcd.reggie.common.constant;/**
 * @author lebrwcd
 * @date 2023/1/17
 * @note
 */

/**
 * ClassName RedisConstant
 * Description redis缓存键名称
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/17
 */
public class RedisConstant {

    /**
     * 本应用redis缓存统一前缀
     */
    public static final String APP_PREFIX = "reggie:";

    /**
     * 手机验证码前缀
     */
    public static final String LOGIN_CODE = "code:";

    /**
     * 手机验证码缓存30s
     */
    public static final Long LOGIN_CODE_TTL = 60L;

}
