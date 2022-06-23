package com.to_do_list.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.to_do_list.dao.AssignmentDao;
import com.to_do_list.domain.Assignment;
import com.to_do_list.service.AssignmentService;
import org.springframework.stereotype.Service;

@Service
public class AssignmentServiceImpl extends ServiceImpl<AssignmentDao, Assignment> implements AssignmentService {
}
