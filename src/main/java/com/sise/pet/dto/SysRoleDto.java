package com.sise.pet.dto;

import com.sise.pet.entity.SysMenu;
import com.sise.pet.entity.SysRole;
import lombok.Data;

import java.util.Objects;
import java.util.Set;

@Data
public class SysRoleDto {

    private Long id;

    private Set<SysMenuDto> menus;


    private String name;

    private String dataScope;

    private Integer level;

    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysRoleDto roleDto = (SysRoleDto) o;
        return Objects.equals(id, roleDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
