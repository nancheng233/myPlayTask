package com.jnu.student;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jnu.student.myclass.ShopItem;
import com.jnu.student.myclass.my_DateSave;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private String[] tabHeaderStrings = {"每日任务", "每周任务", "普通任务", "副本任务"};
    String dataName;
    int my_position;
    int fragment_position;
    ShoppingListFragment shoppingListFragment;
    BaiduMapFragment baiduMapFragment;
    WebViewFragment webViewFragment;
    CopyTaskFragment copyTaskFragment;
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
                        // 在这里使用这两个字符串
                        ShopItem shopItem = new ShopItem(name, count, price);

                        ArrayList<ShopItem> shopItems;
                        // 是否加载页面
                        if (my_position <= fragment_position) {
                            // 刷新RecyclerView
                            switch (my_position) {
                                case 0:
                                    // 确定列表
                                    shopItems = shoppingListFragment.getShopItems();
                                    ShoppingListFragment.ShopItemAdapter shopItemAdapter =
                                            shoppingListFragment.getShopItemAdapter();

                                    // 更新界面
                                    shopItems.add(shopItems.size(), shopItem);
                                    shopItemAdapter.notifyItemInserted(shopItems.size() - 1);

                                    // 保存数据
                                    my_dateSave.save_shop_item(shopItems, dataName);
                                    break;
                                case 1:
                                    // 确定列表
                                    shopItems = baiduMapFragment.getShopItems();
                                    BaiduMapFragment.ShopItemAdapter baidu_shopItemAdapter =
                                            baiduMapFragment.getShopItemAdapter();

                                    // 更新界面
                                    shopItems.add(shopItems.size(), shopItem);
                                    baidu_shopItemAdapter.notifyItemInserted(shopItems.size() - 1);

                                    // 保存数据
                                    my_dateSave.save_shop_item(shopItems, dataName);
                                    break;
                                case 2:
                                    // 确定列表
                                    shopItems = webViewFragment.getShopItems();
                                    WebViewFragment.ShopItemAdapter web_shopItemAdapter =
                                            webViewFragment.getShopItemAdapter();

                                    // 更新界面
                                    shopItems.add(shopItems.size(), shopItem);
                                    web_shopItemAdapter.notifyItemInserted(shopItems.size() - 1);

                                    // 保存数据
                                    my_dateSave.save_shop_item(shopItems, dataName);
                                    break;
                                case 3:
                                    // 确定列表
                                    shopItems = copyTaskFragment.getShopItems();
                                    CopyTaskFragment.ShopItemAdapter copy_shopItemAdapter =
                                            copyTaskFragment.getShopItemAdapter();

                                    // 更新界面
                                    shopItems.add(shopItems.size(), shopItem);
                                    copy_shopItemAdapter.notifyItemInserted(shopItems.size() - 1);

                                    // 保存数据
                                    my_dateSave.save_shop_item(shopItems, dataName);
                                    break;
                            }
                        }
                        // 未加载页面
                        else {
                            // 获得先前数据
                            shopItems = my_dateSave.load_shop_item(dataName);
                            // 添加新的数据
                            shopItems.add(shopItems.size(), shopItem);
                            // 保存数据
                            my_dateSave.save_shop_item(shopItems, dataName);
                        }
                    }
                }
            });

    public TaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskFragment newInstance() {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_task, container, false);

        // 保存数据的类
        my_dateSave = new my_DateSave(requireActivity());

        // 获取ViewPager2和TabLayout的实例
        ViewPager2 viewPager = rootView.findViewById(R.id.view_pager);
        TabLayout tabLayout = rootView.findViewById(R.id.tab_layout);
        // 创建适配器
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getChildFragmentManager(),
                getViewLifecycleOwner().getLifecycle());
        viewPager.setAdapter(fragmentAdapter);

        // 将TabLayout和ViewPager2进行关联
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabHeaderStrings[position])
        ).attach();

        // 添加按钮及其点击监听
        FloatingActionButton fab = rootView.findViewById(R.id.add_fab);
        fab.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(rootView.getContext(), view);
            popupMenu.getMenuInflater().inflate(R.menu.menu_add_task, popupMenu.getMenu());

            // 添加菜单监听
            popupMenu.setOnMenuItemClickListener(item -> {
                Context context = rootView.getContext();
                Intent intent = new Intent(context, forShopItem.class);
                if (item.getItemId() == R.id.add_daily_task) {
                    // 添加每日任务
                    Toast.makeText(context, "添加中...", Toast.LENGTH_SHORT).show();
                    my_position = 0;
                    dataName = ShoppingListFragment.getDataName();
                    activityResultLauncher.launch(intent);
                } else if (item.getItemId() == R.id.add_weekly_task) {
                    // 添加每周任务
                    Toast.makeText(context, "添加中...", Toast.LENGTH_SHORT).show();
                    my_position = 1;
                    dataName = BaiduMapFragment.getDataName();
                    activityResultLauncher.launch(intent);
                } else if (item.getItemId() == R.id.add_normal_task) {
                    // 添加普通任务
                    Toast.makeText(context, "添加中...", Toast.LENGTH_SHORT).show();
                    my_position = 2;
                    dataName = WebViewFragment.getDataName();
                    activityResultLauncher.launch(intent);
                } else if (item.getItemId() == R.id.add_copy_task) {
                    // 添加副本任务
                    Toast.makeText(context, "添加中...", Toast.LENGTH_SHORT).show();
                    my_position = 3;
                    dataName = CopyTaskFragment.getDataName();
                    activityResultLauncher.launch(intent);
                } else {
                    return false;
                }
                return true;
            });
            popupMenu.show();
        });

        // 按钮的拖动监听
        fab.setOnTouchListener(new View.OnTouchListener() {
            private float initialX, initialY;
            private int initialTouchX, initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = fab.getX();
                        initialY = fab.getY();
                        initialTouchX = (int) event.getRawX();
                        initialTouchY = (int) event.getRawY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        int dx = (int) event.getRawX() - initialTouchX;
                        int dy = (int) event.getRawY() - initialTouchY;
                        fab.setX(initialX + dx);
                        fab.setY(initialY + dy);
                        return true;
                    case MotionEvent.ACTION_UP:
                        // 当用户放开按钮时，根据按钮的位置决定靠左边界还是右边界
                        DisplayMetrics displayMetrics = new DisplayMetrics();
                        Activity activity = getActivity();
                        if (activity != null) {
                            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                            int screenWidth = displayMetrics.widthPixels;
                            if (fab.getX() < screenWidth / 2.0f) {
                                // 靠左边界
                                fab.animate().x(0).setDuration(100).start();
                            } else {
                                // 靠右边界
                                fab.animate().x(screenWidth - fab.getWidth()).setDuration(100).start();
                            }
                        }
                        return v.performClick();  // 在这里调用performClick
                    default:
                        return false;
                }
            }
        });

        return rootView;
    }

    public class FragmentAdapter extends FragmentStateAdapter {
        private static final int NUM_TABS = 4;
        public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            // 根据位置返回对应的Fragment实例
            fragment_position = position;
            switch (position) {
                case 0:
                    shoppingListFragment = new ShoppingListFragment();
                    return shoppingListFragment;
                case 1:
                    baiduMapFragment = new BaiduMapFragment();
                    return baiduMapFragment;
                case 2:
                    webViewFragment = new WebViewFragment();
                    return webViewFragment;
                case 3:
                    copyTaskFragment = new CopyTaskFragment();
                    return copyTaskFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return NUM_TABS;
        }
    }

}