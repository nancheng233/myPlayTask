package com.jnu.student;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jnu.student.myclass.Record;
import com.jnu.student.myclass.my_DateSave;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private String recordString = "records.dat";
    private double value = 0;
    private ArrayList<Record> records = new ArrayList<>();
    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigationView;
    StatisticsFragment statisticsFragment;
    my_DateSave myDateSave;
    public void save_record(ArrayList<Record> my_record) {
        myDateSave.save_Record(my_record, recordString);

        // 更新账本
        if(statisticsFragment != null) {
            statisticsFragment.getShopItemAdapter().notifyItemInserted(0);
            statisticsFragment.setToken_num(value);
        }
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public ArrayList<Record> getRecords() {
        return records;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获得数据
        myDateSave = new my_DateSave(this);
        records = myDateSave.load_Record(recordString);
        if(records.size() != 0) {
            value = records.get(records.size() - 1).getNow_value();
        }

        // 获取各个组件
        viewPager = findViewById(R.id.view_pager);
        bottomNavigationView = findViewById(R.id.nav_view);

        // 设置ViewPager的Adapter
        viewPager.setAdapter(new ViewPagerAdapter(this));

        // 设置BottomNavigationView的选择监听
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.navigation_item1) {
                viewPager.setCurrentItem(0);
                return true;
            } else if (id == R.id.navigation_item2) {
                viewPager.setCurrentItem(1);
                return true;
            } else if (id == R.id.navigation_item3) {
                viewPager.setCurrentItem(2);
                return true;
            } else if (id == R.id.navigation_item4) {
                viewPager.setCurrentItem(3);
                return true;
            }
            return false;
        });
    }


    // ViewPagerAdapter.java
    class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new TaskFragment();
                case 1:
                    return new AwardFragment();
                case 2:
                    statisticsFragment = new StatisticsFragment();
                    return statisticsFragment;
                case 3:
                    return new MeFragment();
            }
            return null;
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }
}




