package com.sise.pet.controller.v1;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sise.pet.core.CommonResult;
import com.sise.pet.dto.SysUserDto;
import com.sise.pet.dto.query.SysUserQueryCriteria;
import com.sise.pet.dto.query.WebLogQueryCriteria;
import com.sise.pet.entity.WebLog;
import com.sise.pet.service.IWebLogService;
import com.sise.pet.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "系统：日志管理")
@RestController
@RequestMapping(value = "/api/v1/log")
public class WebLogController {

    @Resource
    private IWebLogService logService;

    @GetMapping(value = "/user")
    @ApiOperation("用户日志查询")
    public CommonResult queryUserLog(WebLogQueryCriteria criteria, Page pageable) {
        criteria.setUsername(SecurityUtils.getCurrentUsername());
        Page<WebLog> page = logService.queryAll(criteria, pageable);
        return CommonResult.success(page);
    }
}
