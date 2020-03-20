package com.sise.pet.controller.v2;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sise.pet.core.Result;
import com.sise.pet.core.ResultGenerator;
import com.sise.pet.entity.User;
import com.sise.pet.exception.RedisConnectException;
import com.sise.pet.service.IUserService;
import com.sise.pet.service.RedisService;
import com.sise.pet.shiro.JWTUtil;
import com.sise.pet.shiro.JwtToken;
import com.sise.pet.shiro.ShiroProperties;
import com.sise.pet.utils.CaptchaType;
import com.sise.pet.utils.Constant;
import com.sise.pet.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName LoginController
 * @Description TODO
 * @Date 2020/3/3 21:42
 * @Version 1.0
 **/
@RestController
@RequestMapping("api/v2")
public class UserLoginController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ShiroProperties shiroProperties;


    @PostMapping("/userLogin")
    public Result userLogin(@NotBlank(message = "{required}") String account,
                            @NotBlank(message = "{required}") String password) throws RedisConnectException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        User user = iUserService.getOne(queryWrapper);
        if (user == null || !StringUtils.equals(user.getPassword(), password)){
            return ResultGenerator.genFailResult("用户名或密码错误");
        }

        String token = JWTUtil.sign(user.getId().toString(), Constant.USER_LOGIN_TYPE, user.getPassword());
        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(86400L);
        String expireTimeStr = DateUtil.formatFullTime(expireTime);
        JwtToken jwtToken = new JwtToken(token, expireTimeStr);
        //保存token到redis
        redisService.set(Constant.TOKEN_CACHE_PREFIX + jwtToken.getToken(), jwtToken.getToken(), shiroProperties.getJwtTimeOut() * 1000);
        Map<String, Object> userInfo = generateUserInfo(jwtToken, user);
        return ResultGenerator.genSuccessResult(userInfo);
    }

    private Map<String, Object> generateUserInfo(JwtToken token, User user) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("token", token.getToken());
        userInfo.put("expireTime", token.getExpireAt());

        user.setPassword("it's a secret");
        userInfo.put("user", user);
        return userInfo;
    }


    @PostMapping("/register")
    public Result register(String account,String password,String captcha){
        //校验验证码是否正确
        try {
            String redisCaptcha = redisService.get(CaptchaType.REGISTER + account);
            if(StringUtils.isNotEmpty(redisCaptcha) && !StringUtils.equals(redisCaptcha,captcha)){
                return ResultGenerator.genFailResult("验证码过期或错误");
            }
            //验证码正确
            User user = new User();
            user.setAccount(account);
            user.setPassword(password);
            user.setUsername("用户" + account);
            user.setCreateTime(new Date());
            user.setAvatar(Constant.USER_DEFAULT_AVATAR);
            iUserService.save(user);
            return ResultGenerator.genSuccessResult();
        } catch (RedisConnectException e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult("服务器内部错误");
        }

    }
}
