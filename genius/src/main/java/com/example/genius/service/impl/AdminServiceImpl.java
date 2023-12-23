package com.example.genius.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.genius.entity.Admin;
import com.example.genius.mapper.AdminMapper;
import com.example.genius.service.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
}
