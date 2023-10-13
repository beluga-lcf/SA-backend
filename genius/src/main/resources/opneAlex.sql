-- 设置字符集
SET NAMES utf8mb4;

-- 创建数据库
CREATE DATABASE IF NOT EXISTS openalex;

-- 使用数据库
USE openAlex;

-- 创建 authors 表
CREATE TABLE authors (
                         id VARCHAR(255) NOT NULL,
                         orcid VARCHAR(255),
                         display_name VARCHAR(255),
                         display_name_alternatives JSON,
                         works_count INT,
                         cited_by_count INT,
                         last_known_institution VARCHAR(255),
                         works_api_url VARCHAR(255),
                         updated_date TIMESTAMP
);

-- 创建 authors_counts_by_year 表
CREATE TABLE authors_counts_by_year (
                                        author_id VARCHAR(255) NOT NULL,
                                        year INT NOT NULL,
                                        works_count INT,
                                        cited_by_count INT,
                                        oa_works_count INT
);

-- 创建 authors_ids 表
CREATE TABLE authors_ids (
                             author_id VARCHAR(255) NOT NULL,
                             openalex VARCHAR(255),
                             orcid VARCHAR(255),
                             scopus VARCHAR(255),
                             twitter VARCHAR(255),
                             wikipedia VARCHAR(255),
                             mag BIGINT
);

-- 创建 concepts 表
CREATE TABLE concepts (
                          id VARCHAR(255) NOT NULL,
                          wikidata VARCHAR(255),
                          display_name VARCHAR(255),
                          level INT,
                          description TEXT,
                          works_count INT,
                          cited_by_count INT,
                          image_url VARCHAR(255),
                          image_thumbnail_url VARCHAR(255),
                          works_api_url VARCHAR(255),
                          updated_date TIMESTAMP
);

-- 创建 concepts_ancestors 表
CREATE TABLE concepts_ancestors (
                                    concept_id VARCHAR(255),
                                    ancestor_id VARCHAR(255)
);

-- 创建 concepts_counts_by_year 表
CREATE TABLE concepts_counts_by_year (
                                         concept_id VARCHAR(255) NOT NULL,
                                         year INT NOT NULL,
                                         works_count INT,
                                         cited_by_count INT,
                                         oa_works_count INT
);

-- 创建 concepts_ids 表
CREATE TABLE concepts_ids (
                              concept_id VARCHAR(255) NOT NULL,
                              openalex VARCHAR(255),
                              wikidata VARCHAR(255),
                              wikipedia VARCHAR(255),
                              umls_aui JSON,
                              umls_cui JSON,
                              mag BIGINT
);

-- 创建 concepts_related_concepts 表
CREATE TABLE concepts_related_concepts (
                                           concept_id VARCHAR(255),
                                           related_concept_id VARCHAR(255),
                                           score FLOAT
);

-- 创建 institutions 表
CREATE TABLE institutions (
                              id VARCHAR(255) NOT NULL,
                              ror VARCHAR(255),
                              display_name VARCHAR(255),
                              country_code VARCHAR(255),
                              type VARCHAR(255),
                              homepage_url VARCHAR(255),
                              image_url VARCHAR(255),
                              image_thumbnail_url VARCHAR(255),
                              display_name_acroynyms JSON,
                              display_name_alternatives JSON,
                              works_count INT,
                              cited_by_count INT,
                              works_api_url VARCHAR(255),
                              updated_date TIMESTAMP
);

-- 创建 institutions_associated_institutions 表
CREATE TABLE institutions_associated_institutions (
                                                      institution_id VARCHAR(255),
                                                      associated_institution_id VARCHAR(255),
                                                      relationship VARCHAR(255)
);

-- 创建 institutions_counts_by_year 表
CREATE TABLE institutions_counts_by_year (
                                             institution_id VARCHAR(255) NOT NULL,
                                             year INT NOT NULL,
                                             works_count INT,
                                             cited_by_count INT,
                                             oa_works_count INT
);

-- 创建 institutions_geo 表
CREATE TABLE institutions_geo (
                                  institution_id VARCHAR(255) NOT NULL,
                                  city VARCHAR(255),
                                  geonames_city_id VARCHAR(255),
                                  region VARCHAR(255),
                                  country_code VARCHAR(255),
                                  country VARCHAR(255),
                                  latitude FLOAT,
                                  longitude FLOAT
);

-- 创建 institutions_ids 表
CREATE TABLE institutions_ids (
                                  institution_id VARCHAR(255) NOT NULL,
                                  openalex VARCHAR(255),
                                  ror VARCHAR(255),
                                  grid VARCHAR(255),
                                  wikipedia VARCHAR(255),
                                  wikidata VARCHAR(255),
                                  mag BIGINT
);

