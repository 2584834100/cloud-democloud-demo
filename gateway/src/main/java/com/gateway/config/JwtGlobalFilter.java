package com.gateway.config;

import com.gateway.utils.JwtUtil;
import io.micrometer.common.lang.NonNull;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
public class JwtGlobalFilter implements WebFilter {

    @NonNull
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        // 从请求头中获取 Authorization 字段
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // 去掉 "Bearer " 前缀
            try {
                // 解析 JWT 令牌
                String[] user = JwtUtil.parseToken(token);
                if (user != null) {

                    // 创建认证对象
                    SecurityContext context = new SecurityContextImpl(
                            new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList())
                    );

                    // ***************** 将用户信息添加到请求头中 请求头传递用户信息
                    ServerHttpRequest request = exchange.getRequest().mutate()
                            .header("X-User-Name", user[0])
                            .header("X-User-Pwd", user[1])
                            .build();
                    // 将认证对象放入上下文
                    return chain.filter(exchange.mutate().request(request).build())
                            .doOnTerminate(() -> {
                                System.out.println("Request completed, cleaning up..."); // 调试日志
                                ReactiveSecurityContextHolder.clearContext();
                            })
                            .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(context)));
                }
            } catch (Exception e) {
                // JWT 解析失败
                System.err.println("JWT 解析失败: " + e.getMessage());
            }
        }
        // 继续执行过滤器链
        return chain.filter(exchange);
    }


}