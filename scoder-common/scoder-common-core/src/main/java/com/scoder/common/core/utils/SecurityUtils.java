package com.scoder.common.core.utils;

import com.scoder.common.core.constant.CacheConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * Security Utils
 *
 * @author Shawn Cui
 */
public class SecurityUtils {

    public static String getToken() {
        return getToken(ServletUtils.getRequest());
    }

    public static String getToken(HttpServletRequest request) {
        String token = ServletUtils.getRequest().getHeader(CacheConstants.HEADER);
        if (StringUtils.isNotEmpty(token) && token.startsWith(CacheConstants.TOKEN_PREFIX)) {
            token = token.replace(CacheConstants.TOKEN_PREFIX, "");
        }
        return token;
    }

    public static boolean matchesPassword(String rawPassword, String password) {
        if (rawPassword.equals(password)) {
            return true;
        } else {
            return false;
        }
    }
}
