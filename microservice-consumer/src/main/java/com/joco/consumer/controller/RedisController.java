//package com.joco.consumer.controller;
//
//import com.joco.consumer.service.RedisService;
//import io.swagger.annotations.Api;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
///**
// * redis测试Controller
// * Created by jocochen on 2020/5/20.
// */
//@Api(tags = "RedisController", description = "redis测试")
//@Controller
//@RequestMapping("/redis")
//public class RedisController {
//    @Autowired
//    private RedisService redisService;
//
//    @Autowired
//    private PmsBrandService brandService;
//
//    @ApiOperation("测试简单缓存")
//    @RequestMapping(value = "/simpleTest", method = RequestMethod.GET)
//    @ResponseBody
//    public CommonResult<PmsBrand> simpleTest() {
//        List<PmsBrand> brandList = brandService.list(1, 5);
//        PmsBrand brand = brandList.get(0);
//        String key = "redis:simple:" + brand.getId();
//        redisService.set(key, brand);
//        PmsBrand cacheBrand = (PmsBrand) redisService.get(key);
//        return CommonResult.success(cacheBrand);
//    }
//
//    @ApiOperation("测试Hash结构的缓存")
//    @RequestMapping(value = "/hashTest", method = RequestMethod.GET)
//    @ResponseBody
//    public CommonResult<PmsBrand> hashTest() {
//        List<PmsBrand> brandList = brandService.list(1, 5);
//        PmsBrand brand = brandList.get(0);
//        String key = "redis:hash:" + brand.getId();
//        Map<String, Object> value = BeanUtil.beanToMap(brand);
//        redisService.hSetAll(key, value);
//        Map<Object, Object> cacheValue = redisService.hGetAll(key);
//        PmsBrand cacheBrand = BeanUtil.mapToBean(cacheValue, PmsBrand.class, true);
//        return CommonResult.success(cacheBrand);
//    }
//
//    @ApiOperation("测试Set结构的缓存")
//    @RequestMapping(value = "/setTest", method = RequestMethod.GET)
//    @ResponseBody
//    public CommonResult<Set<Object>> setTest() {
//        List<PmsBrand> brandList = brandService.list(1, 5);
//        String key = "redis:set:all";
//        redisService.sAdd(key, (Object[]) ArrayUtil.toArray(brandList, PmsBrand.class));
//        redisService.sRemove(key, brandList.get(0));
//        Set<Object> cachedBrandList = redisService.sMembers(key);
//        return CommonResult.success(cachedBrandList);
//    }
//
//    @ApiOperation("测试List结构的缓存")
//    @RequestMapping(value = "/listTest", method = RequestMethod.GET)
//    @ResponseBody
//    public CommonResult<List<Object>> listTest() {
//        List<PmsBrand> brandList = brandService.list(1, 5);
//        String key = "redis:list:all";
//        redisService.lPushAll(key, (Object[]) ArrayUtil.toArray(brandList, PmsBrand.class));
//        redisService.lRemove(key, 1, brandList.get(0));
//        List<Object> cachedBrandList = redisService.lRange(key, 0, 3);
//        return CommonResult.success(cachedBrandList);
//    }
//}
