package com.joco.common.exception;

import com.joco.common.api.CommonResult;
import com.joco.common.api.ResultCode;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * 全局异常处理
 * Created by jocochen on 2020/5/20.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ServiceException.class)
    public CommonResult handle(ServiceException e) {
        if (e.getErrorCode() != null) {
            return CommonResult.failed(e.getErrorCode());
        }
        return CommonResult.failed(e.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult handle(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String message = String.format("%s:%s", error.getField(), error.getDefaultMessage());
        return CommonResult
                .builder()
                .code(ResultCode.VALIDATE_FAILED.getCode())
                .message(message)
                .build();
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public CommonResult handle(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String path = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
        String message = String.format("%s:%s", path, violation.getMessage());
        return CommonResult
                .builder()
                .code(ResultCode.VALIDATE_FAILED.getCode())
                .message(message)
                .build();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public CommonResult handle(NoHandlerFoundException e) {
        return CommonResult
                .builder()
                .code(ResultCode.NOT_FOUND.getCode())
                .message(e.getMessage())
                .build();
    }

}
