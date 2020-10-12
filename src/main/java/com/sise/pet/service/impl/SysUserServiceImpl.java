package com.sise.pet.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Sets;
import com.sise.pet.dto.SysRoleDto;
import com.sise.pet.dto.SysUserDto;
import com.sise.pet.dto.UpdateSysUserPasswordParam;
import com.sise.pet.dto.convert.PageConvert;
import com.sise.pet.dto.convert.SysUserConvert;
import com.sise.pet.dto.format.BooleanFormatter;
import com.sise.pet.dto.query.SysUserQueryCriteria;
import com.sise.pet.entity.SysRole;
import com.sise.pet.entity.SysUser;
import com.sise.pet.mapper.SysUserMapper;
import com.sise.pet.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

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
    @Resource
    BooleanFormatter booleanFormatter;
    @Resource
    private PageConvert<SysUserDto, SysUser> pageConvert;
    @Resource
    private SysUserConvert sysUserConvert;


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
    public Page<SysUserDto> queryAll(SysUserQueryCriteria criteria, Page pageable) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        Long id = criteria.getId();
        Long enabled = criteria.getEnabled() == null ? null : booleanFormatter.toLong(criteria.getEnabled());
        String blurry = criteria.getBlurry();
        List<Timestamp> createTime = criteria.getCreateTime();
        if (null != id) {
            wrapper.eq(SysUser::getId, id);
        }
        if (null != enabled) {
            wrapper.eq(SysUser::getEnabled, enabled);
        }
        if (StrUtil.isNotBlank(blurry)) {
            wrapper.like(SysUser::getUsername, blurry)
                    .or().like(SysUser::getPhone, blurry);
        }
        if (CollectionUtil.isNotEmpty(createTime)) {
            wrapper.ge(SysUser::getCreateTime, createTime.get(0))
                    .le(SysUser::getCreateTime, createTime.get(1));
        }

        Page<SysUser> page = sysUserMapper.selectPage(pageable, wrapper);
        List<SysUserDto> sysUserDtos = sysUserConvert.toDto(page.getRecords());
        sysUserDtos.stream().forEach(record -> {
            record.setRoles(getRoleList(record.getId()));
        });
        Page<SysUserDto> dtoPage = pageConvert.toPageDto(page, sysUserDtos);
        return dtoPage;
    }

}
