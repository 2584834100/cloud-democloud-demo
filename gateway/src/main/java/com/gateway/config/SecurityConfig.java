package com.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, JwtGlobalFilter jwtFilter) {
        return http
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/login/**").permitAll() // 允许公共访问
                        .anyExchange().authenticated() // 其他请求需要认证
                )
                .addFilterBefore(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION) // 添加 JWT 过滤器
//                .httpBasic(withDefaults()) // 启用 HTTP Basic 认证
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // 禁用 CSRF
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 使用 BCrypt 密码编码器
    }
}
