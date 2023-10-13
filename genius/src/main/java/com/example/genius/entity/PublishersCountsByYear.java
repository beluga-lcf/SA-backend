package com.example.genius.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
@TableName("publishers_counts_by_year")
@ApiModel(value = "PublishersCountsByYear对象", description = "")
public class PublishersCountsByYear implements Serializable {

    private static final long serialVersionUID = 1L;

    private String publisherId;

    private Integer year;

    private Integer worksCount;

    private Integer citedByCount;

    private Integer oaWorksCount;

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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
    public Integer getOaWorksCount() {
        return oaWorksCount;
    }

    public void setOaWorksCount(Integer oaWorksCount) {
        this.oaWorksCount = oaWorksCount;
    }

    @Override
    public String toString() {
        return "PublishersCountsByYear{" +
            "publisherId=" + publisherId +
            ", year=" + year +
            ", worksCount=" + worksCount +
            ", citedByCount=" + citedByCount +
            ", oaWorksCount=" + oaWorksCount +
        "}";
    }
}
