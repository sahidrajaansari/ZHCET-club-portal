package com.example.zcm.dataholder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Postdata {
    private String date;
    private String postId;
    private String postImage;
    private String postedBy;
    private String postDescription;
    private Long postedAt;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Postdata(String postId, String postImage, String postedBy, String postDescription, Long postedAt, String date) {
        this.date = date;
        this.postId = postId;
        this.postImage = postImage;
        this.postedBy = postedBy;
        this.postDescription = postDescription;
        this.postedAt = postedAt;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public Long getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(Long postedAt) {
        this.postedAt = postedAt;
    }

    public Postdata() {
    }

}
