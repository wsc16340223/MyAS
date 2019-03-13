package com.group33;

import java.lang.String;
import java.io.Serializable;

public class Heros implements Serializable{
    //英雄称号
    private String heroTitle;
    //英雄名字
    private String heroName;
    //英雄职业
    private String heroJob;
    //生存能力，取值1-5
    private int heroLive;
    //攻击伤害，取值1-5
    private int heroAttack;
    //技能效果，取值1-5
    private int heroSkill;
    //上手难度，取值1-5
    private int heroLevel;
    //人气指数，取值1-5
    private int heroPop;
    //建议路线
    private String adviceWay;
    public Heros()
    {
        heroTitle = "";
        heroName = "";
        heroJob = "";
        heroLive = 1;
        heroAttack = 1;
        heroSkill = 1;
        heroLevel = 1;
        heroPop = 1;
        adviceWay = "上路" ;
    }
    public Heros(String title, String name, String job, int live, int attack, int skill, int level, int pop, String way)
    {
        heroTitle = title;
        heroName = name;
        heroJob = job;
        heroLive = live;
        heroAttack = attack;
        heroSkill = skill;
        heroLevel = level;
        heroPop = pop;
        adviceWay = way;
    }
    void setHeroTitle(String title) {heroTitle = title;}
    void setHeroName(String name) {heroName = name;}
    void setHeroJob(String job) {heroJob = job;}
    void setHeroLive(int live) {heroLive = live;}
    void setHeroAttack(int attack) {heroAttack = attack;}
    void setHeroSkill(int skill) {heroSkill = skill;}
    void setHeroLevel(int level) {heroLevel = level;}
    void setHeroPop(int pop) {heroPop = pop;}
    void setAdviceWay(String way) {adviceWay = way;}

    String getHeroTitle() {return heroTitle;}
    String getHeroName() {return heroName;}
    String getHeroJob() {return heroJob;}
    int getHeroLive() {return heroLive;}
    int getHeroAttack() {return heroAttack;}
    int getHeroSkill() {return heroSkill;}
    int getHeroLevel() {return heroLevel;}
    int getHeroPop() {return  heroPop;}
    String getAdviceWay() {return adviceWay;}
}
