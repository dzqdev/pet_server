package com.sise.pet.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Sets;
import com.sise.pet.dto.SysMenuDto;
import com.sise.pet.dto.SysRoleDto;
import com.sise.pet.dto.SysUserDto;
import com.sise.pet.dto.convert.PageConvert;
import com.sise.pet.dto.convert.SysMenuConvert;
import com.sise.pet.dto.convert.SysRoleConvert;
import com.sise.pet.dto.query.SysRoleQueryCriteria;
import com.sise.pet.entity.SysMenu;
import com.sise.pet.entity.SysRole;
import com.sise.pet.entity.SysRolesMenus;
import com.sise.pet.entity.SysUsersRoles;
import com.sise.pet.exception.Asserts;
import com.sise.pet.exception.EntityExistException;
import com.sise.pet.mapper.SysRoleMapper;
import com.sise.pet.mapper.SysUserMapper;
import com.sise.pet.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.pet.service.ISysRolesMenusService;
import com.sise.pet.service.ISysUsersRolesService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
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

    @Resource
    private SysMenuConvert menuConvert;

    @Resource
    private PageConvert<SysRoleDto,SysRole> pageConvert;

    @Resource
    private ISysRolesMenusService rolesMenusService;

    @Resource
    private ISysUsersRolesService usersRolesService;


    @Override
    public List<SysMenu> getMenuList(Long roleId) {
        List<SysMenu> menuList = sysRoleMapper.getMenuList(roleId);
        return menuList;
    }

    @Override
    public List<GrantedAuthority> mapToGrantedAuthorities(SysUserDto user) {
        Set<String> permissions = new HashSet<>();
        // 如果是管理员直接返回
        if (user.getIsAdmin()) {
            permissions.add("admin");
            return permissions.stream().map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }
        List<SysRole> roles = sysUserMapper.getRoleList(user.getId());
        List<SysRoleDto> roleDtos = new ArrayList<>();
        for (SysRole role : roles) {
            SysRoleDto roleDto = roleConvert.toDto(role);
            List<SysMenu> menuList = sysRoleMapper.getMenuList(roleDto.getId());
            roleDto.setMenus(Sets.newHashSet(menuConvert.toDto(menuList)));
            roleDtos.add(roleDto);
        }
        permissions = roleDtos.stream().flatMap(role -> role.getMenus().stream())
                .filter(menu -> StringUtils.isNotBlank(menu.getPermission()))
                .map(SysMenuDto::getPermission).collect(Collectors.toSet());
        return permissions.stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public void bindRoleMenus(Long roleId, List<Long> menuIds) {
        menuIds.stream().forEach(menuId->{
            SysRolesMenus rolesMenus = new SysRolesMenus(menuId, roleId);
            rolesMenusService.save(rolesMenus);
        });
    }

    @Override
    public void untiedMenu(Long id) {
        sysRoleMapper.untiedMenu(id);
    }

    @Override
    public Page<SysRoleDto> queryAll(SysRoleQueryCriteria criteria, Page pageable) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        String blurry = criteria.getBlurry();
        List<Timestamp> createTime = criteria.getCreateTime();
        if (StrUtil.isNotBlank(blurry)) {
            wrapper.like(SysRole::getName, blurry)
                    .or().like(SysRole::getDescription, blurry);
        }

        if (CollectionUtil.isNotEmpty(createTime)) {
            wrapper.ge(SysRole::getCreateTime, createTime.get(0))
                    .le(SysRole::getCreateTime, createTime.get(1));
        }
        Page<SysRole> page = sysRoleMapper.selectPage(pageable, wrapper);
        List<SysRoleDto> roleDtos = roleConvert.toDto(page.getRecords());
        roleDtos.stream().forEach(record->{
            record.setMenus(Sets.newHashSet(menuConvert.toDto(getMenuList(record.getId()))));
        });
        Page<SysRoleDto> dtoPage = pageConvert.toPageDto(page, roleDtos);
        return dtoPage;
    }

    @Override
    public void create(SysRole resource) {
        if (CollectionUtil.isNotEmpty(list(new LambdaQueryWrapper<SysRole>().eq(SysRole::getName, resource.getName())))) {
            throw new EntityExistException(SysRole.class, "username", resource.getName());
        }
        save(resource);
    }

    @Override
    public void updateMenu(SysRoleDto resources) {
        Long roleId = resources.getId();
        Set<SysMenuDto> menus = resources.getMenus();
        //解绑
        untiedMenu(roleId);
        //重新添加
        bindRoleMenus(roleId,menus.stream().map(SysMenuDto::getId).collect(Collectors.toList()));
    }

    @Override
    public void verification(Set<Long> ids) {
        LambdaQueryWrapper<SysUsersRoles> queryWrapper = new LambdaQueryWrapper<SysUsersRoles>().in(SysUsersRoles::getRoleId);
        int count = usersRolesService.count(queryWrapper);
        if(count > 0){
            Asserts.fail("所选角色存在用户关联，请解除关联再试！");
        }
    }

}
