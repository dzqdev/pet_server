package com.sise.pet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.pet.vo.HistogramChartData;
import com.sise.pet.vo.StatisticsBaseData;

public interface IStatisticsService extends IService<StatisticsBaseData> {
    HistogramChartData getTopic5PetInfo();
    HistogramChartData getArticleTypeCount();
}
