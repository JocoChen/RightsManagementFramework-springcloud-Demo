package com.joco.common.exception;

import com.joco.common.api.IErrorCode;

/**
 * 断言处理类，用于抛出各种API异常
 * Created by jocochen on 2020/5/20.
 */
public class Asserts {
    public static void fail(String message) {
        throw new ServiceException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ServiceException(errorCode);
    }
}
