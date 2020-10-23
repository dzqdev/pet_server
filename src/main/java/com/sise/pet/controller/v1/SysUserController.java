package com.sise.pet.controller.v1;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.annotation.Log;
import com.sise.pet.core.CommonResult;
import com.sise.pet.dto.SysUserDto;
import com.sise.pet.dto.UpdateSysUserPasswordParam;
import com.sise.pet.dto.convert.SysUserConvert;
import com.sise.pet.dto.query.SysUserQueryCriteria;
import com.sise.pet.entity.SysUser;
import com.sise.pet.service.ISysRoleService;
import com.sise.pet.service.ISysUserService;
import com.sise.pet.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Set;

/**
 * <p>
 * 系统用户 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-09-07
 */
@Api(tags = "系统：用户接口")
@RestController
@RequestMapping("/api/v1/sys-user")
public class SysUserController {

    @Resource
    private ISysUserService sysUserService;
    @Resource
    private PasswordEncoder passwordEncoder;


    @ApiOperation("查询用户")
    @GetMapping
    public CommonResult query(SysUserQueryCriteria criteria, Page page){
        return CommonResult.success(sysUserService.queryAll(criteria,page));
    }

    @Log("新增用户")
    @ApiOperation("新增用户")
    @PostMapping
    public CommonResult create(@RequestBody SysUserDto userDto){
        if(StrUtil.isEmpty(userDto.getUsername())){
            return CommonResult.failed("用户名称不能为空");
        }
        // 默认密码 123456
        userDto.setPassword(passwordEncoder.encode("123456"));
        sysUserService.create(userDto);
        return CommonResult.success(null);
    }

    @Log("修改用户")
    @ApiOperation("修改用户")
    @PutMapping
    public CommonResult update(SysUser user){
        sysUserService.updateById(user);
        return CommonResult.success(null);
    }

    @ApiOperation("修改用户：个人中心")
    @PutMapping(value = "center")
    public CommonResult center(SysUser user){
        if(!user.getId().equals(SecurityUtils.getCurrentUserId())){
            return CommonResult.failed("不能修改他人资料");
        }
        sysUserService.updateCenter(user);
        return CommonResult.success(null);
    }

    @Log("删除用户")
    @ApiOperation("删除用户")
    @DeleteMapping
    public CommonResult delete(@RequestBody Set<Long> ids){
        sysUserService.delete(ids);
        return CommonResult.success(null);
    }

    @Log("修改密码")
    @ApiOperation("修改密码")
    @PostMapping(value = "/updatePass")
    public CommonResult updatePass(@RequestBody UpdateSysUserPasswordParam passVo){
        SysUserDto userDto = sysUserService.findByName(SecurityUtils.getCurrentUsername());
        if(!passVo.getOldPassword().equals(userDto.getPassword())){
            return CommonResult.failed("修改失败，旧密码错误");
        }
        if(passVo.getNewPassword().equals(userDto.getPassword())){
            return CommonResult.failed("新密码不能与旧密码相同");
        }
        sysUserService.updatePassword(passVo);
        return CommonResult.success(null);
    }

    @Log("修改头像")
    @ApiOperation("修改头像")
    @PostMapping(value = "/updateAvatar")
    public CommonResult updateAvatar(@RequestParam MultipartFile file){
        return CommonResult.success(sysUserService.updateAvatar(file));
    }

}
