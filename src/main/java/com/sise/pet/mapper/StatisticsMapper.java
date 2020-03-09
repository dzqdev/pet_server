package com.sise.pet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sise.pet.vo.StatisticsBaseData;

import java.util.List;

public interface StatisticsMapper extends BaseMapper<StatisticsBaseData> {
    List<StatisticsBaseData> getTopic5PetInfo();
    List<StatisticsBaseData> getArticleTypeCount();
}
