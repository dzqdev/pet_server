package com.sise.pet.controller.v1;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.core.CommonResult;
import com.sise.pet.dto.UpdateSysUserPasswordParam;
import com.sise.pet.dto.query.SysUserQueryCriteria;
import com.sise.pet.entity.SysUser;
import com.sise.pet.service.ISysRoleService;
import com.sise.pet.service.ISysUserService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
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
@RestController
@RequestMapping("/api/v1/sys-user")
public class SysUserController {

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private ISysRoleService roleService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @ApiOperation("查询用户")
    @GetMapping
    public CommonResult query(SysUserQueryCriteria criteria, Page page){
        return CommonResult.success(sysUserService.queryAll(criteria,page));
    }

    @ApiOperation("新增用户")
    @PostMapping
    public CommonResult create(SysUser user){
        // 默认密码 123456
        user.setPassword(passwordEncoder.encode("123456"));
        sysUserService.save(user);
        return CommonResult.success(null);
    }

    @ApiOperation("修改用户")
    @PutMapping
    public CommonResult update(SysUser user){
        sysUserService.updateById(user);
        return CommonResult.success(null);
    }

    @ApiOperation("修改用户：个人中心")
    @PutMapping(value = "center")
    public CommonResult center(SysUser resources){
        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if(!resources.getId().equals(sysUser.getId())){
            return CommonResult.failed("不能修改他人资料");
        }
        sysUserService.updateCenter(resources);
        return CommonResult.success(null);
    }

    @ApiOperation("删除用户")
    @DeleteMapping
    public CommonResult delete(@RequestBody Set<Long> ids){
        for (Long id : ids) {

        }
        sysUserService.removeByIds(ids);
        return CommonResult.success(null);
    }

    @ApiOperation("修改密码")
    @PostMapping(value = "/updatePass")
    public CommonResult updatePass(@RequestBody UpdateSysUserPasswordParam passVo){
        SysUser currentUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        SysUser user = sysUserService.getById(currentUser.getId());
        if(!passVo.getOldPassword().equals(user.getPassword())){
            return CommonResult.failed("修改失败，旧密码错误");
        }
        if(passVo.getNewPassword().equals(user.getPassword())){
            return CommonResult.failed("新密码不能与旧密码相同");
        }
        sysUserService.updatePassword(passVo);
        return CommonResult.success(null);
    }

    @ApiOperation("修改头像")
    @PostMapping(value = "/updateAvatar")
    public CommonResult updateAvatar(@RequestParam MultipartFile file){
        return CommonResult.success(null);
    }

}
