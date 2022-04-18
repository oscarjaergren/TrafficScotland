/*
 Developer: Oscar Jargren
 Student ID: S1805227
*/

package com.example.trafficscotland.Models;

import java.util.Calendar;
import java.util.Date;

public class TrafficData {

    private final String title;
    private final String description;
    private final String link;
    private final String georss;
    private final String author;
    private Date startDate;
    private Date endDate;
    private final String comments;
    private final String pubDate;
    private String shortStartDate;
    private String shortEndDate;
    private Long roadWorkLength;

    public TrafficData(String title, String description, String link, String georss, String author,
                       String comments,
                       String pubDate,
                       Date startDate,
                       Date endDate,
                       String shortStartDate,
                       String shortEndDate,
                       Long roadWorkLength) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.georss = georss;
        this.author = author;
        this.startDate = startDate;
        this.endDate = endDate;
        this.comments = comments;
        this.pubDate = pubDate;
        this.shortStartDate = shortStartDate;
        this.shortEndDate = shortEndDate;
        this.roadWorkLength = roadWorkLength;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getGeorss() {
        return georss;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public String getComments() {
        return comments;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getShortEndDate() {
        return shortEndDate;
    }

    public Long getRoadWorkLength() {
        return roadWorkLength;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getStartDate() {
        return startDate;
    }
}
