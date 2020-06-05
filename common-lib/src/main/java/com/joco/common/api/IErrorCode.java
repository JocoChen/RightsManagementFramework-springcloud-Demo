package com.joco.common.api;

/**
 * 封装API的错误码
 * Created by jocochen on 2019/4/19.
 */
public interface IErrorCode {
    long getCode();

    String getMessage();
}
