package com.scoder.common.core.exception.user;

import com.scoder.common.core.exception.BaseException;

/**
 * @author Shawn Cui
 */
public class UserException extends BaseException {
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args) {
        super("user", code, args, null);
    }
}
