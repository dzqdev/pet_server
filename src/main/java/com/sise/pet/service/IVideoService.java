package com.sise.pet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.pet.entity.Video;

/**
 * <p>
 * 视频 服务类
 * </p>
 *
 * @author author
 * @since 2020-03-14
 */
public interface IVideoService extends IService<Video> {
    IPage<Video> selectWithPetInfoPage(Page page, Video video);

    Video selectWithPetInfo(Integer id);
}
