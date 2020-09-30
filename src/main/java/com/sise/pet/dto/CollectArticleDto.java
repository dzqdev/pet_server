package com.sise.pet.dto;

import com.sise.pet.entity.Article;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName CollectVo
 * @Description TODO
 * @Date 2020/3/8 16:18
 * @Version 1.0
 **/
@Data
public class CollectArticleDto extends Article {
    //收藏时间
    private Date collectTime;
}
