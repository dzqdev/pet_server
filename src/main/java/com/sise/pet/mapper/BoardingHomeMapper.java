package com.sise.pet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.entity.BoardingHome;

/**
 * <p>
 * 寄养所信息 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2020-02-23
 */
public interface BoardingHomeMapper extends BaseMapper<BoardingHome> {
    IPage<BoardingHome> getWithSubImgPage(Page page, BoardingHome boardingHome);

    BoardingHome getSingleWithSubImg(Integer id);
}
