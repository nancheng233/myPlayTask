package com.jnu.student;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jnu.student.myclass.ShopItem;
import com.jnu.student.myclass.my_DateSave;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShoppingListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingListFragment extends Fragment {
    ArrayList<ShopItem> shopItems = new ArrayList<>();
    ShopItemAdapter shopItemAdapter;
    int my_position;
    int my_cass;

    my_DateSave my_dateSave;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // 在这里处理返回的数据
                    if (data != null) {
                        String name = data.getStringExtra("name");
                        double price = data.getDoubleExtra("price", 0.0);
                        switch (my_cass) {
                            case 1:
                                // 在这里使用这两个字符串
                                ShopItem shopItem = new ShopItem(shopItems.get(my_position).getImageResource(), name, price);
                                // 刷新RecyclerView
                                shopItems.add(shopItems.size(), shopItem);
                                shopItemAdapter.notifyItemInserted(shopItems.size() - 1);
                                my_dateSave.save(shopItems);
                                break;
                            case 2:
                                shopItems.get(my_position).setName(name);
                                shopItems.get(my_position).setPrice(price);
                                shopItemAdapter.notifyItemChanged(my_position);
                                my_dateSave.save(shopItems);
                                break;
                        }
                        my_dateSave.save(shopItems);
                    }
                }
            });

    public ShoppingListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ShoppingListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShoppingListFragment newInstance() {
        ShoppingListFragment fragment = new ShoppingListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        // 填充shopItems列表...
        my_dateSave = new my_DateSave(requireActivity());
        shopItems = my_dateSave.load();
        if (shopItems.size() == 0){
            shopItems.add(new ShopItem(R.drawable.bai_cai, "白菜", 1));
            shopItems.add(new ShopItem(R.drawable.luo_bo, "萝卜", 2));
            shopItems.add(new ShopItem(R.drawable.tu_dou, "土豆", 3));
        }
        shopItemAdapter = new ShopItemAdapter(shopItems);
        recyclerView.setAdapter(shopItemAdapter);

        return rootView;
    }

    public class ShopItemAdapter extends RecyclerView.Adapter<ShopItemAdapter.ShopItemViewHolder> {
        ArrayList<ShopItem> shopItems;


        public ShopItemAdapter(ArrayList<ShopItem> shopItems) {
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
            holder.price.setText(String.valueOf(currentItem.getPrice()));
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
                my_position = getAdapterPosition();
                Context context = itemView.getContext();
                Intent intent = new Intent(context, forShopItem.class);
                switch (menuItem.getItemId()) {
                    case 1:
                        my_cass = 1;
                        Toast.makeText(context, "添加中...", Toast.LENGTH_SHORT).show();
                        activityResultLauncher.launch(intent);
                        break;
                    case 2:
                        my_cass = 2;
                        Toast.makeText(context, "修改中...", Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putString("name", shopItems.get(my_position).getName());
                        bundle.putDouble("price", shopItems.get(my_position).getPrice());
                        intent.putExtras(bundle);
                        activityResultLauncher.launch(intent);
                        break;
                    case 3:
                        Toast.makeText(context, "删除中...", Toast.LENGTH_SHORT).show();
                        shopItems.remove(my_position);
                        shopItemAdapter.notifyItemRemoved(my_position);
                        my_dateSave.save(shopItems);
                        break;
                }
                return false;
            }
        }
    }
}