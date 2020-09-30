package com.sise.pet.dto;

import com.sise.pet.entity.SysRole;
import com.sise.pet.entity.SysUser;
import lombok.Data;

import java.util.List;

@Data
public class SysUserDto extends SysUser {
    private List<SysRole> roles;
}
