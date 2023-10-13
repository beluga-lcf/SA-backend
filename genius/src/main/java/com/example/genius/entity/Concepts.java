package com.example.genius.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author chaofan
 * @since 2023-10-13
 */
@ApiModel(value = "Concepts对象", description = "")
public class Concepts implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String wikidata;

    private String displayName;

    private Integer level;

    private String description;

    private Integer worksCount;

    private Integer citedByCount;

    private String imageUrl;

    private String imageThumbnailUrl;

    private String worksApiUrl;

    private LocalDateTime updatedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getWikidata() {
        return wikidata;
    }

    public void setWikidata(String wikidata) {
        this.wikidata = wikidata;
    }
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getWorksCount() {
        return worksCount;
    }

    public void setWorksCount(Integer worksCount) {
        this.worksCount = worksCount;
    }
    public Integer getCitedByCount() {
        return citedByCount;
    }

    public void setCitedByCount(Integer citedByCount) {
        this.citedByCount = citedByCount;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getImageThumbnailUrl() {
        return imageThumbnailUrl;
    }

    public void setImageThumbnailUrl(String imageThumbnailUrl) {
        this.imageThumbnailUrl = imageThumbnailUrl;
    }
    public String getWorksApiUrl() {
        return worksApiUrl;
    }

    public void setWorksApiUrl(String worksApiUrl) {
        this.worksApiUrl = worksApiUrl;
    }
    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "Concepts{" +
            "id=" + id +
            ", wikidata=" + wikidata +
            ", displayName=" + displayName +
            ", level=" + level +
            ", description=" + description +
            ", worksCount=" + worksCount +
            ", citedByCount=" + citedByCount +
            ", imageUrl=" + imageUrl +
            ", imageThumbnailUrl=" + imageThumbnailUrl +
            ", worksApiUrl=" + worksApiUrl +
            ", updatedDate=" + updatedDate +
        "}";
    }
}
