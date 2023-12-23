package com.example.genius.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.genius.dto.userPackage.ThesisRequest;
import com.example.genius.dto.userPackage.ThesisResult;
import com.example.genius.entity.UserId2PSThesisId;
import com.example.genius.enums.SelectCollectMod;
import com.example.genius.mapper.UserId2PSTIdMapper;
import com.example.genius.service.UserId2PSTIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserId2PSTIdServiceImpl extends ServiceImpl<UserId2PSTIdMapper, UserId2PSThesisId> implements UserId2PSTIdService {
    @Autowired
    UserId2PSTIdMapper userId2PSTIdMapper;

    @Override
    @Transactional
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
            return new ThesisResult(1011, "重复收藏论文", null);
        }
        else {
            userId2PSTIdMapper.insert(userId2PSThesisId);
            return new ThesisResult(200, "收藏论文成功", null);
        }
    }

    @Override
    @Transactional
    public ThesisResult deleteT(int userid, ThesisRequest thesisRequest) {
        QueryWrapper<UserId2PSThesisId> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", userid);
        queryWrapper.eq("psthesisid", thesisRequest.getThesisId());
        UserId2PSThesisId checker = userId2PSTIdMapper.selectOne(queryWrapper);
        if (checker == null) {
            return new ThesisResult(1012, "未收藏论文", null);
        }
        else {
            userId2PSTIdMapper.delete(queryWrapper);
            return new ThesisResult(200, "取消收藏论文成功", null);
        }
    }

    @Override
    @Transactional
    public ThesisResult checkT(int userid, String thesisId) {
        QueryWrapper<UserId2PSThesisId> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", userid);
        queryWrapper.eq("psthesisid", thesisId);
        UserId2PSThesisId checker = userId2PSTIdMapper.selectOne(queryWrapper);
        if (checker == null) {
            return new ThesisResult(200, "false", null);
        }
        else {
            return new ThesisResult(200, "true", null);
        }
    }

    @Override
    @Transactional
    public ThesisResult selectT(int userid, String selectTName, SelectCollectMod mod) {
        LambdaQueryWrapper<UserId2PSThesisId> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserId2PSThesisId::getUserId, userid);
        List<UserId2PSThesisId> checkers;
        if (mod == null) {
            return new ThesisResult(1021, "未知的查询模式", null);
        }
        switch (mod) {
            case contain_full : {
                lambdaQueryWrapper.like(UserId2PSThesisId::getPSThesisName, selectTName);
                checkers = userId2PSTIdMapper.selectList(lambdaQueryWrapper);
                break;
            }
            case contain_fragment: {
                checkers = fragmentLike(selectTName, lambdaQueryWrapper);
                break;
            }
            default: {
                return new ThesisResult(1021, "未知的查询模式", null);
            }
        }
        if (checkers == null || checkers.size() == 0) {
            return new ThesisResult(1019, "未查询到收藏论文", null);
        }
        else {
            return new ThesisResult(200, "查询到收藏论文", checkers);
        }
    }

    @Override
    @Transactional
    public ThesisResult getT(int userid) {
        QueryWrapper<UserId2PSThesisId> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", userid);
        List<UserId2PSThesisId> thesisIds;
        thesisIds = userId2PSTIdMapper.selectList(queryWrapper);
        return new ThesisResult(200, "获取收藏论文", thesisIds);
    }

    @Transactional
    public List<UserId2PSThesisId> fragmentLike(String selectTName, LambdaQueryWrapper<UserId2PSThesisId> lambdaQueryWrapper) {
        int countCharNum = selectTName.length();

        // 动态拼接OR
        for (int i = 0; i < countCharNum; i++) {
            String aStr = String.valueOf(selectTName.charAt(i));
            lambdaQueryWrapper.or(wrapper -> wrapper.like(UserId2PSThesisId::getPSThesisName, aStr));
        }

        List<UserId2PSThesisId> checkers = userId2PSTIdMapper.selectList(lambdaQueryWrapper);

        countCharNum = countCharNum * 4 / 5 + 1;
        int finalCountCharNum = countCharNum;
        checkers.removeIf(checker -> {
            int count = 0;
            for (int i = 0; i < finalCountCharNum; i++) {
                if (checker.getPSThesisName().contains(String.valueOf(selectTName.charAt(i)))) {
                    count++;
                }
            }
            return count < finalCountCharNum;
        });
        return checkers;
    }
}
