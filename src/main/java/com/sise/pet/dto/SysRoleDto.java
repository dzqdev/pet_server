package com.sise.pet.dto;

import com.sise.pet.entity.SysMenu;
import com.sise.pet.entity.SysRole;
import lombok.Data;

import java.util.Set;

@Data
public class SysRoleDto extends SysRole {
    //角色下的菜单
    private Set<SysMenu> menus;
}
