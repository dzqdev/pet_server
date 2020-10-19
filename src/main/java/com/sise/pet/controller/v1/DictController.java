package com.sise.pet.controller.v1;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.core.CommonResult;
import com.sise.pet.dto.query.DictQueryCriteria;
import com.sise.pet.entity.Dict;
import com.sise.pet.service.IDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

/**
 * <p>
 * 数据字典 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-02-21
 */
@RestController
@RequestMapping("/api/v1/dict")
@Api(tags = "系统：字典接口")
public class DictController {

    @Resource
    private IDictService dictService;

    @ApiOperation("查询字典")
    @GetMapping(value = "/all")
    public CommonResult queryAll(){
        return CommonResult.success(dictService.queryAll(new DictQueryCriteria()));
    }

    @ApiOperation("查询字典")
    @GetMapping
    public CommonResult query(DictQueryCriteria resources, Page pageable){
        return CommonResult.success(dictService.queryAll(resources,pageable));
    }

    @ApiOperation("新增字典")
    @PostMapping
    public CommonResult create(@RequestBody Dict resources){
        dictService.save(resources);
        return CommonResult.success(null);
    }

    @ApiOperation("修改字典")
    @PutMapping
    @PreAuthorize("@el.check('dict:edit')")
    public CommonResult update(@RequestBody Dict resources){
        dictService.updateById(resources);
        return CommonResult.success(HttpStatus.NO_CONTENT);
    }


    @ApiOperation("删除字典")
    @DeleteMapping
    @PreAuthorize("@el.check('dict:del')")
    public CommonResult delete(@RequestBody Set<Long> ids){
        dictService.delete(ids);
        return CommonResult.success(null);
    }
}
