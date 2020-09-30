package com.sise.pet.shiro;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
@Configuration
public class ShiroProperties {

    private List<String> anonUrl = new ArrayList<>();

    {
        anonUrl.add("/api/v1/auth/login");
        anonUrl.add("/api/v1/manager/login");
        anonUrl.add("/api/v1/manager/logout");
        anonUrl.add("/api/v1/pictures");
        anonUrl.add("/api/v1/videos");
        //前端页面
        anonUrl.add("/api/v2/register");
        anonUrl.add("/api/v2/userLogin");
        anonUrl.add("/api/v2/hospital/**");
        anonUrl.add("/api/v2/banner/**");
        anonUrl.add("/api/v2/article/**");
        anonUrl.add("/api/v2/boarding-home/**");
        anonUrl.add("/api/v2/pet/**");
        anonUrl.add("/api/v2/dict-detail/**");
        anonUrl.add("/api/v2/video/**");
        anonUrl.add("/api/v2/dict-detail/**");
        anonUrl.add("/api/v2/discussion/**");
        anonUrl.add("/api/v2/comment/discussion/**");
        anonUrl.add("/api/v2/captcha");
        anonUrl.add("/api/v2/updatePassword");

        //静态资源
        anonUrl.add("/images/**");
        anonUrl.add("/videos/**");
    }


    /**
     * token默认有效时间 1天
     */
    private Long jwtTimeOut = 86400L;

    public List<String> getAnonUrl() {
        return anonUrl;
    }

    public void setAnonUrl(List<String> anonUrl) {
        this.anonUrl = anonUrl;
    }

    public Long getJwtTimeOut() {
        return jwtTimeOut;
    }

    public void setJwtTimeOut(Long jwtTimeOut) {
        this.jwtTimeOut = jwtTimeOut;
    }
}
