package com.jnu.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.student.myclass.ShopItem;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<ShopItem> shopItems = new ArrayList<>();

        shopItems.add(new ShopItem(R.drawable.bai_cai, "白菜"));
        shopItems.add(new ShopItem(R.drawable.luo_bo, "萝卜"));
        shopItems.add(new ShopItem(R.drawable.tu_dou, "土豆"));
        shopItems.add(new ShopItem(R.drawable.tu_dou, "土豆"));
        shopItems.add(new ShopItem(R.drawable.tu_dou, "土豆"));
        shopItems.add(new ShopItem(R.drawable.tu_dou, "土豆"));
        shopItems.add(new ShopItem(R.drawable.tu_dou, "土豆"));
        shopItems.add(new ShopItem(R.drawable.tu_dou, "土豆"));
        shopItems.add(new ShopItem(R.drawable.tu_dou, "土豆"));

        // 填充shopItems列表...
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ShopItemAdapter(shopItems));


    }
}


class ShopItemAdapter extends RecyclerView.Adapter<ShopItemAdapter.ShopItemViewHolder> {
    private final List<ShopItem> shopItems;

    public ShopItemAdapter(List<ShopItem> shopItems) {
        this.shopItems = shopItems;
    }

    @ Override
    @ NonNull
    // 这个方法是当RecyclerView需要创建新的列表项（即一个新的ViewHolder）时会被调用。
    // 这个方法会创建并初始化ViewHolder及其关联的视图，但不会填充视图的内容，因为ViewHolder此时尚未绑定到具体数据。
    public ShopItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.for_recycle_view, parent, false);
        return new ShopItemViewHolder(view);
    }

    @ Override
    // 这个方法是当RecyclerView需要将ViewHolder与数据进行绑定时会被调用。
    public void onBindViewHolder(ShopItemViewHolder holder, int position) {
        ShopItem currentItem = shopItems.get(position);
        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.textView.setText(currentItem.getText());
    }

    @ Override
    public int getItemCount() {
        return shopItems.size();
    }

    // 这是你自定义的ViewHolder类。每个列表项在屏幕上都由一个ViewHolder对象表示。
    // 当创建一个新的ViewHolder时，它并没有任何关联的数据。
    // 当RecyclerView准备将它显示在屏幕上时，就会调用上面提到的onBindViewHolder()方法，将数据绑定到这个ViewHolder。
    public static class ShopItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;

        public ShopItemViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
