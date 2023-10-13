package com.example.genius.service.impl;

import com.example.genius.entity.AuthorsCountsByYear;
import com.example.genius.mapper.AuthorsCountsByYearMapper;
import com.example.genius.service.IAuthorsCountsByYearService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chaofan
 * @since 2023-10-13
 */
@Service
public class AuthorsCountsByYearServiceImpl extends ServiceImpl<AuthorsCountsByYearMapper, AuthorsCountsByYear> implements IAuthorsCountsByYearService {

}
