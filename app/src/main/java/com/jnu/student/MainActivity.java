package com.jnu.student;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.student.myclass.ShopItem;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    List<ShopItem> shopItems = new ArrayList<>();
    ShopItemAdapter shopItemAdapter = new ShopItemAdapter(shopItems);

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // 在这里处理返回的数据
                    if (data != null) {
                        String string1 = data.getStringExtra("name");
                        String string2 = data.getStringExtra("price");
                        // 在这里使用这两个字符串
                        ShopItem shopItem = new ShopItem(R.drawable.bai_cai, string1, string2);
                        // 刷新RecyclerView
                        shopItems.add(shopItems.size(), shopItem);
                        shopItemAdapter.notifyItemInserted(shopItems.size() - 1);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shopItems.add(new ShopItem(R.drawable.bai_cai, "白菜", "1"));
        shopItems.add(new ShopItem(R.drawable.luo_bo, "萝卜", "2"));
        shopItems.add(new ShopItem(R.drawable.tu_dou, "土豆", "3"));

        // 填充shopItems列表...
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(shopItemAdapter);

        // 在Activity中注册需要上下文菜单的View
        registerForContextMenu(recyclerView);
    }


    // RecyclerView的Adapter
    public class ShopItemAdapter extends RecyclerView.Adapter<ShopItemAdapter.ShopItemViewHolder> {
        List<ShopItem> shopItems;


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
            holder.name.setText(currentItem.getName());
            holder.price.setText(currentItem.getPrice());
        }


        @ Override
        public int getItemCount() {
            return shopItems.size();
        }


        // 这是你自定义的ViewHolder类。每个列表项在屏幕上都由一个ViewHolder对象表示。
        // 当创建一个新的ViewHolder时，它并没有任何关联的数据。
        // 当RecyclerView准备将它显示在屏幕上时，就会调用上面提到的onBindViewHolder()方法，将数据绑定到这个ViewHolder。
        public class ShopItemViewHolder extends RecyclerView.ViewHolder
                implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
            ImageView imageView;
            TextView name;
            TextView price;


            public ShopItemViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageView);
                name = itemView.findViewById(R.id.name);
                price = itemView.findViewById(R.id.price);

                // 启用长按点击监听
                itemView.setOnCreateContextMenuListener(this);
            }

            @Override
            // 长按后生成菜单
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                // 设置介绍项
                contextMenu.setHeaderTitle("操作");

                // 创建菜单项
                MenuItem append = contextMenu.add(contextMenu.NONE, 1, 1, "增加");
                MenuItem edit = contextMenu.add(contextMenu.NONE, 2, 2, "修改");
                MenuItem delete = contextMenu.add(contextMenu.NONE, 3, 3, "删除");

                // 为菜单项启用点击监听
                append.setOnMenuItemClickListener(this);
                edit.setOnMenuItemClickListener(this);
                delete.setOnMenuItemClickListener(this);
            }

            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                int position = getAdapterPosition();
                Context context = itemView.getContext();
                Intent intent = new Intent(context, forShopItem.class);
                switch (menuItem.getItemId()) {
                    case 1:
                        Toast.makeText(context, "添加中...", Toast.LENGTH_SHORT).show();
                        activityResultLauncher.launch(intent);
                        break;
                    case 2:
                        Toast.makeText(context, "修改中...", Toast.LENGTH_SHORT).show();
                        activityResultLauncher.launch(intent);
                        break;
                    case 3:
                        Toast.makeText(context, "删除中...", Toast.LENGTH_SHORT).show();
                        shopItems.remove(position);
                        shopItemAdapter.notifyItemRemoved(position);
                        break;
                }
                return false;
            }
        }
    }
}



