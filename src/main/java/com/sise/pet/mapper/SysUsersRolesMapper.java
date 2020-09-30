package com.sise.pet.mapper;

import com.sise.pet.entity.SysRole;
import com.sise.pet.entity.SysUsersRoles;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户角色关联 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2020-09-07
 */
public interface SysUsersRolesMapper extends BaseMapper<SysUsersRoles> {
    List<SysRole> selectUserRoles(Long id);
}
