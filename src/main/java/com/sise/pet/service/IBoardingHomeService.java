package com.sise.pet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.pet.entity.BoardingHome;

/**
 * <p>
 * 寄养所信息 服务类
 * </p>
 *
 * @author author
 * @since 2020-02-23
 */
public interface IBoardingHomeService extends IService<BoardingHome> {
    IPage<BoardingHome> selectPage(BoardingHome entity, Page page);
}
