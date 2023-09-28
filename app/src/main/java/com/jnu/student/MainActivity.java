package com.jnu.student;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView Hello_Android = findViewById(R.id.text_view_hello_world);
        Hello_Android.setText(R.string.Hello_Android);

        Button change_text = findViewById(R.id.change_text);
        change_text.setOnClickListener(view -> {
            TextView textView1 = findViewById(R.id.textView_Hello);
            TextView textView2 = findViewById(R.id.textView_JNU);

            // 获取当前的文本
            String text1 = textView1.getText().toString();
            String text2 = textView2.getText().toString();

            // 交换文本
            textView1.setText(text2);
            textView2.setText(text1);

            Toast changeText = Toast.makeText(getApplicationContext(),
                    R.string.change_text, Toast.LENGTH_SHORT);
            changeText.show();

            Context mContext = view.getContext();
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle(R.string.change_view_title)
                    .setMessage(R.string.change_view_content)
                    .setNegativeButton(R.string.change_view_cancel, (dialog, which) ->
                            Toast.makeText(mContext, R.string.change_text, Toast.LENGTH_SHORT).show())
                    .setPositiveButton(R.string.change_view_sure, (dialog, which) ->
                            Toast.makeText(mContext, R.string.change_text, Toast.LENGTH_SHORT).show())
                    .create().show();  // 创建AlertDialog对象并显示
        });
    }
}