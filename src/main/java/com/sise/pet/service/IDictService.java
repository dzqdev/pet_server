package com.sise.pet.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.pet.entity.Dict;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author author
 * @since 2020-02-21
 */
public interface IDictService extends IService<Dict> {
    public Page<Dict> selectPage(Dict dict, Page page);
}
