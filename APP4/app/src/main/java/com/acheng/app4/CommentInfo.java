package com.acheng.app4;

import java.io.Serializable;

public class CommentInfo implements Serializable {

    private String name;
    private String time;
    private String comment;
    private int likeCount;
    public CommentInfo()
    {
        name = "";
        time = "";
        comment = "";
        likeCount = 0;
    }

    public CommentInfo(String na, String ti, String co, int num)
    {
        name = na;
        time  = ti;
        comment = co;
        likeCount = num;
    }

    void setName(String na) {name = na;}
    void setTime(String ti) {time = ti;}
    void setComment(String co) {comment = co;}
    void setLikeCount(int n) {likeCount = n;}

    String getName() {return name;}
    String getTime() {return time;}
    String getComment() {return comment;}
    int getLikeCount() {return likeCount;}
}
