package com.bins.bean;

public class NewsQuery {
    private String title;
    private String typeId;
    private boolean recommend;

    public String getTitle() {
        return title;
    }

    public String getTypeId() {
        return typeId;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    @Override
    public String toString() {
        return "NewsQuery{" +
                "title='" + title + '\'' +
                ", typeId='" + typeId + '\'' +
                ", recommend=" + recommend +
                '}';
    }
}
