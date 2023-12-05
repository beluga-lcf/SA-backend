package com.example.genius.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.genius.dto.userPackage.RePatentRequest;
import com.example.genius.dto.userPackage.RePatentResult;
import com.example.genius.dto.userPackage.ThesisResult;
import com.example.genius.entity.UserId2PSPatentId;
import com.example.genius.entity.UserId2PSThesisId;
import com.example.genius.mapper.UserId2PSPIdMapper;
import com.example.genius.service.UserId2PSPIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserId2PSPIdServiceImpl extends ServiceImpl<UserId2PSPIdMapper, UserId2PSPatentId> implements UserId2PSPIdService {
    @Autowired
    UserId2PSPIdMapper userId2PSPIdMapper;

    @Override
    public RePatentResult collectP(int userid, RePatentRequest patentRequest) {
        UserId2PSPatentId userId2PSPatentId = new UserId2PSPatentId();
        userId2PSPatentId.setUserId(userid);
        userId2PSPatentId.setPSPatentId(patentRequest.getPatentId());
        userId2PSPatentId.setPatentName(patentRequest.getPatentName());
        QueryWrapper<UserId2PSPatentId> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", userid);
        queryWrapper.eq("pspatentId", patentRequest.getPatentId());
        UserId2PSPatentId checker = userId2PSPIdMapper.selectOne(queryWrapper);
        if (checker != null) {
            return new RePatentResult(1013, "重复收藏专利");
        }
        else {
            userId2PSPIdMapper.insert(userId2PSPatentId);
            return new RePatentResult(200, "收藏专利成功");
        }
    }

    @Override
    public RePatentResult deleteP(int userid, RePatentRequest rePatentRequest) {
        QueryWrapper<UserId2PSPatentId> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", userid);
        queryWrapper.eq("pspatentid", rePatentRequest.getPatentId());
        UserId2PSPatentId checker = userId2PSPIdMapper.selectOne(queryWrapper);
        if (checker == null) {
            return new RePatentResult(1013, "未收藏专利");
        }
        else {
            userId2PSPIdMapper.delete(queryWrapper);
            return new RePatentResult(200, "取消收藏专利成功");
        }
    }


}
