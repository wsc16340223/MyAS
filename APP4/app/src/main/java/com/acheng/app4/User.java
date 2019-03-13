package com.acheng.app4;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String pass;
    public User()
    {
        name = "";
        pass = "";
    }
    public User(String na, String pa)
    {
        name = na;
        pass = pa;
    }
    String getName() {return name;}
    String getPass() {return pass;}
}
