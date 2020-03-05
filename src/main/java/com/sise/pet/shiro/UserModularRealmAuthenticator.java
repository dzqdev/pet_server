package com.sise.pet.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.Collection;

/**
 * @ClassName UserModularRealmAuthenticator
 * @Description TODO
 * @Date 2020/3/2 21:56
 * @Version 1.0
 **/
public class UserModularRealmAuthenticator extends ModularRealmAuthenticator {

    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        assertRealmsConfigured();
        JwtToken jwtToken = (JwtToken) authenticationToken;
        String token = jwtToken.getToken();
        String loginType = JWTUtil.getLoginType(token);
        Collection<Realm> realms = getRealms();
        Realm realm = null;
        for (Realm r : realms) {
            String name = r.getClass().getName();
            if (name.indexOf(loginType) > -1) {
                realm = r;
            }
        }
        if (realm == null) {
            return null;
        }
        return doSingleRealmAuthentication(realm, jwtToken);
    }

    @Override
    protected void assertRealmsConfigured() throws IllegalStateException {
        super.assertRealmsConfigured();
    }
}
