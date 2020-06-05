package com.joco.common.exception;

import com.joco.common.api.IErrorCode;

/**
 * 自定义biz service异常
 * Created by jocochen on 2020/5/20.
 */
public class ServiceException extends RuntimeException {
    private IErrorCode errorCode;

    public ServiceException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ServiceException(String message) {
        super(message);
    }


    public ServiceException(IErrorCode errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(IErrorCode errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