-- 创建 publishers 表
CREATE TABLE publishers (
                            id VARCHAR(255) NOT NULL,
                            display_name VARCHAR(255),
                            alternate_titles JSON,
                            country_codes JSON,
                            hierarchy_level INT,
                            parent_publisher VARCHAR(255),
                            works_count INT,
                            cited_by_count INT,
                            sources_api_url VARCHAR(255),
                            updated_date TIMESTAMP
);

-- 创建 publishers_counts_by_year 表
CREATE TABLE publishers_counts_by_year (
                                           publisher_id VARCHAR(255) NOT NULL,
                                           year INT NOT NULL,
                                           works_count INT,
                                           cited_by_count INT,
                                           oa_works_count INT
);

-- 创建 publishers_ids 表
CREATE TABLE publishers_ids (
                                publisher_id VARCHAR(255),
                                openalex VARCHAR(255),
                                ror VARCHAR(255),
                                wikidata VARCHAR(255)
);

-- 创建 sources 表
CREATE TABLE sources (
                         id VARCHAR(255) NOT NULL,
                         issn_l VARCHAR(255),
                         issn JSON,
                         display_name VARCHAR(255),
                         publisher VARCHAR(255),
                         works_count INT,
                         cited_by_count INT,
                         is_oa BOOLEAN,
                         is_in_doaj BOOLEAN,
                         homepage_url VARCHAR(255),
                         works_api_url VARCHAR(255),
                         updated_date TIMESTAMP
);

-- 创建 sources_counts_by_year 表
CREATE TABLE sources_counts_by_year (
                                        source_id VARCHAR(255) NOT NULL,
                                        year INT NOT NULL,
                                        works_count INT,
                                        cited_by_count INT,
                                        oa_works_count INT
);

-- 创建 sources_ids 表
CREATE TABLE sources_ids (
                             source_id VARCHAR(255),
                             openalex VARCHAR(255),
                             issn_l VARCHAR(255),
                             issn JSON,
                             mag BIGINT,
                             wikidata VARCHAR(255),
                             fatcat VARCHAR(255)
);

-- 创建 works 表
CREATE TABLE works (
                       id VARCHAR(255) NOT NULL,
                       doi VARCHAR(255),
                       title TEXT,
                       display_name VARCHAR(255),
                       publication_year INT,
                       publication_date VARCHAR(255),
                       type VARCHAR(255),
                       cited_by_count INT,
                       is_retracted BOOLEAN,
                       is_paratext BOOLEAN,
                       cited_by_api_url VARCHAR(255),
                       abstract_inverted_index JSON
);

-- 创建 works_primary_locations 表
CREATE TABLE works_primary_locations (
                                         work_id VARCHAR(255),
                                         source_id VARCHAR(255),
                                         landing_page_url VARCHAR(255),
                                         pdf_url VARCHAR(255),
                                         is_oa BOOLEAN,
                                         version VARCHAR(255),
                                         license VARCHAR(255)
);

-- 创建 works_locations 表
CREATE TABLE works_locations (
                                 work_id VARCHAR(255),
                                 source_id VARCHAR(255),
                                 landing_page_url VARCHAR(255),
                                 pdf_url VARCHAR(255),
                                 is_oa BOOLEAN,
                                 version VARCHAR(255),
                                 license VARCHAR(255)
);

-- 创建 works_best_oa_locations 表
CREATE TABLE works_best_oa_locations (
                                         work_id VARCHAR(255),
                                         source_id VARCHAR(255),
                                         landing_page_url VARCHAR(255),
                                         pdf_url VARCHAR(255),
                                         is_oa BOOLEAN,
                                         version VARCHAR(255),
                                         license VARCHAR(255)
);

-- 创建 works_authorships 表
CREATE TABLE works_authorships (
                                   work_id VARCHAR(255),
                                   author_position VARCHAR(255),
                                   author_id VARCHAR(255),
                                   institution_id VARCHAR(255),
                                   raw_affiliation_string VARCHAR(255)
);

-- 创建 works_biblio 表
CREATE TABLE works_biblio (
                              work_id VARCHAR(255) NOT NULL,
                              volume VARCHAR(255),
                              issue VARCHAR(255),
                              first_page VARCHAR(255),
                              last_page VARCHAR(255)
);

-- 创建 works_concepts 表
CREATE TABLE works_concepts (
                                work_id VARCHAR(255),
                                concept_id VARCHAR(255),
                                score FLOAT
);

