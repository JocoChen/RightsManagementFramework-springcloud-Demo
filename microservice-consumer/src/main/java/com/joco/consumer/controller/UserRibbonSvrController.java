package com.joco.consumer.controller;

import com.joco.common.api.CommonResult;
import com.joco.consumer.dto.UmsAdminLoginParam;
import com.joco.consumer.dto.UmsAdminParam;
import com.joco.consumer.service.AdminService;
import com.joco.consumer.service.RedisService;
import com.joco.consumer.service.UmsRoleService;
import com.joco.mbg.model.UmsAdmin;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jocochen on 2019/8/29.
 */
@RestController
@RefreshScope    //nacos config
@RequestMapping("/admin")
public class UserRibbonSvrController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRibbonSvrController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UmsRoleService roleService;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value(value = "${service-url.nacos-user-service}")
    private String userServiceUrl;

    @Value("${sleep:0}")
    private int sleep;

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<UmsAdmin> register(@RequestBody UmsAdminParam umsAdminParam, BindingResult result) {
        UmsAdmin umsAdmin = adminService.register(umsAdminParam);
        if (umsAdmin == null) {
            CommonResult.failed();
        }
        return CommonResult.success(umsAdmin);
    }

    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@RequestBody UmsAdminLoginParam umsAdminLoginParam, BindingResult result) {
        String token = adminService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();

        tokenMap.put("token", token);

        if(tokenHead.isEmpty()){
            tokenHead="Bearer";
        }
        tokenMap.put("tokenHead", tokenHead);

        //将token添加到redis缓存中，并设置有效期为1天
        String key = "token:" + umsAdminLoginParam.getUsername();
        redisService.set(key, tokenHead+" "+token, 24*60*60);


        return CommonResult.success(tokenMap);
    }


    @ApiOperation(value = "获取当前登录用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getAdminInfo(Principal principal) {
        if(principal==null){
            return CommonResult.unauthorized(null);
        }
        String username = principal.getName();
        UmsAdmin umsAdmin = adminService.getAdminByUsername(username);
        Map<String, Object> data = new HashMap<>();
        data.put("username", umsAdmin.getUsername());
        data.put("roles", new String[]{"TEST"});
        data.put("menus", roleService.getMenuList(umsAdmin.getId()));
        data.put("icon", umsAdmin.getIcon());
        return CommonResult.success(data);
    }

    @ApiOperation(value = "登出功能")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult logout() {
        return CommonResult.success(null);
    }



    //for test
    @GetMapping("/sayHello")
    public String sayHello() throws InterruptedException {
        LOGGER.info("sayHello::sleep={}",sleep);
        Thread.sleep(sleep);
        return restTemplate.getForObject("http://microservice-provider/user/hello",String.class);
    }
    //for test
    @GetMapping("/sayBye")
    public String sayBye() {
        return restTemplate.getForObject("http://microservice-provider/user/hello",String.class);
    }
}
