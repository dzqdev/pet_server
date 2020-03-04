package com.sise.pet.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sise.pet.core.Result;
import com.sise.pet.core.ResultGenerator;
import com.sise.pet.entity.User;
import com.sise.pet.service.IUserService;
import com.sise.pet.shiro.JWTUtil;
import com.sise.pet.shiro.JwtToken;
import com.sise.pet.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @ClassName LoginController
 * @Description TODO
 * @Date 2020/3/3 21:42
 * @Version 1.0
 **/
@RestController
@RequestMapping("api/v1")
public class LoginController {

    @Autowired
    private IUserService iUserService;

    @PostMapping("/userLogin")
    public Result userLogin(@NotBlank(message = "{required}") String account,
                            @NotBlank(message = "{required}") String password, HttpServletRequest request){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        User user = iUserService.getOne(queryWrapper);
        if (user == null || !StringUtils.equals(user.getPassword(), password)){
            return ResultGenerator.genFailResult("用户名或密码错误");
        }

        String token = JWTUtil.sign(user.getId().toString(), "user", user.getPassword());
        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(86400L);
        String expireTimeStr = DateUtil.formatFullTime(expireTime);
        JwtToken jwtToken = new JwtToken(token, expireTimeStr);
        return ResultGenerator.genSuccessResult();
    }
}
