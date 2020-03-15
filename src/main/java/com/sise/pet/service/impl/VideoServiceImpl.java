package com.sise.pet.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.pet.entity.Video;
import com.sise.pet.mapper.VideoMapper;
import com.sise.pet.service.IVideoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 视频 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-03-14
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements IVideoService {

    @Resource
    private VideoMapper videoMapper;

    @Override
    public IPage<Video> selectWithPetInfoPage(Page page, Video video) {
        return videoMapper.selectWithPetInfoPage(page,video);
    }

    @Override
    public Video selectWithPetInfo(Integer id) {
        return videoMapper.selectWithPetInfo(id);
    }
}
