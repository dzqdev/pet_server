package com.sise.pet.mapper;

import com.sise.pet.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统菜单 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2020-09-07
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    List<SysMenu> findByRoles(Set<Long> roleIds);
}
