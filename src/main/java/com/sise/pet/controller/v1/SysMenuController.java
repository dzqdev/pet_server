package com.sise.pet.controller.v1;


import com.sise.pet.core.CommonResult;
import com.sise.pet.dto.SysMenuDto;
import com.sise.pet.entity.SysMenu;
import com.sise.pet.entity.SysRole;
import com.sise.pet.service.ISysMenuService;
import com.sise.pet.service.ISysRoleService;
import com.sise.pet.service.ISysUserService;
import com.sise.pet.shiro.JWTUtil;
import com.sise.pet.vo.MenuVo;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
public class SysMenuController {

    @Resource
    private ISysMenuService menuService;

    @Resource
    private ISysRoleService roleService;

    @Resource
    private ISysUserService sysUserService;

    @ApiOperation("新增菜单")
    @PostMapping
    public CommonResult create(SysMenu sysMenu){
        boolean b = menuService.save(sysMenu);
        if (b) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("修改菜单")
    @PutMapping
    public CommonResult update(@RequestBody SysMenu sysMenu){
        boolean b = menuService.updateById(sysMenu);
        if (b) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("删除菜单")
    @DeleteMapping
    public CommonResult delete(@RequestBody Set<Long> ids){
        //删除菜单本身以及子菜单
        return CommonResult.success(null);
    }

  /*  @ApiOperation("分页查询后台菜单")
    @RequestMapping(value = "/list/{parentId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<UmsMenu>> list(@PathVariable Long parentId,
                                                  @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<UmsMenu> menuList = menuService.list(parentId, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(menuList));
    }*/

    @ApiOperation("返回全部的菜单")
    @GetMapping(value = "/lazy")
    public CommonResult query(@RequestParam Long pid){
//        return new ResponseEntity<>(menuService.getMenus(pid), HttpStatus.OK);
        return CommonResult.success(null);
    }

    @ApiOperation("根据ID获取菜单详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<SysMenu> getItem(@PathVariable Long id) {
        SysMenu sysMenu = menuService.getById(id);
        return CommonResult.success(sysMenu);
    }

    @ApiOperation("获取前端所需菜单")
    @GetMapping(value = "/build")
    public CommonResult buildMenus(){
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if(Objects.nonNull(principal)){
            String token = (String) principal;
            String userId = JWTUtil.getUserId(token);
            if(null != userId){
                List<SysRole> roles = sysUserService.getRoleList(Long.valueOf(userId));
                List<SysMenuDto> sysMenuDtos = menuService.findByRoles(roles,2);
                List<SysMenuDto> treeMuenuDtos = menuService.buildTree(sysMenuDtos);
                List<MenuVo> menus = menuService.buildMenus(treeMuenuDtos);
                return CommonResult.success(menus);
            }else{
                return CommonResult.failed("用户不存在");
            }
        }else{
            return CommonResult.failed("用户不存在");
        }
    }
}
