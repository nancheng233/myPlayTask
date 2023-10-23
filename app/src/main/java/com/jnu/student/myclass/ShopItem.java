package com.jnu.student.myclass;

public class ShopItem {
    private final int imageResource;
    private final String text;

    public ShopItem(int imageResource, String text) {
        this.imageResource = imageResource;
        this.text = text;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getText() {
        return text;
    }
}
