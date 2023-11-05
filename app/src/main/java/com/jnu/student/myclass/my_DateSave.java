package com.jnu.student.myclass;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class my_DateSave {
    Context context;
    public my_DateSave(Context context) {
        this.context = context;
    }

    public void save (ArrayList<ShopItem> data) {
        try {
            FileOutputStream fos = context.openFileOutput("shopItems.dat", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
            oos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public ArrayList<ShopItem> load () {
        ArrayList<ShopItem> data = new ArrayList<>();
        try {
            FileInputStream fis = context.openFileInput("shopItems.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            data = (ArrayList<ShopItem>) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }
}
