package com.sise.pet.mapper;

import com.sise.pet.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

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
    List<SysMenu> findByRoles(@Param("roleIds") Set<Long> roleIds, @Param("type") Integer type);
}
