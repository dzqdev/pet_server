package com.sise.pet.dto;

import com.sise.pet.entity.Video;
import lombok.Data;

import java.util.Date;

@Data
public class CollectVideoDto extends Video {
    //收藏时间
    private Date collectTime;
}
