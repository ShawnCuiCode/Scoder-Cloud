package com.scoder.common.core.utils.sql;

import com.scoder.common.core.exception.BaseException;
import com.scoder.common.core.utils.StringUtils;

/**
 * @author Shawn Cui
 */
public class SqlUtil {

    public static String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,\\.]+";


    public static String escapeOrderBySql(String value) {
        if (StringUtils.isNotEmpty(value) && !isValidOrderBySql(value)) {
            throw new BaseException("参数不符合规范，不能进行查询");
        }
        return value;
    }


    public static boolean isValidOrderBySql(String value) {
        return value.matches(SQL_PATTERN);
    }
}
