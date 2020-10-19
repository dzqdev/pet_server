package com.sise.pet.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.pet.dto.DictDetailDto;
import com.sise.pet.dto.query.DictDetailQueryCriteria;
import com.sise.pet.entity.DictDetail;

import java.util.List;

/**
 * <p>
 * 数据字典详情 服务类
 * </p>
 *
 * @author author
 * @since 2020-02-21
 */
public interface IDictDetailService extends IService<DictDetail> {

    Page<DictDetailDto> queryAll(DictDetailQueryCriteria criteria, Page pageable);

    List<DictDetailDto> getDictByName(String name);
}
