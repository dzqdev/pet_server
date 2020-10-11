package com.sise.pet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Sets;
import com.sise.pet.dto.SysRoleDto;
import com.sise.pet.dto.SysUserDto;
import com.sise.pet.dto.convert.SysRoleConvert;
import com.sise.pet.entity.SysMenu;
import com.sise.pet.entity.SysRole;
import com.sise.pet.mapper.SysRoleMapper;
import com.sise.pet.mapper.SysUserMapper;
import com.sise.pet.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-09-07
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysRoleConvert roleConvert;


    @Override
    public IPage<SysRole> selectPage(SysRoleDto sysRoleDto, Page page) {
        QueryWrapper<SysRoleDto> queryWrapper = new QueryWrapper<>();
        return null;
    }

    @Override
    public List<SysMenu> getMenuList(Long roleId) {
        List<SysMenu> menuList = sysRoleMapper.getMenuList(roleId);
        return menuList;
    }

    @Override
    public int updateRoleMenus(Long roleId, List<Long> menuIds) {
        return 0;
    }

    @Override
    public List<GrantedAuthority> mapToGrantedAuthorities(SysUserDto user) {
        Set<String> permissions = new HashSet<>();
        // 如果是管理员直接返回
        if(user.getIsAdmin()){
            permissions.add("admin");
            return permissions.stream().map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }
        List<SysRole> roles = sysUserMapper.getRoleList(user.getId());
        List<SysRoleDto> roleDtos = new ArrayList<>();
        for (SysRole role : roles) {
            SysRoleDto roleDto = roleConvert.toDto(role);
            List<SysMenu> menuList = sysRoleMapper.getMenuList(roleDto.getId());
            roleDto.setMenus(Sets.newHashSet(menuList));
            roleDtos.add(roleDto);
        }
        permissions = roleDtos.stream().flatMap(role -> role.getMenus().stream())
                .filter(menu -> StringUtils.isNotBlank(menu.getPermission()))
                .map(SysMenu::getPermission).collect(Collectors.toSet());
        return permissions.stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public void untiedMenu(Long id) {
        sysRoleMapper.untiedMenu(id);
    }

}
