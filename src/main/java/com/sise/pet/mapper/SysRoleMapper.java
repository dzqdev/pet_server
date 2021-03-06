package com.sise.pet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sise.pet.entity.SysMenu;
import com.sise.pet.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2020-09-07
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {
    public List<SysMenu> getMenuList(Long roleId);

    void untiedMenu(@Param("id") Long id);
}
