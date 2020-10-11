package com.sise.pet.dto.convert;

import com.sise.pet.dto.SysMenuDto;
import com.sise.pet.entity.SysMenu;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysMenuConvert extends BaseMapper<SysMenuDto, SysMenu> {
}
