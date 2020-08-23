package com.bins.realm;

import com.bins.bean.Permission;
import com.bins.bean.Role;
import com.bins.bean.User;
import com.bins.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;


public class NewsRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    public void setName(String name){
        setName("newsReal");
    }
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User)principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<Role> roles = user.getRoles();
        for(Role r:roles){
            info.addRole(r.getName());
            for(Permission p:r.getPermissions()){
                info.addStringPermission(p.getCode());
            }
        }
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //以前是从session中取出user

        //shiro是从token中获取user
        UsernamePasswordToken token=(UsernamePasswordToken)authenticationToken;
        String username = token.getUsername();
        String password = new String(token.getPassword());
        User user = userService.checkUser(username,password);
        if(user!=null){
            return new SimpleAuthenticationInfo(user,user.getPassword(),getName());
        }
        return null;
    }
}
