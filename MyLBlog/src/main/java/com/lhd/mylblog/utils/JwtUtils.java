 package com.lhd.mylblog.utils;

import com.auth0.jwt.interfaces.Claim;
import com.lhd.mylblog.common.exception.Asserts;
import io.jsonwebtoken.*;
import jodd.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    private final long EXPIRATION = 7 * 60 * 60 * 24 * 1000; // 过期时间

    private String SECRET; // 密钥加盐

    private String tokenHeader;

    /**
     * 生成token字符串
     *
     * @param userId
     * @return
     */
    public String generateJwtToken(Long userId, String password){

        Date createDate = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION);
        Map<String, Object> claim = new HashMap<>();
        claim.put("userid",userId);
        claim.put("password",password);

        String   builder = Jwts.builder()
                .setHeaderParam("typ","jwt")
                .setHeaderParam("alg","HS256")
                .setSubject("blog-user")
                .setIssuedAt(createDate)
                .setExpiration(expirationDate)
                .setClaims(claim)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();

        return builder;
    }

    /**
     * 检验token是否有效
     *
     * @param token
     * @return
     */
    public boolean checkJwtToken(String token) {
        if(StringUtils.isEmpty(token)) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
        } catch (Exception e) {
            Asserts.fail(e.getMessage());
        }
        return true;
    }

    /**
     * 检验token是否存在与有效
     */
    public boolean checkJwtToken(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            return checkJwtToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 从token中取出用户id
     *
     * @param request 前端请求
     * @return int
     */
    public long getUserIdFromToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        if (!checkJwtToken(token)) {
            return -1L;
        }
        Jws<Claims> claimsJws = Jwts.parser()
                                .setSigningKey(SECRET)
                                .parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (long) claims.get("userid");
    }

    /**
     * 从token中取出用户id
     *
     * @param token token
     * @return int
     */
    public long getUserIdFromToken(String token) {
        if (!checkJwtToken(token)) {
            return -1L;
        }
        Jws<Claims> claimsJws = Jwts.parser()
                                .setSigningKey(SECRET)
                                .parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (long) claims.get("userid");
    }

    /**
     * 从token中取出用户password（加密后）
     *
     * @param request
     * @return
     */
    public String getUserPassFromToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        if (!checkJwtToken(token)) {
            return null;
        }
        Jws<Claims> claimsJws = Jwts.parser()
                                .setSigningKey(SECRET)
                                .parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String) claims.get("password");
    }

    /**
     * 从token中取出用户password（加密后）
     *
     * @param token
     * @return
     */
    public String getUserPassFromToken(String token) {
        if(!checkJwtToken(token)) {
            return null;
        }
        Jws<Claims> claimsJws = Jwts.parser()
                                .setSigningKey(SECRET)
                                .parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String) claims.get("password");
    }

}

