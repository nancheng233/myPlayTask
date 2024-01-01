package com.jnu.student;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.student.myclass.Record;
import com.jnu.student.myclass.ShopItem;
import com.jnu.student.myclass.my_DateSave;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShoppingListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingListFragment extends Fragment {
    static String DataName = "shopItems.dat";
    ArrayList<ShopItem> shopItems = new ArrayList<>();
    ShopItemAdapter shopItemAdapter;
    int my_position;
    int my_case;
    MainActivity mainActivity;

    my_DateSave my_dateSave;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // 在这里处理返回的数据
                    if (data != null) {
                        String name = data.getStringExtra("name");
                        int count = data.getIntExtra("count", 0);
                        double price = data.getDoubleExtra("price", 0.0);
                        switch (my_case) {
                            case 1:
                                // 在这里使用这两个字符串
                                ShopItem shopItem = new ShopItem(name, count, price);
                                // 刷新RecyclerView
                                shopItems.add(shopItems.size(), shopItem);
                                shopItemAdapter.notifyItemInserted(shopItems.size() - 1);
                                break;
                            case 2:
                                shopItems.get(my_position).setName(name);
                                shopItems.get(my_position).setMax_count(count);
                                shopItems.get(my_position).setPrice(price);
                                shopItemAdapter.notifyItemChanged(my_position);
                                break;
                        }
                        my_dateSave.save_shop_item(shopItems, DataName);
                    }
                }
            });

    public static String getDataName() {
        return DataName;
    }

    public ArrayList<ShopItem> getShopItems() {
        return shopItems;
    }

    public ShopItemAdapter getShopItemAdapter() {
        return shopItemAdapter;
    }

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
        shopItems = my_dateSave.load_shop_item(DataName);
        if (shopItems.size() == 0){
            shopItems.add(new ShopItem("深蹲50个", 99,  10.5));
            shopItems.add(new ShopItem("跑步15分钟", 99, 15.0));
            shopItems.add(new ShopItem("俯卧撑30个", 99, 15.5));
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

            // 任务描述和最多次数
            holder.name.setText(currentItem.getName());
            String countText = holder.itemView.getContext().getString(R.string.count_format,
                    currentItem.getMy_count(), currentItem.getMax_count());
            holder.count.setText(countText);

            // 奖励或者代价
            double cost = currentItem.getPrice();
            if (cost > 0) {
                holder.price.setTextColor(Color.GREEN);
                String costFormat = getResources().getString(R.string.positive_cost);
                holder.price.setText(String.format(costFormat, cost));
            } else if (cost < 0) {
                holder.price.setTextColor(Color.RED);
                String costFormat = getResources().getString(R.string.negative_cost);
                holder.price.setText(String.format(costFormat, cost));
            }
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
            TextView name;
            TextView count;
            TextView price;
            CheckBox checkBox;


            public ShopItemViewHolder(View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.description);
                count = itemView.findViewById(R.id.count);
                price = itemView.findViewById(R.id.score);
                checkBox = itemView.findViewById(R.id.checkbox);

                // 监听按钮点击
                checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked && shopItems.get(getAdapterPosition()).getMy_count()
                            <= shopItems.get(getAdapterPosition()).getMax_count()) {
                        // CheckBox被选中且次数小于规定次数
                        // 设置列表里面的count次数
                        shopItems.get(getAdapterPosition()).addMy_count();
                        shopItemAdapter.notifyItemChanged(getAdapterPosition());
                        my_dateSave.save_shop_item(shopItems, DataName);

                        // 获取MainActivity实例并调用getValues()方法
                        mainActivity = (MainActivity) getActivity();
                        double value = mainActivity.getValue();

                        // 更新数据
                        ArrayList<Record> records = mainActivity.getRecords();
                        double its_value = shopItems.get(getAdapterPosition()).getPrice();
                        value += its_value;
                        Record record = new Record(new Date(System.currentTimeMillis()),
                                shopItems.get(getAdapterPosition()).getName(), its_value, value);

                        // 保存数据
                        mainActivity.setValue(value);
                        records.add(0, record);
                        mainActivity.save_record(records);
                    } else {
                        // CheckBox被取消选中
                    }
                });

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
                        my_case = 1;
                        Toast.makeText(context, "添加中...", Toast.LENGTH_SHORT).show();
                        activityResultLauncher.launch(intent);
                        break;
                    case 2:
                        my_case = 2;
                        Toast.makeText(context, "修改中...", Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putString("name", shopItems.get(my_position).getName());
                        bundle.putInt("count", shopItems.get(my_position).getMax_count());
                        bundle.putDouble("price", shopItems.get(my_position).getPrice());
                        intent.putExtras(bundle);
                        activityResultLauncher.launch(intent);
                        break;
                    case 3:
                        Toast.makeText(context, "删除中...", Toast.LENGTH_SHORT).show();
                        shopItems.remove(my_position);
                        shopItemAdapter.notifyItemRemoved(my_position);
                        my_dateSave.save_shop_item(shopItems, DataName);
                        break;
                }
                return false;
            }
        }
    }
}