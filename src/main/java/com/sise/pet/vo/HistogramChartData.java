package com.sise.pet.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName HistogramChartData
 * @Description TODO
 * @Date 2020/3/7 16:55
 * @Version 1.0
 **/
@Data
public class HistogramChartData {
    private List<String> columns;
    private List<Map<String, String>> rows;

    public HistogramChartData(List<String> columns, List<Map<String, String>> rows) {
        this.columns = columns;
        this.rows = rows;
    }

    public static HistogramChartData build(List<StatisticsBaseData> list){
        List<String> columns = new ArrayList<>();
        columns.add("狗狗类别");
        columns.add("点击量");
        List<Map<String, String>> rows = new ArrayList<>();
        for (StatisticsBaseData data : list) {
            Map row = new HashMap();
            row.put(columns.get(0), data.getName());
            row.put(columns.get(1), data.getValue());
            rows.add(row);
        }
        return new HistogramChartData(columns,rows);
    }
}
