package com.sise.pet.mapper;

import com.sise.pet.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2020-02-27
 */
public interface UserMapper extends BaseMapper<User> {

    boolean updatePassword(@Param("account") String account, @Param("password") String password);
}
