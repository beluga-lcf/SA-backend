package com.example.genius.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.genius.dto.userPackage.RePatentRequest;
import com.example.genius.dto.userPackage.RePatentResult;
import com.example.genius.entity.UserId2PSPatentId;
import com.example.genius.enums.SelectCollectMod;

public interface UserId2PSPIdService extends IService<UserId2PSPatentId> {
    RePatentResult collectP(int userid, RePatentRequest patentRequest);
    RePatentResult deleteP(int userid, RePatentRequest rePatentRequest);
    RePatentResult checkP(int userid, String rePatentId);
    RePatentResult selectP(int userid, String selectPName, SelectCollectMod sMod);
    RePatentResult getP(int userid);
    long getNum(int userid);
}
