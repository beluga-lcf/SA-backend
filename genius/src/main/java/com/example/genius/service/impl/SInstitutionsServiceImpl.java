package com.example.genius.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.genius.entity.storage.SInstitutions;
import com.example.genius.mapper.storage.SInstitutionsMapper;
import com.example.genius.service.storage.SInstitutionsService;
import org.springframework.stereotype.Service;

@Service
public class SInstitutionsServiceImpl extends ServiceImpl<SInstitutionsMapper, SInstitutions> implements SInstitutionsService {
}
