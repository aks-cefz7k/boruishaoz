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
package com.openatc.agent.realm;

import com.openatc.agent.model.Permission;
import com.openatc.agent.model.User;
import com.openatc.agent.service.PermissionDao;
import com.openatc.agent.service.UserDao;
import com.openatc.agent.utils.TokenUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;


public class MyRealm extends AuthorizingRealm {

    @Autowired(required = false)
    @Lazy
    private UserDao userDao;

    @Autowired(required = false)
    @Lazy
    private PermissionDao permissionDao;

    @Autowired
    @Lazy
    private TokenUtil tokenUtil;

    private Logger logger = LoggerFactory.getLogger(MyRealm.class);

    @Override
    public boolean supports(AuthenticationToken token) {
        //表示此Realm只支持JwtToken类型
        return token instanceof JwtToken;
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        User user = (User) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<Permission> permissions = permissionDao.getPermissionsByUserName(user.getUser_name());
        for (Permission permission : permissions) {
            info.addStringPermission(permission.getPermission_code());
        }
        return info;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        JwtToken jwtToken = (JwtToken) authenticationToken;
        String token = jwtToken.getToken();
        String ip = jwtToken.getIp();
        if (token == null) {
            throw new AuthenticationException("Token is null!");
        }
        String username = tokenUtil.getUsernameFromToken(token);

        if (username == null) {
            throw new AuthenticationException("Get null username from token!");
        }

        User user = userDao.getUserByUserName(username);
        //判断用户是否停用
        if (user.getStatus() == 0) {
            throw new AuthenticationException("Account has been deactivated!");
        }
        //判断token是否过期 true表示过期了
        boolean isExpired = tokenUtil.isTokenExpiredEx(token);
        if (isExpired) {
            throw new AuthenticationException("Token is expired!");
        }
//        if (!tokenUtil.checkip(ip, token)) {
//            throw new AuthenticationException("access ip is inconsistent with user ip!");
//        }

        try {
            return new SimpleAuthenticationInfo(user, token, getName());
        } catch (Exception e) {
            throw new AuthenticationException(e);
        }
    }
}

