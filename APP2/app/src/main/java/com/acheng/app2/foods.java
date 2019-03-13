package com.acheng.app2;

import java.lang.String;
import java.io.Serializable;

public class foods implements Serializable {
    private String name;
    private String kName;
    private String kind;
    private String nutrition;
    private String color;
    private Boolean isCollect;
    public foods()
    {
        name = "";
        kName = "";
        kind = "";
        nutrition = "";
        color = "";
        isCollect = false;
    }
    public foods(String name, String kName, String kind, String nutrition, String color)
    {
        this.name = name;
        this.kName = kName;
        this.kind = kind;
        this.nutrition = nutrition;
        this.color = color;
        this.isCollect = false;
    }
    void setAll(String name, String kName, String kind, String nutrition, String color)
    {
        this.name = name;
        this.kName = kName;
        this.kind = kind;
        this.nutrition = nutrition;
        this.color = color;
        this.isCollect = false;
    }
    String getName(){return name;}
    String getkName() {return kName;}
    String getKind(){return kind;}
    String getNutrition(){return nutrition;}
    String getColor(){return color;}
    Boolean getCollect() {return  isCollect;}

    void setName(String name){this.name = name;}
    void setkName(String kName) {this.kName = kName;}
    void setKind(String kind){this.kind = kind;}
    void setNutrition(String nutrition){this.nutrition = nutrition;}
    void setColor(String color){this.color = color;}
    void setCollect(Boolean isCollect){this.isCollect = isCollect;}
}
