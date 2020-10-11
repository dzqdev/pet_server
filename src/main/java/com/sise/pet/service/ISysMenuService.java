package com.sise.pet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.pet.dto.SysMenuDto;
import com.sise.pet.dto.query.SysMenuQueryCriteria;
import com.sise.pet.entity.SysMenu;
import com.sise.pet.entity.SysRole;
import com.sise.pet.vo.MenuVo;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统菜单 服务类
 * </p>
 *
 * @author author
 * @since 2020-09-07
 */
public interface ISysMenuService extends IService<SysMenu> {

    List<SysMenuDto> getMenus(Long pid);

    List<SysMenuDto> findByRoles(List<SysRole> roles,Integer type);

    List<SysMenuDto> buildTree(List<SysMenuDto> menuDTOs);

    List<MenuVo> buildMenus(List<SysMenuDto> menuDtos);

    List<SysMenuDto> queryAll(SysMenuQueryCriteria queryCriteria);

    void create(SysMenu sysMenu);

    //获取某个菜单以及菜单下的所有菜单id
    Set<SysMenu> getDeleteMenus(List<SysMenu> menuList, Set<SysMenu> menuSet);

    //删除菜单,与角色解绑
    void delete(Set<SysMenu> menuSet);

    void update(SysMenu resources);

    List<SysMenuDto> getSuperior(SysMenuDto menuDto, List<SysMenu> menus);
}
