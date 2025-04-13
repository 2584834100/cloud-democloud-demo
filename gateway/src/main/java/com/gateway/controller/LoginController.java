package com.gateway.controller;

import com.gateway.utils.JwtUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import common.Result;

@RestController
public class LoginController {

    @GetMapping("/login")
    public Result<String> login(@RequestParam String username, @RequestParam String password) {
        return Result.ok(JwtUtil.generateToken(username, password));
    }
}
