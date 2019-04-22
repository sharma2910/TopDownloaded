package com.example.darsh.topdownloaded;

public class FeedEntry {
    private String name ;
    private String summary ;
    private String artist;
    private String releseDate ;
    private String imgUrl;

    public void setName(String name) {
        this.name = name;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setReleseDate(String releseDate) {
        this.releseDate = releseDate;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public String getSummary() {
        return summary;
    }

    public String getArtist() {
        return artist;
    }

    public String getReleseDate() {
        return releseDate;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    @Override
    public String toString() {
        return "FeedEntry{" +
                "name= " + name + '\'' +
                ", artist= " + artist + '\'' +
                ", releseDate= " + releseDate + '\'' +
                ", imgUrl= " + imgUrl + '\'' +
                '}';
    }
}
