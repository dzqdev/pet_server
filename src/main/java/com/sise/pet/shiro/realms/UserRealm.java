package com.sise.pet.shiro.realms;

import com.sise.pet.entity.User;
import com.sise.pet.service.IUserService;
import com.sise.pet.service.RedisService;
import com.sise.pet.shiro.JWTUtil;
import com.sise.pet.shiro.JwtToken;
import com.sise.pet.utils.Constant;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    private RedisService redisService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = ((JwtToken) authenticationToken).getToken();
        String tokenInRedis = null;
        try {
            tokenInRedis = redisService.get(Constant.TOKEN_CACHE_PREFIX + token);
        } catch (Exception ignore) {
        }
        // 如果找不到，说明已经失效
        if (StringUtils.isBlank(tokenInRedis)){
            throw new AuthenticationException("token已经过期");
        }

        String userId = JWTUtil.getUserId(token);
        if (StringUtils.isBlank(userId)){
            throw new AuthenticationException("token校验不通过");
        }

        User user = userService.getById(userId);
        if (user == null)
            throw new AuthenticationException("用户名或密码错误");

        return new SimpleAuthenticationInfo(token, token, getName());
    }
}
