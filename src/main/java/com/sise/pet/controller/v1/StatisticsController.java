package com.sise.pet.controller.v1;

import com.sise.pet.core.CommonResult;
import com.sise.pet.service.IStatisticsService;
import com.sise.pet.vo.HistogramChartData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName StatisticsController
 * @Description TODO
 * @Date 2020/3/7 16:22
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/v1/statistics")
public class StatisticsController {

    @Autowired
    private IStatisticsService iStatisticsService;

    /** 条形图
     * 获取当前点击前5的宠物狗
     * @return
     */
    @GetMapping("/topic5Pet")
    public CommonResult getTopic5Pet(){
        HistogramChartData chartData = iStatisticsService.getTopic5PetInfo();
        return CommonResult.success(chartData);
    }

    /** 饼图
     * 获取文章分布的类别
     * @return
     */
    @GetMapping("/articleTypeCount")
    public CommonResult getArticleGroupByCategory(){
        HistogramChartData chartData = iStatisticsService.getArticleTypeCount();
        return CommonResult.success(chartData);
    }


    /**
     * 讨论发布数量增长
     * 折线图
     * @return
     */
    @GetMapping("/discussionIncrement")
    public CommonResult getDiscussionIncrement(){
        return CommonResult.success(null);
    }



}
