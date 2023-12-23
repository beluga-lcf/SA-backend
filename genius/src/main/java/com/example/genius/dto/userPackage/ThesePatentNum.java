package com.example.genius.dto.userPackage;

public class ThesePatentNum {
    private long numThesis;
    private long numPatent;

    public ThesePatentNum(long numThesis, long numPatent) {
        this.numThesis = numThesis;
        this.numPatent = numPatent;
    }

    public long getNumThesis() {
        return numThesis;
    }

    public long getNumPatent() {
        return numPatent;
    }
}
