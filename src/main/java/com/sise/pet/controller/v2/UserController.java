package com.sise.pet.controller.v2;


import com.sise.pet.core.Result;
import com.sise.pet.core.ResultGenerator;
import com.sise.pet.service.IUserService;
import com.sise.pet.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-02-27
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisService redisService;

    /**
     * 判断旧密码是否正确
     * @param account
     * @param password
     * @return
     */
    @GetMapping("/password/check")
    public Result checkOldPassword(String account, String password) {
        Boolean code = userService.checkOldPassword(account, password);
        return ResultGenerator.genSuccessResult(code);
    }

    @PutMapping
    public Result updatePassword(String id,String newPassword,String captcha){
        return ResultGenerator.genSuccessResult();
    }
}
