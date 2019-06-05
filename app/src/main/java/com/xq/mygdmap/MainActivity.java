package com.xq.mygdmap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.xq.mygdmap.test1.Test1Activity;
import com.xq.mygdmap.test2.Test2Activity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                startActivity(new Intent(MainActivity.this, Test1Activity.class));
                break;
            case R.id.btn2:
                startActivity(new Intent(MainActivity.this, Test2Activity.class));
                break;
        }
    }
}
