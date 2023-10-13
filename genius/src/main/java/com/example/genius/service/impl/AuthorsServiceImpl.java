package com.example.genius.service.impl;

import com.example.genius.entity.Authors;
import com.example.genius.mapper.AuthorsMapper;
import com.example.genius.service.IAuthorsService;
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
public class AuthorsServiceImpl extends ServiceImpl<AuthorsMapper, Authors> implements IAuthorsService {

}
