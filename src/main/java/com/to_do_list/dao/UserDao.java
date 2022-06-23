package com.to_do_list.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.to_do_list.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {
}
