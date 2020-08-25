package com.sise.pet.controller.v1;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.core.CommonResult;
import com.sise.pet.entity.DictDetail;
import com.sise.pet.service.IDictDetailService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 数据字典详情 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-02-21
 */
@RestController
@RequestMapping(value = {"api/v1/dict-detail","api/v2/dict-detail"})
public class DictDetailController {

    @Resource
    private IDictDetailService iDictDetailService;

    @PostMapping
    public CommonResult addDictDetail(DictDetail dict){
        iDictDetailService.save(dict);
        return CommonResult.success(null);
    }

    @PutMapping
    public CommonResult updateDictDetail(DictDetail dict){
        iDictDetailService.updateById(dict);
        return CommonResult.success(null);
    }

    @GetMapping
    public CommonResult dictDetailList(DictDetail dict, Page page){
        Page<DictDetail> dictPage = iDictDetailService.selectPage(dict, page);
        return CommonResult.success(dictPage);
    }

}
