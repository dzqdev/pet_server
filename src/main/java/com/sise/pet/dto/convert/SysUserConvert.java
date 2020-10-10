package com.sise.pet.dto.convert;

import com.sise.pet.dto.SysUserDto;
import com.sise.pet.dto.format.BooleanFormatter;
import com.sise.pet.entity.SysUser;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {BooleanFormatter.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysUserConvert extends BaseMapper<SysUserDto, SysUser> {

}
