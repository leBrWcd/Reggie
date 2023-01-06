package com.lebrwcd.reggie.common.exec;/**
 * @author lebrwcd
 * @date 2023/1/6
 * @note
 */

import com.lebrwcd.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * ClassName GlobalExceptionHandler
 * Description 全局异常处理器
 *
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/6
 */
@ControllerAdvice(annotations = { RestController.class, Controller.class })
@Slf4j
@ResponseBody
public class GlobalExceptionHandler  {


    /**
     * 处理 SQLIntegrityConstraintViolationException 异常
     * @param exception
     * @return 解决SQLIntegrityConstraintViolationException异常
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException exception) {
        String message = exception.getMessage();
        // 如果是数据库中字段唯一性出现异常
        if (message.contains("Duplicate entry")) {
            // Duplicate entry 'xxx' for key 'idx_username'
            String[] split = message.split(" ");
            // 'xxx'
            String field = split[2];
            return R.error(field + "已存在，请勿重复添加!");
        }
       return R.error("未知错误！");
    }
}
