package com.sise.pet.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.pet.dto.DictDto;
import com.sise.pet.dto.query.DictQueryCriteria;
import com.sise.pet.entity.Dict;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author author
 * @since 2020-02-21
 */
public interface IDictService extends IService<Dict> {

    List<DictDto> queryAll(DictQueryCriteria dictQueryCriteria);

    Page<DictDto> queryAll(DictQueryCriteria dictQueryCriteria,Page pageable);

    void delete(Set<Long> ids);
}
