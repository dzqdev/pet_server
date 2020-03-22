package com.sise.pet.controller.v2;


import com.sise.pet.core.Result;
import com.sise.pet.core.ResultGenerator;
import com.sise.pet.entity.User;
import com.sise.pet.service.IUserService;
import com.sise.pet.service.RedisService;
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


}
