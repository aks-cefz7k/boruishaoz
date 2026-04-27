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
package com.openatc.agent.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.openatc.agent.model.Role;
import com.openatc.agent.model.Token;
import com.openatc.agent.model.User;
import com.openatc.agent.model.UserRole;
import com.openatc.agent.realm.JwtToken;
import com.openatc.agent.service.PermissionDao;
import com.openatc.agent.service.RoleDao;
import com.openatc.agent.service.UserDao;
import com.openatc.agent.service.UserRoleDao;
import com.openatc.agent.utils.DateUtil;
import com.openatc.agent.utils.TokenUtil;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.Logger;

import static com.openatc.core.common.IErrorEnumImplOuter.*;


/**
 * @author kedacom
 * @ClassName: UserController
 * @Description:
 * @date 2019年10月19日 下午5:27:48
 */
@RestController
@CrossOrigin
public class UserController {
    private static Logger logger = Logger.getLogger(UserController.class.toString());
    private static String STANDARD_DATE_FORMAT_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    //    public volatile Map<String, Token> tokenMap = new HashMap<>();
    public long timestamp;
    @Autowired(required = false)
    protected UserDao userDao;
    @Autowired(required = false)
    protected PermissionDao permissionDao;
    @Autowired(required = false)
    protected UserRoleDao userRoleDao;
    @Autowired(required = false)
    protected RoleDao roleDao;
    @Autowired
    protected TokenUtil tokenUtil;
    @Value("${default.user.password}")
    private String initialPassword;

    /**
     * 根据用户创建授权token
     */
    @PostMapping(value = "/auth/user/token")
    public RESTRetBase getTokenByUsername(@RequestBody JsonObject jsonObject) {
        String userName = jsonObject.get("user_name").getAsString();
        String endtime = jsonObject.get("end_time").getAsString();
        String starttime = jsonObject.get("start_time").getAsString();
        Date enddate = DateUtil.stringToDate(endtime);
        Date startdate = DateUtil.stringToDate(starttime);
        String token = tokenUtil.generateToken(userName, System.currentTimeMillis());
        tokenUtil.tokenMap.put(token, new Token(token, 1, startdate.getTime(), enddate.getTime()));
        Map resultmap = new HashMap();
        resultmap.put("token", token);
        return RESTRetUtils.successObj(resultmap);
    }

