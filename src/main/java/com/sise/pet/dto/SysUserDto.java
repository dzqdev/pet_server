package com.sise.pet.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sise.pet.entity.SysRole;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SysUserDto{
    private Long id;

    private List<SysRole> roles;

    private String username;

    private String phone;

    private String avatar;

    private String email;

    @JsonIgnore
    private String password;

    private Boolean enabled;

    @JsonIgnore
    private Boolean isAdmin = false;

    private Date pwdResetTime;
}
