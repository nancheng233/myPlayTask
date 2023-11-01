package com.jnu.student;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class forShopItem extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.for_shop_item);

        // 假设你有两个EditText，用户在这里输入字符串
        EditText shop_item_name = findViewById(R.id.name);
        EditText shop_item_price = findViewById(R.id.price);

        // 当用户完成输入后，你可以创建一个Intent，并添加数据
        Button button = findViewById(R.id.true_button);
        button.setOnClickListener(view -> {
            Intent data = new Intent();
            data.putExtra("name", shop_item_name.getText().toString());
            data.putExtra("price", shop_item_price.getText().toString());

            // 然后，你可以设置结果码和数据
            setResult(Activity.RESULT_OK, data);

            // 最后，你可以结束这个Activity
            forShopItem.this.finish();
        });
    }
}
