/**
 * Copyright (c) 2020 kedacom
 * OpenATC is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 **/
package com.openatc.configserver.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Data
public class TokenUtil {
    private final static String secret = "kedacom"; //密码
    private static Long expiration = 86400L; // 过期事件1天
    private static Logger logger = Logger.getLogger(TokenUtil.class.getName());
    //  String为token的内容，long为token的过期时间戳
    public Map<String, Long> tokenMap = new HashMap<>();
    private static Algorithm algorithm = null;
    private static JWTVerifier verifier = null;

    private static synchronized Algorithm getAlgorithm() {
        if (algorithm == null) {
            algorithm = Algorithm.HMAC256(secret);
        }
        return algorithm;
    }

    private static synchronized JWTVerifier getVerifier() {
        if (verifier == null) {
            verifier = JWT.require(getAlgorithm()).build();
        }
        return verifier;
    }

    /**
     * 生成token
     * @param username 用户名
     * @return
     */
    public static String generateToken(String username, Long timeStamp) {

        String token = JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withKeyId(timeStamp.toString())
                .sign(getAlgorithm());
        return token;
    }

    /**
     * 检验token是否正确
     * @param token token
     * @return
     */
    public static boolean verify(String token){
        try {
            getVerifier().verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }


    /**
     * 生成token时间 = 当前时间 + expiration（properties中配置的失效时间）
     *
     * @return
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 根据token获取用户名
     *
     * @param token
     * @return
     */
    public static String getUsernameFromToken(String token) {

        try {
            DecodedJWT jwt = getVerifier().verify(token);
            return jwt.getSubject();
        } catch (JWTVerificationException exception) {
            //Invalid signature/claims
            logger.warning("getUsernameFromToken: " + exception.getMessage());
            return null;
        } catch (NullPointerException e) {
            //Invalid signature/claims
            logger.warning("getUsernameFromToken: " + e.getMessage());
            return null;
        }
    }

    /**
     * 判断token失效时间是否到了
     *
     * @param token
     * @return
     */
    private static Boolean isTokenExpired(String token) throws ParseException {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 获取设置的token失效时间
     *
     * @param token
     * @return
     */
    public static Date getExpirationDateFromToken(String token) {

        try {
            DecodedJWT jwt = getVerifier().verify(token);
            return jwt.getExpiresAt();
        } catch (JWTVerificationException exception) {
            //Invalid signature/claims
            logger.warning("getExpirationDateFromToken: " + exception.getMessage());
            return null;
        }
    }

    /**
     * Token失效校验
     * @param token token字符串
     * @return
     */
    public static Boolean validateToken(String token) throws ParseException {
        getUsernameFromToken(token);
        return !isTokenExpired(token);
    }

    public String getIdFromToken(String token) {
        try {
            DecodedJWT jwt = getVerifier().verify(token);
            return jwt.getKeyId();
        } catch (JWTVerificationException exception) {
            //Invalid signature/claims
            logger.warning("getIdFromToken: " + exception.getMessage());
            return null;
        } catch (NullPointerException e) {
            //Invalid signature/claims
            logger.warning("getIdFromToken: " + e.getMessage());
            return null;
        }
    }

    /**
     * Token失效校验，没有失效，则增加有效时间
     *
     * @param token token字符串
     * @return
     */
//    public boolean isTokenExpiredEx(String token) throws AuthenticationException {
//        if (tokenMap.get(token) == null) {
//            throw new AuthenticationException("there is no token in tokenmap");
//        }
//
//
//        //token的过期时间戳 expirationDate
//        long expirationDate = ((Long) tokenMap.get(token)).longValue();
//        // 当前时间戳
//        long currentDate = System.currentTimeMillis();
//        if (expirationDate > currentDate) {
//            //token没有过期
//            tokenMap.put(token, currentDate + expiration);
//            return false;
//        } else {
//            return true;
//        }
//    }
}
