package com.sise.pet.controller.v1;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.core.CommonResult;
import com.sise.pet.entity.Dict;
import com.sise.pet.service.IDictService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 数据字典 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-02-21
 */
@RestController
@RequestMapping("api/v1/dict")
public class DictController {

    @Resource
    private IDictService iDictService;

    @PostMapping
    public CommonResult addDic(Dict dict){
        iDictService.save(dict);
        return CommonResult.success(null);
    }

    @PutMapping
    public CommonResult updateDic(Dict dict){
        iDictService.updateById(dict);
        return CommonResult.success(null);
    }

    @GetMapping
    public CommonResult dicList(Dict dict, Page page){
        Page<Dict> dictPage = iDictService.selectPage(dict, page);
        return CommonResult.success(dictPage);
    }

}
