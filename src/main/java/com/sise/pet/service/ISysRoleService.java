package com.sise.pet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.dto.SysRoleDto;
import com.sise.pet.entity.SysMenu;
import com.sise.pet.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.pet.entity.SysUser;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author author
 * @since 2020-09-07
 */
public interface ISysRoleService extends IService<SysRole> {
    public IPage<SysRole> selectPage(SysRoleDto sysRoleDto, Page page);

    List<SysMenu> getMenuList(Long roleId);

    int updateMenu(Long roleId, List<Long> menuIds);

    List<SysRole> findByUsersId(Long id);
}
