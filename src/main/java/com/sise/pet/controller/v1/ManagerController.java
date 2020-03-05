package com.sise.pet.controller.v1;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sise.pet.core.Result;
import com.sise.pet.core.ResultGenerator;
import com.sise.pet.entity.Manager;
import com.sise.pet.exception.RedisConnectException;
import com.sise.pet.service.IManagerService;
import com.sise.pet.service.RedisService;
import com.sise.pet.shiro.JWTUtil;
import com.sise.pet.shiro.JwtToken;
import com.sise.pet.shiro.ShiroProperties;
import com.sise.pet.utils.Constant;
import com.sise.pet.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 管理员 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-03-05
 */
@RestController
@RequestMapping("/api/v1/manager")
public class ManagerController {

    @Autowired
    private IManagerService iManagerService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ShiroProperties shiroProperties;


    @PostMapping("/login")
    public Result managerLogin(@NotBlank(message = "{required}") String account,
                            @NotBlank(message = "{required}") String password) throws RedisConnectException {
        QueryWrapper<Manager> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        Manager manager = iManagerService.getOne(queryWrapper);
        if (manager == null || !StringUtils.equals(manager.getPassword(), password)){
            return ResultGenerator.genFailResult("用户名或密码错误");
        }

        String token = JWTUtil.sign(manager.getId().toString(), Constant.MANAGER_LOGIN_TYPE, manager.getPassword());
        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(86400L);
        String expireTimeStr = DateUtil.formatFullTime(expireTime);
        JwtToken jwtToken = new JwtToken(token, expireTimeStr);
        //保存token到redis
        redisService.set(Constant.TOKEN_CACHE_PREFIX + jwtToken.getToken(), jwtToken.getToken(), shiroProperties.getJwtTimeOut() * 1000);
        Map<String, Object> userInfo = generateUserInfo(jwtToken, manager);
        return ResultGenerator.genSuccessResult(userInfo);
    }

    private Map<String, Object> generateUserInfo(JwtToken token, Manager manager) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("token", token.getToken());
        userInfo.put("expireTime", token.getExpireAt());

        manager.setPassword("it's a secret");
        userInfo.put("user", manager);
        return userInfo;
    }
}
