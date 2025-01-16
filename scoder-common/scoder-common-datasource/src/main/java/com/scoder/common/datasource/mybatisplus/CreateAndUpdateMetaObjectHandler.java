package com.scoder.common.datasource.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * MyBatis-Plus MetaObject Handler for automatic field filling.
 * <p>
 * Automatically sets `createTime` and `updateTime` fields during insert and update operations.
 *
 * @author Shawn Cui
 */
public class CreateAndUpdateMetaObjectHandler implements MetaObjectHandler {

    /**
     * Automatically fills the `createTime` field during insert operations.
     *
     * @param metaObject MetaObject representing the current operation context
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasGetter("createTime") && metaObject.getValue("createTime") == null) {
            this.setFieldValByName("createTime", new Date(), metaObject);
        }
    }

    /**
     * Automatically fills the `updateTime` field during update operations.
     *
     * @param metaObject MetaObject representing the current operation context
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasGetter("updateTime") && metaObject.getValue("updateTime") == null) {
            this.setFieldValByName("updateTime", new Date(), metaObject);
        }
    }
}