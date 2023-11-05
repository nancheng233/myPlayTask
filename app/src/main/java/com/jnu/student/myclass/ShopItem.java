package com.jnu.student.myclass;

import java.io.Serializable;

public class ShopItem implements Serializable {
    // 序列化需要的UID值
    private static final long serialVersionUID = 114514L;
    private int imageResource;
    private String name;
    private double price;

    public ShopItem(int imageResource, String name, double price) {
        this.imageResource = imageResource;
        this.name = name;
        this.price = price;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getName() {
        return name;
    }

    public double getPrice(){
        return price;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
}
