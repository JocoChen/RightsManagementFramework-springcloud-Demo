package com.joco.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.joco.common.api.CommonResult;
import com.joco.gateway.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
//import com.github.autfish.gateway.util.MD5Utils;

@Component
public class AuthAndLogFilter implements GlobalFilter, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger("request");

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        StringBuilder logBuilder = new StringBuilder();

        //获取token
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        String token = authHeader.substring(this.tokenHead.length());
        boolean result =jwtTokenUtil.validateToken(token);

        if(!result) {
            String resp = JSON.toJSONString(CommonResult.unauthorized("权限验证失败"));
            logBuilder.append(",resp=").append(resp);
            LOGGER.info(logBuilder.toString());
            DataBuffer bodyDataBuffer = exchange.getResponse().bufferFactory().wrap(resp.getBytes());
            exchange.getResponse().getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            return exchange.getResponse().writeWith(Mono.just(bodyDataBuffer));
        }
        LOGGER.info("AuthAndLogFilter:token="+token);
        return chain.filter(exchange);

    }
/*
 * 该方法checkSignature用于签名检查
 */
//    private boolean checkSignature(Map<String, String> params, ServerHttpRequest serverHttpRequest) {
//
//        String sign = params.get("sign");
//        if(StringUtils.isBlank(sign)) {
//            return false;
//        }
//        //检查签名
//        Map<String, String> sorted = new TreeMap<>();
//        params.forEach( (k, v) -> {
//            if(!"sign".equals(k)) {
//                sorted.put(k, v);
//            }
//        });
//        StringBuilder builder = new StringBuilder();
//        sorted.forEach((k, v) -> {
//            builder.append(k).append("=").append(v).append("&");
//        });
//        String value = builder.toString();
//        value = value.substring(0, value.length() - 1);
//        System.out.println(MD5Utils.MD5(value));
//        if(!sign.equalsIgnoreCase(MD5Utils.MD5(value))) {
//            return false;
//        }
//
//        return true;
//    }

    @Override
    public int getOrder() {
        return -20;
    }
}
