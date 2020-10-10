/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.sise.pet.security.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sise.pet.core.ResultCode;
import com.sise.pet.dto.SysUserDto;
import com.sise.pet.dto.convert.SysUserConvert;
import com.sise.pet.entity.SysUser;
import com.sise.pet.exception.Asserts;
import com.sise.pet.security.dto.JwtUserDto;
import com.sise.pet.service.ISysRoleService;
import com.sise.pet.service.ISysUserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("userDetailsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private ISysUserService userService;
    @Resource
    private ISysRoleService roleService;

    @Resource
    private SysUserConvert sysUserConvert;

    @Override
    public JwtUserDto loadUserByUsername(String username) {
        SysUserDto sysUserDto = null;
        try {
            SysUser sysUser = userService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
            sysUserDto = sysUserConvert.toDto(sysUser);
        } catch (UsernameNotFoundException e) {
            Asserts.fail(ResultCode.UN_AUTHORIZED);
        }
        if (sysUserDto == null) {
            throw new UsernameNotFoundException("");
        } else {
            if (!sysUserDto.getEnabled()) {
                Asserts.fail("账号未激活");
            }
            return new JwtUserDto(
                    sysUserDto,
                    roleService.mapToGrantedAuthorities(sysUserDto)
            );
        }
    }
}
