package com.sise.pet.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.entity.Discussion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 讨论 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2020-02-27
 */
public interface DiscussionMapper extends BaseMapper<Discussion> {

    IPage<Discussion> getDetailInfoPage(Page page, Discussion entity);
}
