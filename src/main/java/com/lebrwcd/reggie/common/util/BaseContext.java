package com.lebrwcd.reggie.common.util;/**
 * @author lebrwcd
 * @date 2023/1/10
 * @note
 */

/**
 * ClassName BaseContext
 * Description ThreadLocal保存用户id
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/10
 */
public class BaseContext {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long get() {
       return threadLocal.get();
    }

    public static void removeCurrentId() {
        if (threadLocal.get() == null) {
            threadLocal.remove();
        }
    }

}
