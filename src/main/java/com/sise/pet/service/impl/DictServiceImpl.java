package com.sise.pet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.pet.dto.DictDto;
import com.sise.pet.dto.convert.DictConvert;
import com.sise.pet.dto.convert.DictDetailConvert;
import com.sise.pet.dto.convert.PageConvert;
import com.sise.pet.dto.query.DictQueryCriteria;
import com.sise.pet.entity.Dict;
import com.sise.pet.entity.DictDetail;
import com.sise.pet.mapper.DictDetailMapper;
import com.sise.pet.mapper.DictMapper;
import com.sise.pet.service.IDictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-02-21
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

    @Resource
    private DictDetailMapper dictDetailMapper;
    @Resource
    private DictConvert dictConvert;
    @Resource
    private DictDetailConvert dictDetailConvert;
    @Resource
    private DictMapper dictMapper;
    @Resource
    private PageConvert<DictDto, Dict> pageConvert;


    @Override
    public List<DictDto> queryAll(DictQueryCriteria dictQueryCriteria) {
        LambdaQueryWrapper<Dict> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(dictQueryCriteria.getBlurry())){
            queryWrapper.like(Dict::getName, dictQueryCriteria.getBlurry())
                    .or().like(Dict::getRemark, dictQueryCriteria.getBlurry());
        }
        List<Dict> list = list(queryWrapper);
        List<DictDto> dictDtos = dictConvert.toDto(list);
        dictDtos.forEach(dto->{
            //查询dict的所有dictDetail
            List<DictDetail> dictDetails = dictDetailMapper.selectList(new LambdaQueryWrapper<DictDetail>().eq(DictDetail::getDictId, dto.getId()));
            dto.setDictDetails(dictDetailConvert.toDto(dictDetails));
        });
        return dictDtos;
    }

    @Override
    public Page<DictDto> queryAll(DictQueryCriteria dictQueryCriteria, Page pageable) {
        LambdaQueryWrapper<Dict> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(dictQueryCriteria.getBlurry())){
            queryWrapper.like(Dict::getName, dictQueryCriteria.getBlurry())
                    .or().like(Dict::getRemark, dictQueryCriteria.getBlurry());
        }
        Page<Dict> page = dictMapper.selectPage(pageable, queryWrapper);
        List<DictDto> dictDtos = dictConvert.toDto(page.getRecords());
        dictDtos.forEach(dto->{
            //查询dict的所有dictDetail
            List<DictDetail> dictDetails = dictDetailMapper.selectList(new LambdaQueryWrapper<DictDetail>().eq(DictDetail::getDictId, dto.getId()));
            dto.setDictDetails(dictDetailConvert.toDto(dictDetails));
        });
        Page<DictDto> dictDtoPage = pageConvert.toPageDto(page, dictDtos);
        return dictDtoPage;
    }

    @Override
    public void delete(Set<Long> ids) {
        removeByIds(ids);
    }
}
