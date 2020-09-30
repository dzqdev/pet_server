package com.sise.pet.vo;

import com.sise.pet.entity.SysUser;
import lombok.Data;

import java.util.List;

@Data
public class SysUserVo extends SysUser {
    private List<String> roles;
}
