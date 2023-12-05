package com.example.genius.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.genius.dto.userPackage.ThesisRequest;
import com.example.genius.dto.userPackage.ThesisResult;
import com.example.genius.entity.UserId2PSThesisId;

public interface UserId2PSTIdService extends IService<UserId2PSThesisId> {
    ThesisResult collectT(int userid, ThesisRequest thesisRequest);
    ThesisResult deleteT(int userid, ThesisRequest thesisRequest);
}
