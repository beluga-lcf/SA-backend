package com.example.genius.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.genius.dto.userPackage.RePatentRequest;
import com.example.genius.dto.userPackage.RePatentResult;
import com.example.genius.dto.userPackage.ThesisResult;
import com.example.genius.entity.UserId2PSPatentId;
import com.example.genius.entity.UserId2PSThesisId;
import com.example.genius.enums.SelectCollectMod;
import com.example.genius.mapper.UserId2PSPIdMapper;
import com.example.genius.service.UserId2PSPIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserId2PSPIdServiceImpl extends ServiceImpl<UserId2PSPIdMapper, UserId2PSPatentId> implements UserId2PSPIdService {
    @Autowired
    UserId2PSPIdMapper userId2PSPIdMapper;

    @Override
    @Transactional
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
            return new RePatentResult(1013, "重复收藏专利", null);
        }
        else {
            userId2PSPIdMapper.insert(userId2PSPatentId);
            return new RePatentResult(200, "收藏专利成功", null);
        }
    }

    @Override
    @Transactional
    public RePatentResult deleteP(int userid, RePatentRequest rePatentRequest) {
        QueryWrapper<UserId2PSPatentId> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", userid);
        queryWrapper.eq("pspatentid", rePatentRequest.getPatentId());
        UserId2PSPatentId checker = userId2PSPIdMapper.selectOne(queryWrapper);
        if (checker == null) {
            return new RePatentResult(1013, "未收藏专利", null);
        }
        else {
            userId2PSPIdMapper.delete(queryWrapper);
            return new RePatentResult(200, "取消收藏专利成功", null);
        }
    }

    @Override
    @Transactional
    public RePatentResult checkP(int userid, String rePatentId) {
        QueryWrapper<UserId2PSPatentId> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", userid);
        queryWrapper.eq("pspatentid", rePatentId);
        UserId2PSPatentId checker = userId2PSPIdMapper.selectOne(queryWrapper);
        if (checker == null) {
            return new RePatentResult(200, "false", null);
        }
        else {
            return new RePatentResult(200, "true", null);
        }
    }

    @Override
    @Transactional
    public RePatentResult selectP(int userid, String selectPName, SelectCollectMod mod) {
        LambdaQueryWrapper<UserId2PSPatentId> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserId2PSPatentId::getUserId, userid);
        List<UserId2PSPatentId> checkers;
        if (mod == null) {
            return new RePatentResult(1021, "未知的查询模式", null);
        }
        switch (mod) {
            case contain_full : {
                lambdaQueryWrapper.like(UserId2PSPatentId::getPatentName, selectPName);
                checkers = userId2PSPIdMapper.selectList(lambdaQueryWrapper);
                break;
            }
            case contain_fragment: {
                checkers = fragmentLike(selectPName, lambdaQueryWrapper);
                break;
            }
            default: {
                return new RePatentResult(1021, "未知的查询模式", null);
            }
        }
        if (checkers == null || checkers.size() == 0) {
            return new RePatentResult(1020, "未查询到收藏专利", null);
        }
        else {
            return new RePatentResult(200, "查询到收藏专利", checkers);
        }
    }

    @Override
    @Transactional
    public RePatentResult getP(int userid) {
        QueryWrapper<UserId2PSPatentId> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", userid);
        List<UserId2PSPatentId> patents;
        patents = userId2PSPIdMapper.selectList(queryWrapper);
        return new RePatentResult(200, "获取收藏专利", patents);
    }

    @Transactional
    public List<UserId2PSPatentId> fragmentLike(String selectPName, LambdaQueryWrapper<UserId2PSPatentId> lambdaQueryWrapper) {
        int countCharNum = selectPName.length();

        // 动态拼接OR
        for (int i = 0; i < countCharNum; i++) {
            String aStr = String.valueOf(selectPName.charAt(i));
            lambdaQueryWrapper.or(wrapper -> wrapper.like(UserId2PSPatentId::getPSPatentId, aStr));
        }

        List<UserId2PSPatentId> checkers = userId2PSPIdMapper.selectList(lambdaQueryWrapper);

        countCharNum = countCharNum * 4 / 5 + 1;
        int finalCountCharNum = countCharNum;
        checkers.removeIf(checker -> {
            int count = 0;
            for (int i = 0; i < finalCountCharNum; i++) {
                if (checker.getPatentName().contains(String.valueOf(selectPName.charAt(i)))) {
                    count++;
                }
            }
            return count < finalCountCharNum;
        });
        return checkers;
    }
}
