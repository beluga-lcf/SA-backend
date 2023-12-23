package com.example.genius.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.genius.entity.Admin;
import com.example.genius.mapper.AdminMapper;
import org.springframework.stereotype.Service;

@Service
public class AdminService extends ServiceImpl<AdminMapper, Admin> implements com.example.genius.service.AdminService {
}
