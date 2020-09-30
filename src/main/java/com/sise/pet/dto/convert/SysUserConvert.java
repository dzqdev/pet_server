package com.sise.pet.dto.convert;

import com.sise.pet.dto.SysUserDto;
import com.sise.pet.entity.SysUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface SysUserConvert {
    @Mappings({
            @Mapping(source = "id",target = "id"),
            @Mapping(source = "username",target = "username"),
            @Mapping(source = "phone",target = "phone")
    })
    SysUserDto entity2Dto(SysUser user);
}
