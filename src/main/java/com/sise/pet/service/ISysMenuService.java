package com.sise.pet.service;

import com.sise.pet.dto.SysMenuDto;
import com.sise.pet.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.pet.entity.SysRole;
import com.sise.pet.vo.MenuVo;

import java.util.List;

/**
 * <p>
 * 系统菜单 服务类
 * </p>
 *
 * @author author
 * @since 2020-09-07
 */
public interface ISysMenuService extends IService<SysMenu> {

    List<SysMenu> getMenus(Long pid);

    List<SysMenuDto> findByRoles(List<SysRole> roles);

    List<SysMenuDto> buildTree(List<SysMenuDto> menuDTOs);

    public List<MenuVo> buildMenus(List<SysMenuDto> menuDTOs);
}
