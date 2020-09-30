package com.sise.pet.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.dto.SysUserDto;
import com.sise.pet.dto.UpdateSysUserPasswordParam;
import com.sise.pet.entity.SysRole;
import com.sise.pet.entity.SysUser;
import com.sise.pet.mapper.SysUserMapper;
import com.sise.pet.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-09-07
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public int updateRole(Long userId, List<Long> roleIds) {
        return 0;
    }

    @Override
    public List<SysRole> getRoleList(Long userId) {
        List<SysRole> roleList = sysUserMapper.getRoleList(userId);
        return roleList;
    }

    @Override
    public int updatePassword(UpdateSysUserPasswordParam updateSysUserPasswordParam) {
        SysUser sysUser = getById(updateSysUserPasswordParam.getUserId());
        sysUser.setPassword(updateSysUserPasswordParam.getNewPassword());
        updateById(sysUser);
        return 0;
    }

    @Override
    public void updateCenter(SysUser resources) {
        SysUser user = this.getById(resources.getId());
        user.setUsername(resources.getUsername());
        user.setPhone(resources.getPhone());
        this.updateById(user);
    }

    @Override
    public IPage<SysUserDto> query(SysUserDto dto, Page page) {
        return sysUserMapper.query(dto,page);
    }
}
