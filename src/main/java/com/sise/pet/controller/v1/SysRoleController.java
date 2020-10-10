package com.sise.pet.controller.v1;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.core.CommonResult;
import com.sise.pet.dto.SysRoleDto;
import com.sise.pet.entity.SysMenu;
import com.sise.pet.entity.SysRole;
import com.sise.pet.service.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    @ApiOperation("获取单个角色")
    @GetMapping(value = "/{id}")
    public CommonResult query(@PathVariable Long id){
        SysRole role = roleService.getById(id);
        return CommonResult.success(role);
    }

    @ApiOperation("返回全部的角色")
    @GetMapping(value = "/all")
    public CommonResult query(SysRoleDto condition,Page page){
        List<SysRole> list = roleService.list();
        return CommonResult.success(list);
    }


    @ApiOperation("新增角色")
    @PostMapping
    public CommonResult addArticle(SysRole sysRole){
        roleService.save(sysRole);
        return CommonResult.success(null);
    }

    @ApiOperation("修改角色")
    @PutMapping(value = "/{id}")
    @ResponseBody
    public CommonResult update(SysRole role) {
        boolean b = roleService.updateById(role);
        if (b) {
            return CommonResult.success(b);
        }
        return CommonResult.failed();
    }

    @ApiOperation("批量删除角色")
    @DeleteMapping(value = "/delete")
    @ResponseBody
    public CommonResult delete(@RequestParam("ids") List<Long> ids) {
        boolean b = roleService.removeByIds(ids);
        if (b) {
            return CommonResult.success(b);
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取相应角色权限")
    @GetMapping(value = "/{roleId}/menu")
    @ResponseBody
    public CommonResult<List<SysMenu>> getMenuList(@PathVariable Long roleId) {
        List<SysMenu> menuList = roleService.getMenuList(roleId);
        return CommonResult.success(menuList);
    }

    @ApiOperation("修改角色权限")
    @PutMapping(value = "/{roleId}/menu")
    @ResponseBody
    public CommonResult updatePermission(@PathVariable Long roleId,
                                         @RequestParam("menuIds") List<Long> menuIds) {
        int count = roleService.updateRoleMenus(roleId, menuIds);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }


}
