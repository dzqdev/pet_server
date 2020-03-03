package com.sise.pet.shiro;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @ClassName UserToken
 * @Description TODO
 * @Date 2020/3/2 21:54
 * @Version 1.0
 **/
@Data
public class JwtToken implements AuthenticationToken {
    private String token;

    private String expireAt;


    public JwtToken(String token) {
        this.token = token;
    }

    public JwtToken(String token, String expireAt) {
        this.token = token;
        this.expireAt = expireAt;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
