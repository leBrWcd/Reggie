package com.lebrwcd.reggie.common;/**
 * @author lebrwcd
 * @date 2023/1/10
 * @note
 */

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.lebrwcd.reggie.common.util.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * ClassName MyMetaObjectHandler
 * Description 自定义元数据处理器
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/10
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入时自动填充  @TableField(fill = FieldFill.INSERT)
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("插入自动填充 {}",metaObject.toString());
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime",LocalDateTime.now());
        // 从ThreadLocal中获取当前用户
        Long userId = BaseContext.get();
        metaObject.setValue("createUser",userId);
        metaObject.setValue("updateUser",userId);

    }

    /**
     * 更新时自动填充    @TableField(fill = FieldFill.INSERT_UPDATE)   @TableField(fill = FieldFill.UPDATE)
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("当前线程id: {}",Thread.currentThread().getId());
        log.info("更新自动填充 {}",metaObject.toString());
        // 从ThreadLocal中获取当前用户
        Long userId = BaseContext.get();
        metaObject.setValue("updateTime",LocalDateTime.now());
        metaObject.setValue("updateUser",userId);
    }
}
