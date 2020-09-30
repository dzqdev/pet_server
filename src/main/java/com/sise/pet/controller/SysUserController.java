package com.sise.pet.controller;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.PageUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.core.CommonResult;
import com.sise.pet.dto.ArticleDto;
import com.sise.pet.dto.SysUserDto;
import com.sise.pet.dto.UpdateSysUserPasswordParam;
import com.sise.pet.entity.SysUser;
import com.sise.pet.service.ISysRoleService;
import com.sise.pet.service.ISysUserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import org.apache.shiro.SecurityUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @ApiOperation("查询用户")
    @GetMapping
    public CommonResult query(SysUserDto dto, Page page){
        IPage<SysUserDto> list = sysUserService.query(dto,page);
        return CommonResult.success(list);
    }

    @ApiOperation("新增用户")
    @PostMapping
    public CommonResult create(SysUser user){
        // 默认密码 123456
        user.setPassword("123456");
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
