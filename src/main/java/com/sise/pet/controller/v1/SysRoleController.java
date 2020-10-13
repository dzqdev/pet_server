package com.sise.pet.controller.v1;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.core.CommonResult;
import com.sise.pet.dto.SysRoleDto;
import com.sise.pet.dto.query.SysRoleQueryCriteria;
import com.sise.pet.entity.SysRole;
import com.sise.pet.service.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-09-07
 */
@RestController
@Api(tags = "系统：角色管理")
@RequestMapping("/api/v1/role")
public class SysRoleController {

    @Resource
    private ISysRoleService roleService;

    @ApiOperation("获取单个role")
    @GetMapping(value = "/{id}")
    public CommonResult query(@PathVariable Long id){
        return CommonResult.success(roleService.getById(id));
    }

    @ApiOperation("返回全部的角色")
    @GetMapping(value = "/all")
    public CommonResult query(){
        return CommonResult.success(roleService.queryAll());
    }

    @ApiOperation("查询角色")
    @GetMapping
    public CommonResult query(SysRoleQueryCriteria criteria, Page pageable){
        Page<SysRoleDto> page = roleService.queryAll(criteria, pageable);
        return CommonResult.success(page);
    }

    @ApiOperation("新增角色")
    @PostMapping
    public CommonResult create(@Validated @RequestBody SysRole resource){
        roleService.create(resource);
        return CommonResult.success(null);
    }

    @ApiOperation("修改角色")
    @PutMapping
    public CommonResult update( @RequestBody SysRole resources){
        roleService.updateById(resources);
        return CommonResult.success(null);
    }

    @ApiOperation("修改角色菜单")
    @PutMapping(value = "/menu")
    public CommonResult updateMenu(@RequestBody SysRoleDto resources){
        roleService.updateMenu(resources);
        return CommonResult.success(null);
    }

    @ApiOperation("删除角色")
    @DeleteMapping
    public CommonResult delete(@RequestBody Set<Long> ids){
        // 验证是否被用户关联
        roleService.verification(ids);
        //删除
        roleService.removeByIds(ids);
        return CommonResult.success(null);
    }


}
