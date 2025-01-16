package com.scoder.common.core.exception.file;

import com.scoder.common.core.exception.BaseException;

/**
 * @author Shawn Cui
 */
public class FileException extends BaseException {
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args) {
        super("file", code, args, null);
    }

}
