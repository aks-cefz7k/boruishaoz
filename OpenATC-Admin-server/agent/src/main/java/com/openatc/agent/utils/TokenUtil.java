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
package com.openatc.agent.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.openatc.agent.model.Token;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class TokenUtil {

    //      String为token的内容，long为token的过期时间戳
    public volatile Map<String, Token> tokenMap = new HashMap<>();

    private Logger logger = LoggerFactory.getLogger(TokenUtil.class);

    @Value("${jwt.token.secret:kedacom}")
    private String secret;

    @Value("${jwt.token.expiration:86400}")
    private Long expiration; // 1 Day default

    private Algorithm algorithm = null;

    private JWTVerifier verifier = null;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    private synchronized Algorithm getAlgorithm() {
        if (algorithm == null) {
            algorithm = Algorithm.HMAC256(secret);
        }
        return algorithm;
    }

    private synchronized JWTVerifier getVerifier() {
        if (verifier == null) {
            verifier = JWT.require(getAlgorithm()).build();
        }
        return verifier;
    }

    /**
     * 生成token
     *
     * @param username 用户名
     * @return
     */
    public String generateToken(String username, Long timeStamp) {

        String token = JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(generateExpirationDate())
                .withKeyId(timeStamp.toString())
                .sign(getAlgorithm());

        return token;
    }

    /**
     * 生成token时间 = 当前时间 + expiration（properties中配置的失效时间）
     *
     * @return
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private Date generateExpirationDate(long expiration) {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

//    private String getUsername() {
//        Object principal = SecurityUtils.getSubject().getPrincipal();
//        return principal.toString();
//    }
//
//    private String getIp(){
//        Subject subject = SecurityUtils.getSubject();
//        String host = subject.getSession().getHost();
//        return host;
//    }

    /**
     * 根据token获取用户名
     *
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token) {

        if(token == null){
            return null;
        }

        try {
            DecodedJWT jwt = getVerifier().verify(token);
            return jwt.getSubject();
        } catch (JWTVerificationException exception) {
            //Invalid signature/claims
            logger.error("getUsernameFromToken error:{}", exception.getMessage());
            return null;
        } catch (NullPointerException e) {
            //Invalid signature/claims
            logger.error("getUsernameFromToken error:{}", e.getMessage());
            return null;
        }
    }

    /**
     * 判断token失效时间是否到了
     *
     * @param token
     * @return
     */
    private Boolean isTokenExpired(String token) throws ParseException {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 获取设置的token失效时间
     *
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token) {

        try {
            DecodedJWT jwt = getVerifier().verify(token);
            return jwt.getExpiresAt();
        } catch (JWTVerificationException exception) {
            //Invalid signature/claims
            logger.error("getExpirationDateFromToken:{}", exception.getMessage());
            return null;
        }
    }

    /**
     * Token失效校验
     *
     * @param token token字符串
     * @return
     */
    public Boolean validateToken(String token) throws ParseException {
        getUsernameFromToken(token);
        return !isTokenExpired(token);
    }

    public String getIdFromToken(String token) {
        try {
            DecodedJWT jwt = getVerifier().verify(token);
            return jwt.getKeyId();
        } catch (JWTVerificationException exception) {
            //Invalid signature/claims
            logger.error("getIdFromToken:{}", exception.getMessage());
            return null;
        } catch (NullPointerException e) {
            //Invalid signature/claims
            logger.error("getIdFromToken:{}", e.getMessage());
            return null;
        }
    }

    /**
     * Token失效校验，没有失效，则增加有效时间
     *
     * @param token token字符串
     * @return
     */
    public boolean isTokenExpiredEx(String token) throws AuthenticationException {
        if (tokenMap.get(token) == null) {
            throw new AuthenticationException("there is no token in tokenmap");
        }
        // 获取token的属性
        Token tokenAtt = tokenMap.get(token);
        long starttime = tokenAtt.getStarttime();
        long endtime = tokenAtt.getEndtime();
        long currenttime = System.currentTimeMillis();
        // token没有过期
        if (currenttime >= starttime && currenttime <= endtime) {
            // 从登录接口传过来的token需要续期
            if (tokenAtt.getSource() == 0) {
                if (endtime <= (currenttime + expiration)) {
                    tokenAtt.setEndtime(currenttime + expiration);
                    tokenMap.put(token, tokenAtt);
                }
            }
            return false;
        } else {
            return true;
        }
    }
}
