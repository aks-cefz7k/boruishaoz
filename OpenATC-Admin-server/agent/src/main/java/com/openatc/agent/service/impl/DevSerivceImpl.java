package com.openatc.agent.service.impl;

import com.google.gson.JsonObject;
import com.openatc.agent.model.SysOrg;
import com.openatc.agent.model.User;
import com.openatc.agent.resmodel.PageOR;
import com.openatc.agent.service.AscsDao;
import com.openatc.agent.service.DevService;
import com.openatc.agent.service.OrgService;
import com.openatc.agent.service.UserDao;
import com.openatc.core.common.IErrorEnumImplOuter;
import com.openatc.core.model.RESTRet;
import com.openatc.core.model.RESTRetBase;
import com.openatc.core.util.RESTRetUtils;
import com.openatc.model.model.AscsBaseModel;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname DevSerivceImpl
 * @Description
 * @Date 2022/4/8 18:41
 * @Created by panleilei
 */
@Service
public class DevSerivceImpl implements DevService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AscsDao mDao;

    @Autowired
    private OrgService orgService;

    /**
     * 根据用户角色返回设备
     * @return
     * @param ascsBaseModels
     */
    @Override
    public RESTRet getDevs(List<AscsBaseModel> ascsBaseModels) {
        // 管理员列表
        List<String> adminRoles = new ArrayList<>();
        adminRoles.add("superadmin");
        adminRoles.add("admin");

        // 获取当前登录用户信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        // 未开启shiro
        if (user == null){
            // 返回所有设备
            return RESTRetUtils.successObj(ascsBaseModels);
        }
        // 获取用户名
        String user_name = user.getUser_name();
        List<String> roles = userDao.getRoleNamesByUsername(user_name);
        adminRoles.retainAll(roles);
        // 非管理员
        if (adminRoles.size() == 0){
            user = userDao.getUserByUserName(user_name);
            String organization = user.getOrganization();
            // 该用户不属于任何组织机构
            if (organization.equals("")){
                // 返回没有组织机构的设备
                return RESTRetUtils.successObj(mDao.getAscsByOrgCode(ascsBaseModels,organization));
            }
            // 该用户属于某个或多个组织机构
            List<SysOrg> sysOrgs;
            if (organization.equals("*")){
                sysOrgs = orgService.findByOrgnizationCodeLike("");
            } else {
                sysOrgs = orgService.findByOrgnizationCodeLike(organization);
            }

            if (sysOrgs == null) return RESTRetUtils.errorObj(false, IErrorEnumImplOuter.E_3017);

            List<AscsBaseModel> devices = new ArrayList<>();
            for (SysOrg sysOrg : sysOrgs) {
                List<AscsBaseModel> ascsByCode = mDao.getAscsByOrgCode(ascsBaseModels,sysOrg.getOrgnization_code() );
                devices.addAll(ascsByCode);
            }
            return RESTRetUtils.successObj(devices);
        }

        // 其他情况返回所有设备
        return RESTRetUtils.successObj(ascsBaseModels);

    }

    /**
     * 条件查询后根据用户角色过滤设备
     * @param jsonObject
     * @return
     */
    @Override
    public RESTRetBase getRangeDevs(JsonObject jsonObject) {
        PageOR<AscsBaseModel> ascsRange = mDao.getAscsRange(jsonObject);
        if (ascsRange == null){
            return RESTRetUtils.successObj(null);
        }

        // 没有记录
        Long total = ascsRange.getTotal();
        if (total == 0){
            return RESTRetUtils.successObj(ascsRange);
        }

        RESTRet ret = getDevs(ascsRange.getContent());
        if (ret.isSuccess()){
            List<AscsBaseModel> ascsBaseModels = (List<AscsBaseModel>) ret.getData();
            ascsRange.setContent(ascsBaseModels);
            ascsRange.setTotal((long)(ascsBaseModels.size()));
            return RESTRetUtils.successObj(ascsRange);
        }
        return ret;
    }
}
