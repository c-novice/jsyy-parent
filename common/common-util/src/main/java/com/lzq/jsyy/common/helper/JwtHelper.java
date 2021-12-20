package com.lzq.jsyy.common.helper;

import com.alibaba.excel.util.StringUtils;
import io.jsonwebtoken.*;

import java.util.Date;

/**
 * JWT工具类
 *
 * @author lzq
 */
public class JwtHelper {
    /**
     * 过期时间 ms
     */
    private static final long TOKEN_EXPIRATION = 24 * 60 * 60 * 1000;
    /**
     * 签名密钥
     */
    private static final String TOKEN_SIGN_KEY = "123456";

    /**
     * 生成token
     *
     * @param userId
     * @param userName
     * @return
     */
    public static String createToken(String userId, String userName) {
        String token = Jwts.builder()
                .setSubject("JSYY-USER")
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
                .claim("userId", userId)
                .claim("userName", userName)
                .signWith(SignatureAlgorithm.HS512, TOKEN_SIGN_KEY)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    /**
     * 根据token字符串得到用户id
     *
     * @param token
     * @return
     */
    public static String getUserId(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(TOKEN_SIGN_KEY).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String) claims.get("userId");
    }

    /**
     * 根据token字符串得到用户名称
     *
     * @param token
     * @return
     */
    public static String getUserName(String token) {
        if (StringUtils.isEmpty(token)) {
            return "";
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(TOKEN_SIGN_KEY).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String) claims.get("userName");
    }
}
