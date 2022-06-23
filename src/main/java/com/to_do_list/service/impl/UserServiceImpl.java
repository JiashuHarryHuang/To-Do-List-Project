package com.to_do_list.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.to_do_list.dao.UserDao;
import com.to_do_list.domain.User;
import com.to_do_list.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
}
