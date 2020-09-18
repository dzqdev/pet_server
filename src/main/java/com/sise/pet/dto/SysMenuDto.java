package com.sise.pet.dto;

import com.sise.pet.entity.SysMenu;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class SysMenuDto extends SysMenu {
    private List<SysMenuDto> children;
    private String componentName;
    private Boolean leaf = true;
    private Boolean hasChildren = false;
}
