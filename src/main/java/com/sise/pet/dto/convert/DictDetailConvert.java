package com.sise.pet.dto.convert;

import com.sise.pet.dto.DictDetailDto;
import com.sise.pet.entity.DictDetail;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DictDetailConvert extends BaseMapper<DictDetailDto, DictDetail> {
}
