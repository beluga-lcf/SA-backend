package com.example.generated.entity;

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
 * @since 2023-11-11
 */
@TableName("openalex.works_biblio")
@ApiModel(value = "WorksBiblio对象", description = "")
public class WorksBiblio implements Serializable {

    private static final long serialVersionUID = 1L;

    private String workId;

    private String volume;

    private String issue;

    private String firstPage;

    private String lastPage;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(String firstPage) {
        this.firstPage = firstPage;
    }

    public String getLastPage() {
        return lastPage;
    }

    public void setLastPage(String lastPage) {
        this.lastPage = lastPage;
    }

    @Override
    public String toString() {
        return "WorksBiblio{" +
            "workId = " + workId +
            ", volume = " + volume +
            ", issue = " + issue +
            ", firstPage = " + firstPage +
            ", lastPage = " + lastPage +
        "}";
    }
}
