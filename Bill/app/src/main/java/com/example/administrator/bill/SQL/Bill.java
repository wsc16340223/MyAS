package com.example.administrator.bill.SQL;

public class Bill {
    private int id, style;
    private float money;
    private String time, tip, type;
    private byte[] pic;

    public Bill(int id, String type, float money, String time, String tip, int style) {
        this.id = id;
        this.type = type;
        this.money = money;
        this.time = time;
        this.tip = tip;
        this.style = style;
        this.pic = null;
    }

    public Bill(int id, String type, float money, String time, String tip, int style, byte[] pic) {
        this.id = id;
        this.type = type;
        this.money = money;
        this.time = time;
        this.tip = tip;
        this.style = style;
        this.pic = pic;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public float getMoney() { return money; }

    public String getTime() {
        return time;
    }

    public String getTip() {
        return tip;
    }

    public int getStyle() {
        return style;
    }

    public byte[] getPic() {
        return pic;
    }
}
