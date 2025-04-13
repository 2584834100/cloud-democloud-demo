package com.customer.controller;


import com.customer.service.CustomerService;
import common.Result;
import customer.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RefreshScope
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Resource
    private CustomerService customerService;

    @Value("${customer.key}")
    private String key;

    @GetMapping("/getCustomerById/{id}")
    public Result<CustomerDTO> getCustomerById(@PathVariable Integer id) {
        return Result.ok(customerService.getCustomerById(id));
    }

    @GetMapping("/getNacosConfig")
    public Result<Object> getNacosConfig() {
        return Result.ok(key);
    }

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    //读取key
    @GetMapping("/get/{keyName}")
    public String getKey(@PathVariable String keyName){
        return redisTemplate.opsForValue().get(keyName);
    }


    //设置key-value
    @GetMapping("/set/{keyName}/{value}")
    public String setKey(@PathVariable String keyName, @PathVariable String value){
        redisTemplate.opsForValue().set(keyName,value);
        return "set key success";
    }
}
