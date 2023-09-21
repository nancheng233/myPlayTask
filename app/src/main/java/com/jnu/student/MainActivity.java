package com.jnu.student;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView Hello_Android = findViewById(R.id.text_view_hello_world);
        Hello_Android.setText(R.string.Hello_Android);
    }
}