    /**
     * @return RESTRetBase
     * @Title: edgeLogin
     * @Description: com.app.name=openatc 有openatc表示平台版本, 需要配置shiro, 且ShiroOpenUtil的返回值为true
     */
    @PostMapping(value = "/auth/login")
    public RESTRetBase edgeLogin(HttpServletRequest httpServletRequest, @RequestBody JsonObject loginUser) throws Exception {
        String userName = loginUser.get("user_name").getAsString();
        String password = loginUser.get("password").getAsString();
        timestamp = loginUser.get("timestamp").getAsLong();
        String ip = getRemoteIP(httpServletRequest);
        User user = userDao.getUserByUserName(userName);

        //不存在该用户
        if (user == null) {
            return RESTRetUtils.errorObj(E_3011);
        }

        //从数据库获取密码，用时间戳加密，与password进行对比
        String md5str = Base64.getEncoder().encodeToString(DigestUtils.md5(user.getPassword() + timestamp));
        if (!password.equals(md5str)) {
            return RESTRetUtils.errorObj(E_3011);
        }

        Subject subject = SecurityUtils.getSubject();
        String token = tokenUtil.generateToken(userName, timestamp);
        tokenUtil.tokenMap.put(token, new Token(token, 0, System.currentTimeMillis(), System.currentTimeMillis() + 86400000L));
        JwtToken loginJwt = JwtToken.builder().token(token).ip(ip).build();
        try {
            subject.login(loginJwt);
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);

            //判断用户是否过期
            if (user.getExpiration_time() == null || user.getExpiration_time().before(new Date())) {
                map.put("expire", true);
                return RESTRetUtils.successObj(map);
            }
            map.put("expire", false);
            return RESTRetUtils.successObj(map);
        } catch (AuthenticationException ex) {
            if (ex.getMessage().equals("Account has been deactivated!")) {
                return RESTRetUtils.errorObj(E_3013);
            } else if (ex.getMessage().equals("Token is expired!")) {
                return RESTRetUtils.errorObj(E_3014);
            } else if (ex.getMessage().equals("access ip is inconsistent with user ip!")){
                return RESTRetUtils.errorObj(E_3018);
            }
            return RESTRetUtils.errorObj(E_3002);
        }
    }

    /**
     * @return RESTRetBase
     * @Title: edgeInfo
     * @Description:
     */
    @GetMapping(value = "/auth/info")  //获取登录用户信息
    public RESTRetBase edgeInfo(@RequestHeader("Authorization") String token) {

        String username = tokenUtil.getUsernameFromToken(token);
//        User user = (User) SecurityUtils.getSubject().getPrincipal();

        if (username != null) {
            User user = userDao.getUserByUserName(username);
            if(user == null){
                return RESTRetUtils.errorObj(E_3011);
            }
            List<String> roleNames= userDao.getRoleNamesByUsername(user.getUser_name());
            user.setRoleNames(roleNames);
            return RESTRetUtils.successObj(user);
        }
        return RESTRetUtils.errorObj(E_3011);
    }

    /**
     * @return RESTRetBase
     * @Title: edgeLogout
     * @Description:
     */
    @GetMapping(value = "/auth/logout")  //登出
    public RESTRetBase edgeLogout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return RESTRetUtils.successObj("登出成功！");
    }

    /**
     * @param pageNum pageNum
     * @param pageRow pageRow
     * @return RESTRetBase
     * @Title: getUsers
     * @Description:
     */
    @RequiresPermissions({"sys:auth:user:view"})  //获取所有用户信息
    @GetMapping(value = "/auth/user")
    public RESTRetBase getUsers(@RequestParam(required = false) Integer pageNum,
                                @RequestParam(required = false) Integer pageRow) throws JsonProcessingException {

        //不分页获取
        if (pageNum == null || pageRow == null) {
            List<User> users = userDao.findAllUser();
            for (User user : users) {
                List<String> rolenames = userDao.getRoleNamesByUsername(user.getUser_name());
                user.setRoleNames(rolenames);
            }
            return RESTRetUtils.successObj(users);
        }

        //分页获取
//        Sort sort = new Sort(Sort.Direction.ASC, "user_name");
//        Pageable pageable = new PageRequest(pageNum, pageRow, sort); //分页设置
        Pageable pageable = PageRequest.of(pageNum, pageRow); //分页设置

        Page<User> users = userDao.findAll(pageable);
        for (User user : users) {
            List<String> rolenames = userDao.getRoleNamesByUsername(user.getUser_name());
            user.setRoleNames(rolenames);
        }
        return RESTRetUtils.successObj(users);
    }

    /**
     * @param userName userName
     * @return RESTRetBase
     * @Title: getSingleUser
     * @Description:
     */
    @RequiresPermissions({"sys:auth:user:view"})  //获取单个用户信息
    @GetMapping(value = "/auth/user/{userName}")
    public RESTRetBase getSingleUser(@PathVariable String userName) {
        User user = userDao.getUserByUserName(userName);
        if (user == null) {
            return RESTRetUtils.errorObj(E_3011);
        }
        List<String> rolenames = userDao.getRoleNamesByUsername(user.getUser_name());
        user.setRoleNames(rolenames);
        return RESTRetUtils.successObj(user);
    }


    /**
     * @param joinUser joinUser
     * @return RESTRetBase
     * @Title: addUser
     * @Description:
     */
    @RequiresPermissions({"sys:auth:user:modify"})  //添加用户
    @PostMapping(value = "/auth/user")
    public RESTRetBase addUser(@RequestBody User joinUser) {

//        if (joinUser.getPassword() == null) {
//            return RESTRetUtils.errorObj(E_3005);
//        }
        String userName = joinUser.getUser_name();
        String dbPassword = Base64.getEncoder().encodeToString(DigestUtils.md5(userName + initialPassword));
        joinUser.setPassword(dbPassword);
        if (userDao.getUserByUserName(joinUser.getUser_name()) != null) {
            return RESTRetUtils.errorObj(E_3003);
        }
        userDao.save(joinUser);
        List<String> roleNames = joinUser.getRoleNames();
        if (roleNames != null) {
            for (String name : roleNames) {
                Role role = roleDao.getRoleByUserName(name);
                UserRole userRole = new UserRole(joinUser, role);
                userRoleDao.save(userRole);
            }
        }

        return RESTRetUtils.successObj();
    }

    /**
     * @return RESTRetBase
     * @Title: addUser
     * @Description:
     */
    @RequiresPermissions({"sys:auth:user:modify"})  //重置密码
    @PutMapping(value = "/auth/user/reset/{userName}")
    public RESTRetBase resetUser(@PathVariable String userName) {
        User user = userDao.getUserByUserName(userName);
        String dbPassword = Base64.getEncoder().encodeToString(DigestUtils.md5(userName + initialPassword));
        user.setPassword(dbPassword);
        user.setExpiration_time(new Date());
        userDao.save(user);
        return RESTRetUtils.successObj();
    }

    /**
     * @param
     * @param userName userName
     * @return RESTRetBase
     * @Title: updateUserInfo
     * @Description:
     */
    @RequiresPermissions({"sys:auth:user:modify"})       //修改用户
    @PutMapping(value = "/auth/user/{userName}")
    public RESTRetBase updateUserInfo(@PathVariable String userName, @RequestBody JsonObject jsonObject) {

        Set<String> set = jsonObject.keySet();
        User olduser = userDao.getUserByUserName(userName);
        if (set.contains("expiration_time") && !jsonObject.get("expiration_time").isJsonNull()) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(STANDARD_DATE_FORMAT_UTC);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date expirationTime = null;
            try {
                expirationTime = simpleDateFormat.parse(jsonObject.get("expiration_time").getAsString());
                olduser.setExpiration_time(expirationTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
//        if (set.contains("expiration_time") && jsonObject.get("expiration_time").isJsonNull()) {
//            olduser.setExpiration_time(null);
//        }
        if (set.contains("user_name")) {
            String modiUserName = jsonObject.get("user_name").getAsString();
            if (userDao.getUserByUserName(modiUserName) != null) {
                return RESTRetUtils.errorObj(E_3003);
            }
            olduser.setUser_name(modiUserName);
        }
        if (set.contains("status")) {
            olduser.setStatus(jsonObject.get("status").getAsShort());
        }
        if (set.contains("password")) {
            olduser.setPassword(jsonObject.get("password").getAsString());
        }
        if (set.contains("nick_name")) {
            olduser.setNick_name(jsonObject.get("nick_name").getAsString());
        }
        if (set.contains("mobile_phone")) {
            olduser.setMobile_phone(jsonObject.get("mobile_phone").getAsString());
        }
        if (set.contains("email")) {
            olduser.setEmail(jsonObject.get("email").getAsString());
        }
        if (set.contains("organization")) {
            olduser.setOrganization(jsonObject.get("organization").getAsString());
        }
        if (set.contains("roleNames")) {
            //先查看该用户用否有超级管理远角色，如果有不允许修改
            List<String> currentUserRoleNames = userDao.getRoleNamesByUsername(olduser.getUser_name());
            if (currentUserRoleNames.contains("superadmin")) {
                return RESTRetUtils.errorObj(E_3010);
            }

            //先清空用户原来的角色
            List<UserRole> userRoles = userRoleDao.findByUser(olduser);

            //如果userRoles的长度不为0，表示用户已有角色，需要先清空
            if (userRoles.size() != 0) {
                for (UserRole userRole : userRoles) {
                    userRoleDao.delete(userRole);
                }
            }
            Gson gson = new Gson();
            List<String> roleNames = gson.fromJson(jsonObject.get("roleNames"), List.class);
            for (String name : roleNames) {
                Role role = roleDao.getRoleByUserName(name);
                UserRole userRole = new UserRole(olduser, role);
                userRoleDao.save(userRole);
            }
        }
        userDao.save(olduser);
        List<String> rolenames = userDao.getRoleNamesByUsername(olduser.getUser_name());
        olduser.setRoleNames(rolenames);
        return RESTRetUtils.successObj(olduser);
    }

    /**
     * @param userName userName
     * @return RESTRetBase
     * @Title: deleteUser
     * @Description:
     */
    @RequiresPermissions({"sys:auth:user:modify"})  //删除用户
    @DeleteMapping(value = "/auth/user/{userName}")
    public RESTRetBase deleteUser(@PathVariable String userName) {
        User olduser = userDao.getUserByUserName(userName);
        if (olduser == null) {
            return RESTRetUtils.errorObj(E_3011);
        }
        //先从UserRole表中删除用户权限
        List<UserRole> userRoles = userRoleDao.findByUser(olduser);
        if (userRoles != null) {
            for (UserRole userRole : userRoles) {
                userRoleDao.delete(userRole);
            }
        }
        userDao.delete(olduser);
        return RESTRetUtils.successObj();
    }

    /**
     * @param jsonObject
     * @return RESTRetBase
     * @Title: updatePassword
     * @Description:
     */
    @PostMapping(value = "/auth/password")    //修改密码
    public RESTRetBase updatePassword(@RequestBody JsonObject jsonObject) {
        //获取登录用户
        User subject = (User) SecurityUtils.getSubject().getPrincipal();
        String old_password = jsonObject.get("old_password").getAsString();
        String new_password = jsonObject.get("new_password").getAsString();

        if (!subject.getPassword().equals(old_password)) {
            return RESTRetUtils.errorObj(E_3006);
        }
        subject.setPassword(new_password);
        if (!subject.getUser_name().equals("admin")) {
            subject.setExpiration_time(getHalfYearLaterDate());
        }
        userDao.save(subject);
        return RESTRetUtils.successObj();
    }

    //获得半年后的日期
    public Date getHalfYearLaterDate() {
        LocalDate localDate = LocalDate.now().plus(6, ChronoUnit.MONTHS);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * @param roles    roles
     * @param userName userName
     * @return RESTRetBase
     * @Title: updateUserRole
     * @Description:
     */
    @RequiresPermissions({"sys:auth:user:modify"})
    @PutMapping(value = "/auth/user/{userName}/roles")   //修改用户的所有角色
    public RESTRetBase updateUserRole(@PathVariable String userName, @RequestBody List<Role> roles) {
        User user = userDao.getUserByUserName(userName);
        List<UserRole> userRoles = userRoleDao.findByUser(user);

        //如果userRoles的长度为0，表示该用户还未拥有任何角色，此接口为给用户增加角色
        if (userRoles.size() == 0) {
            for (Role role : roles) {
                UserRole userRole = new UserRole(user, role);
                userRoleDao.save(userRole);
            }
            return RESTRetUtils.successObj();
        }

        //否则先清空再增加角色
        for (UserRole userRole : userRoles) {
            userRoleDao.delete(userRole);
        }
        for (Role role : roles) {
            UserRole userRole = new UserRole(user, role);
            userRoleDao.save(userRole);
        }
        return RESTRetUtils.successObj();
    }

    /**
     * @param userName userName
     * @return RESTRetBase
     * @Title: getUserRole
     * @Description:
     */
    @RequiresPermissions({"sys:auth:user:view"})
    @GetMapping(value = "/auth/user/{userName}/roles")    //获取用户的所有角色
    public RESTRetBase getUserRole(@PathVariable String userName) {
        User user = userDao.getUserByUserName(userName);
        if (user == null) {
            return RESTRetUtils.errorObj(E_3011);
        }
        List<UserRole> userRoles = userRoleDao.findByUser(user);
        List<Role> roles = new ArrayList<>();
        for (UserRole userRole : userRoles) {
            roles.add(userRole.getRole());
        }
        return RESTRetUtils.successObj(roles);
    }

    public String getRemoteIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }

    public static String md5(String text, String key) throws Exception {
        //加密后的字符串
        String encodeStr = DigestUtils.md5Hex(text + key);
        return encodeStr;
    }
}
