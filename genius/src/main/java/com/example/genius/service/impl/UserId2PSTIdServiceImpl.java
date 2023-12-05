package com.example.genius.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.genius.dto.userPackage.ThesisRequest;
import com.example.genius.dto.userPackage.ThesisResult;
import com.example.genius.entity.UserId2PSThesisId;
import com.example.genius.mapper.UserId2PSTIdMapper;
import com.example.genius.service.UserId2PSTIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserId2PSTIdServiceImpl extends ServiceImpl<UserId2PSTIdMapper, UserId2PSThesisId> implements UserId2PSTIdService {
    @Autowired
    UserId2PSTIdMapper userId2PSTIdMapper;

    @Override
    public ThesisResult collectT(int userid, ThesisRequest thesisRequest) {
        UserId2PSThesisId userId2PSThesisId = new UserId2PSThesisId();
        userId2PSThesisId.setUserId(userid);
        userId2PSThesisId.setpSThesisId(thesisRequest.getThesisId());
        userId2PSThesisId.setpSThesisName(thesisRequest.getThesisName());
        QueryWrapper<UserId2PSThesisId> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", userid);
        queryWrapper.eq("psthesisid", thesisRequest.getThesisId());
        UserId2PSThesisId checker = userId2PSTIdMapper.selectOne(queryWrapper);
        if (checker != null) {
            return new ThesisResult(1011, "重复收藏论文");
        }
        else {
            userId2PSTIdMapper.insert(userId2PSThesisId);
            return new ThesisResult(200, "收藏论文成功");
        }
    }

    @Override
    public ThesisResult deleteT(int userid, ThesisRequest thesisRequest) {
        QueryWrapper<UserId2PSThesisId> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", userid);
        queryWrapper.eq("psthesisid", thesisRequest.getThesisId());
        UserId2PSThesisId checker = userId2PSTIdMapper.selectOne(queryWrapper);
        if (checker == null) {
            return new ThesisResult(1012, "未收藏论文");
        }
        else {
            userId2PSTIdMapper.delete(queryWrapper);
            return new ThesisResult(200, "取消收藏论文成功");
        }
    }
}