-- 创建 works_ids 表
CREATE TABLE works_ids (
                           work_id VARCHAR(255) NOT NULL,
                           openalex VARCHAR(255),
                           doi VARCHAR(255),
                           mag BIGINT,
                           pmid VARCHAR(255),
                           pmcid VARCHAR(255)
);

-- 创建 works_mesh 表
CREATE TABLE works_mesh (
                            work_id VARCHAR(255),
                            descriptor_ui VARCHAR(255),
                            descriptor_name VARCHAR(255),
                            qualifier_ui VARCHAR(255),
                            qualifier_name VARCHAR(255),
                            is_major_topic BOOLEAN
);

-- 创建 works_open_access 表
CREATE TABLE works_open_access (
                                   work_id VARCHAR(255) NOT NULL,
                                   is_oa BOOLEAN,
                                   oa_status VARCHAR(255),
                                   oa_url VARCHAR(255),
                                   any_repository_has_fulltext BOOLEAN
);

-- 创建 works_referenced_works 表
CREATE TABLE works_referenced_works (
                                        work_id VARCHAR(255),
                                        referenced_work_id VARCHAR(255)
);

-- 创建 works_related_works 表
CREATE TABLE works_related_works (
                                     work_id VARCHAR(255),
                                     related_work_id VARCHAR(255)
);

-- 添加主键约束

ALTER TABLE authors ADD PRIMARY KEY (id);
ALTER TABLE authors_counts_by_year ADD PRIMARY KEY (author_id, year);
ALTER TABLE authors_ids ADD PRIMARY KEY (author_id);
ALTER TABLE concepts ADD PRIMARY KEY (id);
ALTER TABLE concepts_ancestors ADD PRIMARY KEY (concept_id, ancestor_id);
ALTER TABLE concepts_counts_by_year ADD PRIMARY KEY (concept_id, year);
ALTER TABLE concepts_ids ADD PRIMARY KEY (concept_id);
ALTER TABLE institutions ADD PRIMARY KEY (id);
ALTER TABLE institutions_associated_institutions ADD PRIMARY KEY (institution_id, associated_institution_id);
ALTER TABLE institutions_counts_by_year ADD PRIMARY KEY (institution_id, year);
ALTER TABLE institutions_geo ADD PRIMARY KEY (institution_id);
ALTER TABLE institutions_ids ADD PRIMARY KEY (institution_id);
ALTER TABLE publishers ADD PRIMARY KEY (id);
ALTER TABLE publishers_counts_by_year ADD PRIMARY KEY (publisher_id, year);
ALTER TABLE publishers_ids ADD PRIMARY KEY (publisher_id);
ALTER TABLE sources ADD PRIMARY KEY (id);
ALTER TABLE sources_counts_by_year ADD PRIMARY KEY (source_id, year);
ALTER TABLE sources_ids ADD PRIMARY KEY (source_id);
ALTER TABLE works ADD PRIMARY KEY (id);
ALTER TABLE works_primary_locations ADD PRIMARY KEY (work_id, source_id);
ALTER TABLE works_locations ADD PRIMARY KEY (work_id, source_id);
ALTER TABLE works_best_oa_locations ADD PRIMARY KEY (work_id, source_id);
ALTER TABLE works_authorships ADD PRIMARY KEY (work_id, author_id);
ALTER TABLE works_biblio ADD PRIMARY KEY (work_id);
ALTER TABLE works_concepts ADD PRIMARY KEY (work_id, concept_id);
ALTER TABLE works_ids ADD PRIMARY KEY (work_id);
ALTER TABLE works_mesh ADD PRIMARY KEY (work_id);
ALTER TABLE works_open_access ADD PRIMARY KEY (work_id);
ALTER TABLE works_referenced_works ADD PRIMARY KEY (work_id);
ALTER TABLE works_related_works ADD PRIMARY KEY (work_id);


-- 创建索引 concepts_ancestors_concept_id_idx
CREATE INDEX concepts_ancestors_concept_id_idx ON concepts_ancestors (concept_id);

-- 创建索引 concepts_related_concepts_concept_id_idx
CREATE INDEX concepts_related_concepts_concept_id_idx ON concepts_related_concepts (concept_id);

-- 创建索引 concepts_related_concepts_related_concept_id_idx
CREATE INDEX concepts_related_concepts_related_concept_id_idx ON concepts_related_concepts (related_concept_id);

-- 创建索引 works_primary_locations_work_id_idx
CREATE INDEX works_primary_locations_work_id_idx ON works_primary_locations (work_id);

-- 创建索引 works_locations_work_id_idx
CREATE INDEX works_locations_work_id_idx ON works_locations (work_id);

-- 创建索引 works_best_oa_locations_work_id_idx
CREATE INDEX works_best_oa_locations_work_id_idx ON works_best_oa_locations (work_id);