package com.sise.pet.vo;

import com.sise.pet.entity.Video;
import lombok.Data;

import java.util.Date;

@Data
public class CollectVideoVo extends Video {
    //收藏事件
    private Date collectTime;
}
