package com.sise.pet.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.pet.dto.SysUserDto;
import com.sise.pet.dto.UpdateSysUserPasswordParam;
import com.sise.pet.dto.convert.PageConvert;
import com.sise.pet.dto.convert.SysUserConvert;
import com.sise.pet.dto.format.BooleanFormatter;
import com.sise.pet.dto.query.SysUserQueryCriteria;
import com.sise.pet.entity.SysRole;
import com.sise.pet.entity.SysUser;
import com.sise.pet.entity.SysUsersRoles;
import com.sise.pet.mapper.SysUserMapper;
import com.sise.pet.service.ISysRoleService;
import com.sise.pet.service.ISysUserService;
import com.sise.pet.service.ISysUsersRolesService;
import com.sise.pet.utils.FileProperties;
import com.sise.pet.utils.FileUtil;
import com.sise.pet.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    @Resource
    private ISysUsersRolesService usersRolesService;
    @Resource
    private FileProperties properties;

    @Override
    public List<SysRole> getUserRoleList(Long userId) {
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
    public void updateCenter(SysUser sysUser) {
        SysUser user = getById(sysUser.getId());
        user.setUsername(sysUser.getUsername());
        user.setPhone(sysUser.getPhone());
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
            record.setRoles(getUserRoleList(record.getId()));
        });
        Page<SysUserDto> dtoPage = pageConvert.toPageDto(page, sysUserDtos);
        return dtoPage;
    }

    @Override
    public void create(SysUserDto userDto) {
        List<SysRole> roles = userDto.getRoles();
        SysUser sysUser = sysUserConvert.toEntity(userDto);
        save(sysUser);
        if (CollectionUtil.isNotEmpty(roles)) {
            roles.forEach(role -> {
                SysUsersRoles usersRoles = new SysUsersRoles();
                usersRoles.setRoleId(role.getId());
                usersRoles.setUserId(sysUser.getId());
                usersRolesService.save(usersRoles);
            });
        }
    }

    @Override
    public SysUserDto findByName(String userName) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(SysUser::getUsername, userName);
        SysUser sysUser = getOne(queryWrapper);
        List<SysRole> roleList = getUserRoleList(sysUser.getId());
        SysUserDto sysUserDto = sysUserConvert.toDto(sysUser);
        sysUserDto.setRoles(roleList);
        return sysUserDto;
    }

    @Override
    public void update(SysUserDto userDto) {
        SysUser user = sysUserConvert.toEntity(userDto);
        List<SysRole> roles = userDto.getRoles();
        updateById(user);
        //删除旧关系
        LambdaUpdateWrapper<SysUsersRoles> deleteOldRelationWrapper = new LambdaUpdateWrapper();
        deleteOldRelationWrapper.eq(SysUsersRoles::getUserId, user.getId());
        usersRolesService.remove(deleteOldRelationWrapper);
        //重新建立关系
        if (CollectionUtil.isNotEmpty(roles)) {
            roles.forEach(role -> {
                SysUsersRoles usersRoles = new SysUsersRoles();
                usersRoles.setRoleId(role.getId());
                usersRoles.setUserId(user.getId());
                usersRolesService.save(usersRoles);
            });
        }
    }

    @Override
    public Map<String, String> updateAvatar(MultipartFile file) {
        SysUserDto userDto = findByName(SecurityUtils.getCurrentUsername());
        SysUser user = sysUserConvert.toEntity(userDto);
        File upload = FileUtil.upload(file, properties.getPath().getAvatar());
        user.setAvatar(upload.getPath());
        return new HashMap<String, String>(1) {{
            put("avatar", file.getName());
        }};
    }

    @Override
    public void delete(Set<Long> ids) {
        //先解绑用户-角色关系，再删除用户]
        if(CollectionUtil.isNotEmpty(ids)){
            ids.forEach(id -> {
                LambdaUpdateWrapper<SysUsersRoles> deleteOldRelationWrapper = new LambdaUpdateWrapper();
                deleteOldRelationWrapper.eq(SysUsersRoles::getUserId, id);
                usersRolesService.remove(deleteOldRelationWrapper);
                removeById(id);
            });
        }
    }

}
