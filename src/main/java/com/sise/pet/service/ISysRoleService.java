package com.sise.pet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.dto.SysRoleDto;
import com.sise.pet.dto.SysUserDto;
import com.sise.pet.entity.SysMenu;
import com.sise.pet.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.pet.entity.SysUser;
import org.springframework.security.core.GrantedAuthority;

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
    int updateRoleMenus(Long roleId, List<Long> menuIds);
    /**
     * 获取用户权限信息
     * @param user 用户信息
     * @return 权限信息
     */
    List<GrantedAuthority> mapToGrantedAuthorities(SysUserDto user);

}
