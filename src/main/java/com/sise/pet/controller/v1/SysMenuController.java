package com.sise.pet.controller.v1;


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sise.pet.core.CommonResult;
import com.sise.pet.dto.SysMenuDto;
import com.sise.pet.dto.convert.SysMenuConvert;
import com.sise.pet.dto.query.SysMenuQueryCriteria;
import com.sise.pet.entity.SysMenu;
import com.sise.pet.entity.SysRole;
import com.sise.pet.entity.SysUser;
import com.sise.pet.service.ISysMenuService;
import com.sise.pet.service.ISysRoleService;
import com.sise.pet.service.ISysUserService;
import com.sise.pet.utils.SecurityUtils;
import com.sise.pet.vo.MenuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 系统菜单 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-09-07
 */
@RestController
@RequestMapping("/api/v1/menu")
@Api(tags = "系统：菜单接口")
public class SysMenuController {

    @Resource
    private ISysMenuService menuService;

    @Resource
    private ISysRoleService roleService;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private SysMenuConvert sysMenuConvert;

    @ApiOperation("新增菜单")
    @PostMapping
    public CommonResult create(SysMenu sysMenu){
        menuService.create(sysMenu);
        return CommonResult.success(null);
    }

    @ApiOperation("修改菜单")
    @PutMapping
    public CommonResult update(@RequestBody SysMenu sysMenu){
        menuService.update(sysMenu);
        return CommonResult.success(null);
    }

    @ApiOperation("删除菜单")
    @DeleteMapping
    public CommonResult delete(@RequestBody Set<Long> ids){
        //删除菜单本身以及子菜单
        Set<SysMenu> menuSet = new HashSet<>();
        for (Long id : ids) {
            List<SysMenuDto> menuList = menuService.getMenus(id);
            menuSet.add(menuService.getById(id));
            menuSet = menuService.getDeleteMenus(sysMenuConvert.toEntity(menuList), menuSet);
        }
        menuService.delete(menuSet);
        return CommonResult.success(null);
    }

    @ApiOperation("查询菜单")
    @GetMapping
    public CommonResult query(SysMenuQueryCriteria queryCriteria) {
        List<SysMenuDto> menus = menuService.queryAll(queryCriteria);
        return CommonResult.success(menus);
    }

    @ApiOperation("返回全部的菜单")
    @GetMapping(value = "/lazy")
    public CommonResult query(@RequestParam Long pid){
        List<SysMenuDto> menus = menuService.getMenus(pid);
        return CommonResult.success(menus);
    }


    @ApiOperation("获取前端所需菜单")
    @GetMapping(value = "/build")
    public CommonResult buildMenus(){
        SysUser user = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, SecurityUtils.getCurrentUsername()));
        List<SysRole> roles = sysUserService.getRoleList(Long.valueOf(user.getId()));
        List<SysMenuDto> sysMenuDtos = menuService.findByRoles(roles,2);
        List<SysMenuDto> treeMuenuDtos = menuService.buildTree(sysMenuDtos);
        List<MenuVo> menus = menuService.buildMenus(treeMuenuDtos);
        return CommonResult.success(menus);
    }

    @ApiOperation("查询菜单:根据ID获取同级与上级数据")
    @PostMapping("/superior")
    public CommonResult getSuperior(@RequestBody List<Long> ids) {
        Set<SysMenuDto> menuDtos = new LinkedHashSet<>();
        if(CollectionUtil.isNotEmpty(ids)){
            for (Long id : ids) {
                SysMenu menu = menuService.getById(id);
                SysMenuDto menuDto = sysMenuConvert.toDto(menu);
                menuDtos.addAll(menuService.getSuperior(menuDto, new ArrayList<>()));
            }
            return CommonResult.success(menuService.buildTree(new ArrayList<>(menuDtos)));
        }
        return CommonResult.success(menuService.getMenus(null));
    }
}
