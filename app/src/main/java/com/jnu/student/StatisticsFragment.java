package com.jnu.student;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.student.myclass.Record;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    ShopItemAdapter shopItemAdapter;
    double token_num;
    MainActivity mainActivity;
    TextView token;

    public ShopItemAdapter getShopItemAdapter() {
        return shopItemAdapter;
    }

    public void setToken_num(double token_num) {
        this.token_num = token_num;

        if (token_num > 0) {
            token.setTextColor(Color.GREEN);
            String costFormat = getResources().getString(R.string.positive_token_show);
            token.setText(String.format(costFormat, token_num));
        } else if (token_num < 0) {
            token.setTextColor(Color.RED);
            String costFormat = getResources().getString(R.string.negative_token_show);
            token.setText(String.format(costFormat, token_num));
        } else {
            token.setTextColor(Color.BLACK);
            String costFormat = getResources().getString(R.string.negative_token_show);
            token.setText(String.format(costFormat, token_num));
        }
    }

    public StatisticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment StatisticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticsFragment newInstance() {
        StatisticsFragment fragment = new StatisticsFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_statistics, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        // 获得账本数据
        mainActivity = (MainActivity) getActivity();
        shopItemAdapter = new ShopItemAdapter(mainActivity.getRecords());
        recyclerView.setAdapter(shopItemAdapter);

        // 获得总计代币
        token_num = mainActivity.getValue();
        token = rootView.findViewById(R.id.textViewToken);

        if (token_num > 0) {
            token.setTextColor(Color.GREEN);
            String costFormat = getResources().getString(R.string.positive_token_show);
            token.setText(String.format(costFormat, token_num));
        } else if (token_num < 0) {
            token.setTextColor(Color.RED);
            String costFormat = getResources().getString(R.string.negative_token_show);
            token.setText(String.format(costFormat, token_num));
        } else {
            token.setTextColor(Color.BLACK);
            String costFormat = getResources().getString(R.string.negative_token_show);
            token.setText(String.format(costFormat, token_num));
        }

        return rootView;
    }

    public class ShopItemAdapter extends RecyclerView.Adapter<ShopItemAdapter.ShopItemViewHolder> {
        ArrayList<Record> records;

        public ShopItemAdapter(ArrayList<Record> records) {
            this.records = records;
        }

        @ Override
        @NonNull
        // 这个方法是当RecyclerView需要创建新的列表项（即一个新的ViewHolder）时会被调用。
        // 这个方法会创建并初始化ViewHolder及其关联的视图，但不会填充视图的内容，因为ViewHolder此时尚未绑定到具体数据。
        public ShopItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.for_statistics_recycle_view, parent, false);
            return new ShopItemViewHolder(view);
        }


        @ Override
        // 这个方法是当RecyclerView需要将ViewHolder与数据进行绑定时会被调用。
        public void onBindViewHolder(ShopItemViewHolder holder, int position) {
            Record currentItem = records.get(position);

            // 账本时间和描述
            // 创建一个SimpleDateFormat对象，指定目标格式
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss", Locale.CHINA);
            // 使用SimpleDateFormat对象的format方法将Date对象转换为字符串
            String str = formatter.format(currentItem.getDate());

            holder.time.setText(str);
            holder.name.setText(currentItem.getDescribe());

            // 奖励或者代价
            double cost = currentItem.getChange_value();
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
            return records.size();
        }


        // 这是你自定义的ViewHolder类。每个列表项在屏幕上都由一个ViewHolder对象表示。
        // 当创建一个新的ViewHolder时，它并没有任何关联的数据。
        // 当RecyclerView准备将它显示在屏幕上时，就会调用上面提到的onBindViewHolder()方法，将数据绑定到这个ViewHolder。
        public class ShopItemViewHolder extends RecyclerView.ViewHolder {
            TextView time;
            TextView name;
            TextView price;

            public ShopItemViewHolder(View itemView) {
                super(itemView);
                time = itemView.findViewById(R.id.textViewTime);
                name = itemView.findViewById(R.id.textViewDescription);
                price = itemView.findViewById(R.id.textViewPrice);
            }
        }
    }
}