package com.sise.pet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.entity.Video;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 视频 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2020-03-14
 */
public interface VideoMapper extends BaseMapper<Video> {
    IPage<Video> selectWithPetInfoPage(Page page, @Param("video") Video video);
    Video selectWithPetInfo(Integer id);

    void updateViewCount(Integer id);
}
