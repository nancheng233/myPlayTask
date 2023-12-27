package com.jnu.student.myclass;

import java.io.Serializable;

public class ShopItem implements Serializable {
    // 序列化需要的UID值
    private static final long serialVersionUID = 1226L;
    private String name;
    private int max_count;
    private int my_count;
    private double price;

    public ShopItem(String name, int max_count, double price) {
        this.name = name;
        this.max_count = max_count;
        this.my_count = 0;
        this.price = price;
    }


    public String getName() {
        return name;
    }

    public int getMax_count() {
        return max_count;
    }

    public int getMy_count() {
        return my_count;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMax_count(int max_count) {
        this.max_count = max_count;
    }

    public void addMy_count() {
        this.my_count += 1;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
}
