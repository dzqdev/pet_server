package com.sise.pet.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.pet.dto.SysUserDto;
import com.sise.pet.dto.UpdateSysUserPasswordParam;
import com.sise.pet.dto.query.SysUserQueryCriteria;
import com.sise.pet.entity.SysRole;
import com.sise.pet.entity.SysUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * 获取用户对应角色
     */
    List<SysRole> getUserRoleList(Long userId);

    /**
     * 修改密码
     */
    int updatePassword(UpdateSysUserPasswordParam updateSysUserPasswordParam);

    /**
     * 用户自助修改资料
     * @param sysUser
     */
    void updateCenter(SysUser sysUser);

    /**
     * 分页查询用户列表
     * @param criteria
     * @param pageable
     * @return
     */
    Page<SysUserDto> queryAll(SysUserQueryCriteria criteria, Page pageable);

    /**
     * 创建用户
     * @param userDto
     */
    void create(SysUserDto userDto);

    /**
     * 根据用户名查询用户信息
     * @param userName
     * @return
     */
    SysUserDto findByName(String userName);

    /**
     * 更新用户信息
     * @param userDto
     */
    void update(SysUserDto userDto);

    /**
     * 修改头像
     * @param file 文件
     * @return /
     */
    Map<String, String> updateAvatar(MultipartFile file);

    /**
     * 删除用户
     * @param ids
     */
    void delete(Set<Long> ids);
}
