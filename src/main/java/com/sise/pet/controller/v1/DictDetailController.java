package com.sise.pet.controller.v1;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.core.Result;
import com.sise.pet.core.ResultGenerator;
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
    public Result addDictDetail(DictDetail dict){
        iDictDetailService.save(dict);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result updateDictDetail(DictDetail dict){
        iDictDetailService.updateById(dict);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping
    public Result dictDetailList(DictDetail dict, Page page){
        Page<DictDetail> dictPage = iDictDetailService.selectPage(dict, page);
        return ResultGenerator.genSuccessResult(dictPage);
    }

}
