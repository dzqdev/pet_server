package com.sise.pet.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdateSysUserPasswordParam {
    @ApiModelProperty(value = "用户id", required = true)
    @NotEmpty(message = "用户id不能为空")
    private String userId;
    @ApiModelProperty(value = "旧密码", required = true)
    @NotEmpty(message = "旧密码不能为空")
    private String oldPassword;
    @ApiModelProperty(value = "新密码", required = true)
    @NotEmpty(message = "新密码不能为空")
    private String newPassword;
}
