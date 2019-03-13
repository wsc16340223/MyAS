package com.acheng.app5;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class MyObject {
    private Boolean status;
    private MyData myData;
    private ArrayList<MyImage> myImages;

    public static class MyImage{
        private Bitmap bitmap;
        private int index;
        public MyImage(Bitmap bit, int i){
            bitmap = bit;
            index = i;
        }
        public Bitmap getBitmap(){return bitmap;}
        public void setBitmap(Bitmap bit){bitmap = bit;}
        public int getIndex(){return index;}
        public void setIndex(int i){index = i;}
    }

    public static class MyData{
        private String av;
        private String title;
        private String playNum;
        private String commentNum;
        private String duration;
        private String createTime;
        private String resume;
        private Bitmap image;

        public MyData(String av, String title, String playNum, String commentNum, String duration, String createTime, String resume, Bitmap image){
            this.av = av;
            this.title = title;
            this.playNum = playNum;
            this.commentNum = commentNum;
            this.duration = duration;
            this.createTime = createTime;
            this.resume = resume;
            this.image = image;
        }
        public String getAv(){return av;}
        public void setAv(String av){this.av = av;}
        public String getTitle(){return title;}
        public void setTitle(String title){this.title = title;}
        public String getPlayNum(){return playNum;}
        public void setPlayNum(String playNum){this.playNum = playNum;}
        public String getCommentNum(){return commentNum;}
        public void setCommentNum(String commentNum){this.commentNum = commentNum;}
        public String getDuration(){return duration;}
        public void setDuration(String duration){this.duration = duration;}
        public String getCreateTime(){return createTime;}
        public void setCreateTime(String createTime){this.createTime = createTime;}
        public String getResume(){return resume;}
        public void setResume(String resume){this.resume = resume;}
        public Bitmap getImage(){return image;}
        public void setImage(Bitmap image) { this.image = image; }
    }

    public Boolean getStatus(){return status;}
    public void setStatus(Boolean status){this.status = status;}
    public MyData getMyData(){return myData;}
    public void setMyData(MyData myData){this.myData = myData;}
    public ArrayList<MyImage> getMyImages() { return myImages; }
    public void setMyImages(ArrayList<MyImage> myImages){this.myImages = myImages;}
}
