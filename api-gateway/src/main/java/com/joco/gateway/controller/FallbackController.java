package com.joco.gateway.controller;

import com.joco.common.api.CommonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @RequestMapping("/defaultFallback")
    public CommonResult defaultFallback() {
        return CommonResult.bizFailed("交易降级处理，稍后重试！");
    }
}
