package com.example.genius.service;

import java.util.ArrayList;

public interface AuthorService {
    public ArrayList<String> getAuthorIdByWorkId(String workId) throws Exception;
}
