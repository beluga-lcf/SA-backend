package com.example.genius.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.generated.entity.Works;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chaofan
 * @since 2023-11-11
 */
public interface IWorkssService extends IService<Works> {
    public List<Map<String, Object>> getTypeCounts();
}
