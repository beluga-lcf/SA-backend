package com.example.genius.service;

import com.example.genius.dto.payload.*;

public interface ApiService {
    public String getArticles(ArticlesPayload payload, int type) throws Exception;
    public String getPatents(PatentsPayload payload, int type) throws Exception;
    public String getBooks(BooksPayload payload, int type) throws Exception;
    public String getBulletins(BulletinsPayload payload, int type) throws Exception;
    public String getReports(ReportsPayload payload, int type) throws Exception;
    public String getSciencedata(SciencedataPayload payload, int type) throws Exception;
}
