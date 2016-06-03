package com.walmart.gtulla.nyts.model;

import java.io.Serializable;

/**
 * Created by gtulla on 6/1/16.
 */
public class SearchFilterOptions implements Serializable {
    private String createdDate;
    private String endDate;
    private String newsCategory;
    public SearchFilterOptions(){
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getNewsCategory() {
        return newsCategory;
    }

    public void setNewsCategory(String newsCategory) {
        this.newsCategory = newsCategory;
    }


}
