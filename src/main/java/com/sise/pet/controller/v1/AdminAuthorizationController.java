package com.sise.pet.controller.v1;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sise.pet.core.CommonResult;
import com.sise.pet.dto.SysMenuDto;
import com.sise.pet.dto.SysUserDto;
import com.sise.pet.dto.convert.SysUserConvert;
import com.sise.pet.entity.Manager;
import com.sise.pet.entity.SysRole;
import com.sise.pet.entity.SysUser;
import com.sise.pet.exception.RedisConnectException;
import com.sise.pet.service.ISysUserService;
import com.sise.pet.service.RedisService;
import com.sise.pet.shiro.JWTUtil;
import com.sise.pet.shiro.JwtToken;
import com.sise.pet.shiro.ShiroProperties;
import com.sise.pet.utils.Constant;
import com.sise.pet.utils.DateUtil;
import com.sise.pet.vo.MenuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Api(tags = "AdminAuthorizationController",description = "后台用户认证")
@RequestMapping("/api/v1/auth")
@RestController
public class AdminAuthorizationController {
    @Resource
    private ISysUserService sysUserService;

    @Resource
    private RedisService redisService;

    @Resource
    private ShiroProperties shiroProperties;

    @Resource
    private SysUserConvert sysUserConvert;


    @PostMapping("/login")
    public CommonResult login(String username, String password) throws RedisConnectException {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        SysUser sysUser = sysUserService.getOne(queryWrapper);
        if (sysUser == null || !StringUtils.equals(sysUser.getPassword(), password)){
            return CommonResult.failed("用户名或密码错误");
        }
        String token = JWTUtil.sign(sysUser.getId().toString(), Constant.ADMIN_LOGIN_TYPE, sysUser.getPassword());
        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(86400L);
        String expireTimeStr = DateUtil.formatFullTime(expireTime);
        JwtToken jwtToken = new JwtToken(token, expireTimeStr);
        //保存token到redis
        redisService.set(Constant.TOKEN_CACHE_PREFIX + jwtToken.getToken(), jwtToken.getToken(), shiroProperties.getJwtTimeOut() * 1000);
        Map<String, Object> userInfo = generateUserInfo(jwtToken, sysUser);
        return CommonResult.success(userInfo);
    }

    private Map<String, Object> generateUserInfo(JwtToken token, SysUser sysUser) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("token", token.getToken());
        userInfo.put("expireTime", token.getExpireAt());
        //查询用户的所有角色
        SysUserDto sysUserDto = sysUserConvert.entity2Dto(sysUser);
        List<SysRole> roleList = sysUserService.getRoleList(sysUserDto.getId());
        List<String> roles = roleList.stream().map(SysRole::getName).distinct().collect(Collectors.toList());
        sysUserDto.setRoles(sysUserService.getRoleList(sysUserDto.getId()));
        userInfo.put("roles", roles);
        userInfo.put("user", sysUserDto);
        return userInfo;
    }


    @ApiOperation("获取用户信息")
    @GetMapping(value = "/info")
    public CommonResult getUserInfo(){
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if(Objects.nonNull(principal)){
            String token = (String) principal;
            String userId = JWTUtil.getUserId(token);
            SysUser user = sysUserService.getById(userId);
            return CommonResult.success(user);
        }else{
            return CommonResult.failed("用户不存在");
        }
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
}
