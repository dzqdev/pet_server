package com.sise.pet.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.pet.entity.ImgEntity;

/**
 * <p>
 * 图片-实体中间表 服务类
 * </p>
 *
 * @author author
 * @since 2020-02-23
 */
public interface IImgEntityService extends IService<ImgEntity> {
    Page<ImgEntity> selectPage(ImgEntity entity, Page page);
}
