package com.joco.common.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 枚举了一些常用API操作码
 * Created by jocochen on 2019/4/19.
 */
@Getter
@AllArgsConstructor
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    SERVICE_UNAVAILABLE(503,"服务不可用"),
    HTTP_VERSION_NOT_SUPPORTED(505,"HTTP版本不支持"),
    BIZ_FAILED(400, "处理异常"),
    VALIDATE_FAILED(400, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),
    NOT_FOUND(404, "404 无法找到服务"),
    METHOD_NOT_ALLOWED(405,"请求方法不允许");
    private long code;
    private String message;

//    private ResultCode(long code, String message) {
//        this.code = code;
//        this.message = message;
//    }
//
//    public long getCode() {
//        return code;
//    }
//
//    public String getMessage() {
//        return message;
//    }

}
