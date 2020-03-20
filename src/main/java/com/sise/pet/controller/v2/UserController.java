package com.sise.pet.controller.v2;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sise.pet.core.Result;
import com.sise.pet.core.ResultGenerator;
import com.sise.pet.entity.User;
import com.sise.pet.exception.RedisConnectException;
import com.sise.pet.service.IUserService;
import com.sise.pet.service.RedisService;
import com.sise.pet.utils.CaptchaType;
import com.sise.pet.utils.SmsUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-02-27
 */
@RestController
@RequestMapping(value = {"/api/v1/user","/api/v2/user"})
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisService redisService;

    /**
     * 修改密码
     * @param account
     * @param newPassword
     * @param captcha
     * @return
     */
    @PutMapping("/updatePassword")
    public Result updatePassword(String account,String newPassword,String captcha){
        try {
            String redisCaptcha = redisService.get(CaptchaType.UPDATE_PASSWORD + account);
            if(StringUtils.isNotEmpty(redisCaptcha)){
                //缓存中的验证码
                if(StringUtils.equals(redisCaptcha,captcha)){
                    //验证码正确
                    User user = new User();
                    user.setPassword(newPassword);
                    QueryWrapper<User> wrapper = new QueryWrapper();
                    wrapper.eq("account", account);
                    userService.update(user, wrapper);
                    return ResultGenerator.genSuccessResult();
                }else{
                    return ResultGenerator.genFailResult("验证码错误或已过期");
                }
            }else{
                return ResultGenerator.genFailResult("验证码错误或已过期");
            }
        } catch (RedisConnectException e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult("获取验证码失败");
        }
    }

    /**
     * 获取某个用户的信息
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public Result get(@PathVariable Integer userId){
        User user = userService.getById(userId);
        user.setPassword("it's a secret");
        return ResultGenerator.genSuccessResult(user);
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @PutMapping
    public Result updateUser(User user){
        userService.updateById(user);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 发送短信
     * @param phone
     * @return
     */
    @GetMapping("/captcha")
    public Result getCaptcha(String phone,Integer captchaType){
        String value = CaptchaType.getValue(captchaType);
        Result result = SmsUtil.sendSms(phone,value);
        if(result.getCode() != 200){
            return ResultGenerator.genFailResult(result.getMessage());
        }
        return ResultGenerator.genSuccessResult();
    }


}
