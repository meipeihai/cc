package com.example.cc.utils;

import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtils {
    // 密钥
    public static final String SECRET = "mySecretKey";
    // 过期时间，单位毫秒
    public static final long EXPIRATION_TIME = 864_000_000; // 10天

    // 签名算法
    public static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;
    // 其他常量和方法
}
