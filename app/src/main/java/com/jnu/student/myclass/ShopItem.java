package com.jnu.student.myclass;

public class ShopItem {
    private final int imageResource;
    private final String name;
    private final String price;

    public ShopItem(int imageResource, String name, String price) {
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

    public String getPrice(){
        return price;
    }
}
