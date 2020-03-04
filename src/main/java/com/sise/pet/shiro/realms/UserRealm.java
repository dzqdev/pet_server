package com.sise.pet.shiro.realms;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sise.pet.entity.User;
import com.sise.pet.service.IUserService;
import com.sise.pet.shiro.JWTUtil;
import com.sise.pet.shiro.JwtToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName UserRealm
 * @Description TODO
 * @Date 2020/3/2 21:59
 * @Version 1.0
 **/
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken token = (JwtToken) authenticationToken;
        String s = token.getToken();
        String principal = (String)token.getPrincipal();
        Object credentials = token.getCredentials();
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("account", principal);
        queryWrapper.eq("password", credentials);
        User user = userService.getOne(queryWrapper);
        if(null == user){
            return null;
        }
        return new SimpleAuthenticationInfo(principal,credentials,getName());
    }
}
