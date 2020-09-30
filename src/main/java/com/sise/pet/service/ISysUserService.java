package com.sise.pet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.dto.SysUserDto;
import com.sise.pet.dto.UpdateSysUserPasswordParam;
import com.sise.pet.entity.SysRole;
import com.sise.pet.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author author
 * @since 2020-09-07
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 修改用户角色关系
     */
    @Transactional
    int updateRole(Long userId, List<Long> roleIds);

    /**
     * 获取用户对应角色
     */
    List<SysRole> getRoleList(Long userId);

    /**
     * 修改密码
     */
    int updatePassword(UpdateSysUserPasswordParam updateSysUserPasswordParam);

    /**
     * 用户自助修改资料
     * @param resources
     */
    void updateCenter(SysUser resources);

    IPage<SysUserDto> query(SysUserDto dto, Page page);
}
