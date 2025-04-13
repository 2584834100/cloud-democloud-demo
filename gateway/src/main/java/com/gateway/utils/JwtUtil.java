package com.gateway.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {

    //密钥  长度有要求
    private static final String secretStr = "CIw6DzttvHA+XnrTa2B1EMhLoai1R0vC6jr0Q6y/qsU=";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretStr));
    private static final long EXPIRATION_TIME = 86400000; // 24 小时

    public static String generateToken(String username, String password) {
        return Jwts.builder()
                .claim("username", username)
                .claim("password", password)
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }


    public static String[] parseToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(SECRET_KEY) // 使用密钥验证
                .build()
                .parseSignedClaims(token); // 解析 JWT

        Claims claims = claimsJws.getPayload();
        String username = claims.get("username", String.class); // 提取 username
        String password = claims.get("password", String.class); // 提取 password

        return new String[]{username, password}; // 返回 username 和 password
    }

}
