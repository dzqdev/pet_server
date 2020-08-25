package com.sise.pet.controller.v1;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.core.CommonResult;
import com.sise.pet.entity.Manager;
import com.sise.pet.exception.RedisConnectException;
import com.sise.pet.service.IManagerService;
import com.sise.pet.service.RedisService;
import com.sise.pet.shiro.JWTUtil;
import com.sise.pet.shiro.JwtToken;
import com.sise.pet.shiro.ShiroProperties;
import com.sise.pet.utils.Constant;
import com.sise.pet.utils.DateUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Date;
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
    public CommonResult managerLogin(@NotBlank(message = "{required}") String account,
                                     @NotBlank(message = "{required}") String password) throws RedisConnectException {
        QueryWrapper<Manager> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        Manager manager = iManagerService.getOne(queryWrapper);
        if (manager == null || !StringUtils.equals(manager.getPassword(), password)){
            return CommonResult.failed("用户名或密码错误");
        }

        String token = JWTUtil.sign(manager.getId().toString(), Constant.MANAGER_LOGIN_TYPE, manager.getPassword());
        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(86400L);
        String expireTimeStr = DateUtil.formatFullTime(expireTime);
        JwtToken jwtToken = new JwtToken(token, expireTimeStr);
        //保存token到redis
        redisService.set(Constant.TOKEN_CACHE_PREFIX + jwtToken.getToken(), jwtToken.getToken(), shiroProperties.getJwtTimeOut() * 1000);
        Map<String, Object> userInfo = generateUserInfo(jwtToken, manager);
        return CommonResult.success(userInfo);
    }

    private Map<String, Object> generateUserInfo(JwtToken token, Manager manager) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("token", token.getToken());
        userInfo.put("expireTime", token.getExpireAt());

        manager.setPassword("it's a secret");
        userInfo.put("user", manager);
        return userInfo;
    }

    @DeleteMapping("/logout")
    public CommonResult logout(String token){
        try {
            redisService.del(Constant.TOKEN_CACHE_PREFIX + token);
            return CommonResult.success(null);
        } catch (RedisConnectException e) {
            e.printStackTrace();
            return CommonResult.failed("退出登录失败");
        }

    }

    @PostMapping
    public CommonResult add(Manager manager){
        manager.setRole("manager");
        manager.setAvatar(Constant.USER_DEFAULT_AVATAR);
        manager.setCreateTime(new Date());
        manager.setName("管理员" + RandomStringUtils.randomAlphanumeric(6));
        iManagerService.save(manager);
        return CommonResult.success(null);
    }

    @PutMapping
    public CommonResult update(Manager manager){
        iManagerService.updateById(manager);
        return CommonResult.success(null);
    }

    @GetMapping
    public CommonResult managerList(Manager manager, Page page){
        Page<Manager> list = iManagerService.selectPage(manager,page);
        return CommonResult.success(list);
    }

    @GetMapping("/validate/{account}")
    public CommonResult accountValidate(@PathVariable String account){
        QueryWrapper<Manager> condition = new QueryWrapper<>();
        condition.eq("account", account);
        Manager manager = iManagerService.getOne(condition);
        if(null == manager){
            return CommonResult.success(false);
        }else{
            return CommonResult.success(true);
        }

    }

    @GetMapping("/{id}")
    public CommonResult get(@PathVariable String id){
        Manager manager = iManagerService.getById(id);
        return CommonResult.success(manager);
    }

    @DeleteMapping("/{id}")
    public CommonResult delete(@PathVariable String id){
        iManagerService.removeById(id);
        return CommonResult.success(null);
    }
}
