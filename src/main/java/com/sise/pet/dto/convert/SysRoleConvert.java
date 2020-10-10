package com.sise.pet.dto.convert;

import com.sise.pet.dto.SysRoleDto;
import com.sise.pet.entity.SysRole;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysRoleConvert extends BaseMapper<SysRoleDto,SysRole>{
}
