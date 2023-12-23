package com.example.genius.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.genius.dto.userPackage.ThesisRequest;
import com.example.genius.dto.userPackage.ThesisResult;
import com.example.genius.entity.UserId2PSThesisId;
import com.example.genius.enums.SelectCollectMod;

public interface UserId2PSTIdService extends IService<UserId2PSThesisId> {
    ThesisResult collectT(int userid, ThesisRequest thesisRequest);
    ThesisResult deleteT(int userid, ThesisRequest thesisRequest);
    ThesisResult checkT(int userid, String thesisId);
    ThesisResult selectT(int userid, String selectTName, SelectCollectMod mod);
    ThesisResult getT(int userid);
    long getNum(int userid);
}
