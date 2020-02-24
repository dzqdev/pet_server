package com.sise.pet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.pet.entity.Dict;
import com.sise.pet.entity.DictDetail;
import com.sise.pet.mapper.DictDetailMapper;
import com.sise.pet.mapper.DictMapper;
import com.sise.pet.service.IDictDetailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 数据字典详情 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-02-21
 */
@Service
public class DictDetailServiceImpl extends ServiceImpl<DictDetailMapper, DictDetail> implements IDictDetailService {

    @Resource
    private DictDetailMapper dictDetailMapper;

    @Resource
    private DictMapper dictMapper;

    @Override
    public Page<DictDetail> selectPage(DictDetail dictDetail, Page page) {
        if(StringUtils.isNotEmpty(dictDetail.getDictName())){
            String dictName = dictDetail.getDictName();
            QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
            dictQueryWrapper.eq("name", dictName);
            Dict dict = dictMapper.selectOne(dictQueryWrapper);
            if(dict != null){
                dictDetail.setDictId(dict.getId());
            }else{
                return null;
            }
        }
        QueryWrapper<DictDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_id", dictDetail.getDictId());
        Page result = dictDetailMapper.selectPage(page, queryWrapper);
        return result;
    }
}
