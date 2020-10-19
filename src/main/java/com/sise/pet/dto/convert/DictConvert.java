package com.sise.pet.dto.convert;

import com.sise.pet.dto.DictDto;
import com.sise.pet.entity.Dict;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DictConvert extends BaseMapper<DictDto,Dict> {
}
