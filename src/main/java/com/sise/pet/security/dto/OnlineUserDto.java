package com.sise.pet.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 在线用户
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlineUserDto {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * IP
     */
    private String ip;

    /**
     * token
     */
    private String key;

    /**
     * 登录时间
     */
    private Date loginTime;


}
