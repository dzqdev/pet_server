package com.sise.pet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.pet.dto.SysRoleDto;
import com.sise.pet.dto.SysUserDto;
import com.sise.pet.dto.query.SysRoleQueryCriteria;
import com.sise.pet.entity.SysMenu;
import com.sise.pet.entity.SysRole;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author author
 * @since 2020-09-07
 */
public interface ISysRoleService extends IService<SysRole> {

    List<SysMenu> getMenuList(Long roleId);
    /**
     * 获取用户权限信息
     * @param user 用户信息
     * @return 权限信息
     */
    List<GrantedAuthority> mapToGrantedAuthorities(SysUserDto user);

    /**
     * 为绑定角色菜单
     * @param roleId
     * @param menuIds
     */
    void bindRoleMenus(Long roleId, List<Long> menuIds);

    /**
     * 解绑某一个角色的菜单
     * @param roleId
     */
    void untiedMenu(Long roleId);

    /**
     * 分页查询角色列表
     * @param criteria
     * @param pageable
     * @return
     */
    Page<SysRoleDto> queryAll(SysRoleQueryCriteria criteria, Page pageable);

    /**
     * 新增角色
     * @param resource
     */
    void create(SysRole resource);

    /**
     * 更新角色的菜单
     * @param resources
     */
    void updateMenu(SysRoleDto resources);

    /**
     * 角色与用户是否存在关联
     * @param ids
     */
    void verification(Set<Long> ids);
}
