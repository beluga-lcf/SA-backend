package com.example.genius.entity;

import lombok.Data;
import java.util.List;

@Data
public class Work {
    // 表示文章是否已被撤回
    private boolean isRetracted;

    // 与文章处理费（APC）相关的列表或信息
    private Object apcList;

    // 表示文本是否为某种特殊文本
    private boolean isParatext;

    // 引用该文章的API链接
    private String citedByApiUrl;

    // 每年的计数
    private List<Object> countsByYear;

    // 与文献信息（卷、期、页码等）相关的信息
    private Biblio biblio;

    // 主要位置信息，包含许可证、PDF链接、是否为开放获取等
    private PrimaryLocation primaryLocation;

    // 文章所使用的语言
    private String language;

    // 与该文章相关的其他作品链接列表
    private List<String> relatedWorks;

    // 文章标题
    private String title;

    // 文章类型（书籍章节等）
    private String type;

    // 相应机构的ID列表
    private List<Object> correspondingInstitutionIds;

    // 摘要统计信息，如引用计数等
    private SummaryStats summaryStats;

    // 文章发布日期
    private String publicationDate;

    // 开放获取信息，包括开放获取状态、URL等
    private OpenAccess openAccess;

    // 作者信息列表
    private List<Object> authorships;

    // 文章的唯一标识符
    private String id;

    // 文章摘要的反向索引信息
    private Object abstractInvertedIndex;

    // 可持续发展目标相关信息的列表
    private List<Object> sustainableDevelopmentGoals;

    // Medical Subject Headings（医学主题词）相关的信息
    private List<Object> mesh;

    // 与该文章相关的赠款信息
    private List<Object> grants;

    // 最佳开放获取位置信息
    private Object bestOALocation;

    // 文章被引用的次数
    private int citedByCount;

    // 涉及概念的计数
    private int conceptsCount;

    // 引用的作品列表
    private List<Object> referencedWorks;

    // 文章类型（Crossref的一种类型）
    private String typeCrossref;

    // 引用的作品计数
    private int referencedWorksCount;

    // 文章的显示名称
    private String displayName;

    // DOI注册机构
    private String doiRegistrationAgency;

    // 文章发表年份
    private int publicationYear;

    // 涉及的概念列表，包括得分、级别、标识和名称
    private List<Concepts> concepts;

    // 作者计数
    private int authorsCount;

    // 文章的不同标识符（OpenAlex、DOI等）
    private Ids ids;

    // 位置计数
    private int locationsCount;

    // 位置信息列表，包括许可证、PDF链接、是否为开放获取等
    private List<Locations> locations;

    // 相应作者的ID列表
    private List<Object> correspondingAuthorIds;

    // 更新日期时间戳
    private String updatedDate;

    // 创建日期时间戳
    private String createdDate;

    // APC是否已支付
    private Object apcPaid;

    // 更新日期时间戳
    private String updated;

    // 文章的 DOI
    private String doi;

    // 内部类
    @Data
    public static class Biblio {
        private Object volume;
        private Object issue;
        private Object firstPage;
        private Object lastPage;
    }

    @Data
    public static class PrimaryLocation {
        private Object license;
        private Object pdfUrl;
        private boolean isOA;
        private Source source;
        private String landingPageUrl;
        private Object version;
        private String doi;
    }

    @Data
    public static class Source {
        private Object publisherId;
        private List<Object> hostOrganizationLineage;
        private List<Object> hostOrganizationLineageNames;
        private boolean isInDOAJ;
        private List<Object> publisherLineage;
        private Object issnL;
        private List<Object> publisherLineageNames;
        private String displayName;
        private String type;
        private Object hostOrganizationName;
        private Object issn;
        private String publisher;
        private List<Object> hostInstitutionLineageNames;
        private boolean isOA;
        private String id;
        private List<Object> hostInstitutionLineage;
        private Object hostOrganization;
    }

    @Data
    public static class SummaryStats {
        private int citedByCount;
        private int yrCitedByCount;
    }

    @Data
    public static class OpenAccess {
        private String oaStatus;
        private boolean isOA;
        private Object oaUrl;
        private boolean anyRepositoryHasFulltext;
    }

    @Data
    public static class Concepts {
        private double score;
        private int level;
        private String id;
        private String displayName;
        private String wikidata;
    }

    @Data
    public static class Ids {
        private String openalex;
        private String doi;
    }

    @Data
    public static class Locations {
        private Object license;
        private Object pdfUrl;
        private boolean isOA;
        private Source source;
        private String landingPageUrl;
        private Object version;
        private String doi;
    }
}

