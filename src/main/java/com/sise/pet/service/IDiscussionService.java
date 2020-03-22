package com.sise.pet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.entity.Discussion;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 讨论 服务类
 * </p>
 *
 * @author author
 * @since 2020-02-27
 */
public interface IDiscussionService extends IService<Discussion> {

    IPage<Discussion> selectPage(Discussion entity, Page page);

    Discussion getSingleDiscussion(Integer id);

    /**
     * 根据id删除一个讨论
     * @param id
     */
    void deleteById(Integer id);

    List<Discussion> getPopularDiscussion();

    void updateViewCount(Integer id);
}
