package com.sise.pet.security.service;

import com.sise.pet.security.config.SecurityProperties;
import com.sise.pet.security.vo.JwtUser;
import com.sise.pet.security.vo.OnlineUser;
import com.sise.pet.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author Zheng Jie
 * @Date 2019年10月26日21:56:27
 */
@Service
@Slf4j
public class OnlineUserService {

    private final SecurityProperties properties;
    private RedisUtils redisUtils;

    public OnlineUserService(SecurityProperties properties, RedisUtils redisUtils) {
        this.properties = properties;
        this.redisUtils = redisUtils;
    }

    /**
     * 保存在线用户信息
     * @param jwtUser /
     * @param token /
     * @param request /
     */
    public void save(JwtUser jwtUser, String token, HttpServletRequest request){
        String ip = null;
        String browser = null;
        String address = null;
        OnlineUser onlineUser = null;
        try {
            onlineUser = new OnlineUser(jwtUser.getUsername(), browser , ip, address, token, new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        redisUtils.set(properties.getOnlineKey() + token, onlineUser, properties.getTokenValidityInSeconds()/1000);
    }

    /**
     * 查询全部数据
     * @param filter /
     * @param pageable /
     * @return /
     */
    public Map<String,Object> getAll(String filter, Pageable pageable){
        List<OnlineUser> onlineUsers = getAll(filter);
        return null;
    }

    /**
     * 查询全部数据，不分页
     * @param filter /
     * @return /
     */
    public List<OnlineUser> getAll(String filter){
        List<String> keys = redisUtils.scan(properties.getOnlineKey() + "*");
        Collections.reverse(keys);
        List<OnlineUser> onlineUsers = new ArrayList<>();
        for (String key : keys) {
            OnlineUser onlineUser = (OnlineUser) redisUtils.get(key);
            if(StringUtils.isNotBlank(filter)){
                if(onlineUser.toString().contains(filter)){
                    onlineUsers.add(onlineUser);
                }
            } else {
                onlineUsers.add(onlineUser);
            }
        }
        onlineUsers.sort((o1, o2) -> o2.getLoginTime().compareTo(o1.getLoginTime()));
        return onlineUsers;
    }

    /**
     * 踢出用户
     * @param key /
     * @throws Exception /
     */
    public void kickOut(String key) throws Exception {
        key = properties.getOnlineKey() + key;
        redisUtils.del(key);
    }

    /**
     * 退出登录
     * @param token /
     */
    public void logout(String token) {
        String key = properties.getOnlineKey() + token;
        redisUtils.del(key);
    }


    /**
     * 查询用户
     * @param key /
     * @return /
     */
    public OnlineUser getOne(String key) {
        return (OnlineUser)redisUtils.get(key);
    }

    /**
     * 检测用户是否在之前已经登录，已经登录踢下线
     * @param userName 用户名
     */
    public void checkLoginOnUser(String userName, String igoreToken){
        List<OnlineUser> onlineUsers = getAll(userName);
        if(onlineUsers ==null || onlineUsers.isEmpty()){
            return;
        }
        for(OnlineUser onlineUser:onlineUsers){
            if(onlineUser.getUserName().equals(userName)){
                try {
                    String token =onlineUser.getKey();
                    if(StringUtils.isNotBlank(igoreToken)&&!igoreToken.equals(token)){
                        this.kickOut(onlineUser.getKey());
                    }else if(StringUtils.isBlank(igoreToken)){
                        this.kickOut(onlineUser.getKey());
                    }
                } catch (Exception e) {
                    log.error("checkUser is error",e);
                }
            }
        }
    }

}
