package com.example.genius.entity;

import lombok.Data;
import java.util.List;

@Data
public class Concept {
    private int citedByCount;
    private String imageThumbnailUrl;
    private int level;
    private List<CountByYear> countsByYear;
    private String imageUrl;
    private String description;
    private String displayName;
    private List<Object> relatedConcepts;
    private SummaryStats summaryStats;
    private String worksApiUrl;
    private Ids ids;
    private String id;
    private String updatedDate;
    private String createdDate;
    private int worksCount;
    private International international;
    private List<Ancestor> ancestors;
    private String wikidata;
    private String updated;

    @Data
    public static class CountByYear {
        private int citedByCount;
        private int year;
        private int worksCount;
        private int oaWorksCount;
    }

    @Data
    public static class SummaryStats {
        private int citedByCount;
        private int twoYearI10Index;
        private int hIndex;
        private int i10Index;
        private double oaPercent;
        private int twoYearMeanCitedness;
        private int worksCount;
        private int twoYearWorksCount;
        private int twoYearHIndex;
        private int twoYearCitedByCount;
    }

    @Data
    public static class Ids {
        private int mag;
        private String openalex;
        private String wikipedia;
        private String wikidata;
    }

    @Data
    public static class International {
        private Description description;

        @Data
        public static class Description {
            private String kk;
            private String de;
            private String be;
            private String fi;
            private String ru;
            private String pt;
            private String en;
            private String it;
            private String es;
            private String ar;
            // Add more language descriptions if needed
        }
    }

    @Data
    public static class Ancestor {
        private int level;
        private String id;
        private String displayName;
        private String wikidata;
    }
}

