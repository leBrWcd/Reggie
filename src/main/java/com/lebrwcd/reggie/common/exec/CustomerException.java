package com.lebrwcd.reggie.common.exec;/**
 * @author lebrwcd
 * @date 2023/1/12
 * @note
 */

import org.omg.SendingContext.RunTime;

/**
 * ClassName CustomerException
 * Description 自定义分类删除异常
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/12
 */
public class CustomerException extends RuntimeException {
    public CustomerException() {
        super();
    }

    public CustomerException(String message) {
        super(message);
    }
}
