package com.sise.pet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.pet.mapper.StatisticsMapper;
import com.sise.pet.service.IStatisticsService;
import com.sise.pet.vo.HistogramChartData;
import com.sise.pet.vo.StatisticsBaseData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName StatisticsServiceImpl
 * @Description TODO
 * @Date 2020/3/7 17:02
 * @Version 1.0
 **/
@Service
public class StatisticsServiceImpl extends ServiceImpl<StatisticsMapper, StatisticsBaseData> implements IStatisticsService {

    @Resource
    private StatisticsMapper statisticsMapper;

    @Override
    public HistogramChartData getTopic5PetInfo() {
        List<StatisticsBaseData> topic5PetInfo = statisticsMapper.getTopic5PetInfo();
        HistogramChartData chartData = HistogramChartData.build(topic5PetInfo);
        return chartData;
    }

    @Override
    public HistogramChartData getArticleTypeCount() {
        List<StatisticsBaseData> articleTypeCount = statisticsMapper.getArticleTypeCount();
        HistogramChartData chartData = HistogramChartData.build(articleTypeCount);
        return chartData;
    }
}
