package com.sise.pet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.dto.SysRoleDto;
import com.sise.pet.entity.SysMenu;
import com.sise.pet.entity.SysRole;
import com.sise.pet.mapper.SysRoleMapper;
import com.sise.pet.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    public int updateMenu(Long roleId, List<Long> menuIds) {
        return 0;
    }

    @Override
    public List<SysRole> findByUsersId(Long id) {
        List<SysRole> list = sysRoleMapper.findByUsersId(id);
        return list;
    }
}
