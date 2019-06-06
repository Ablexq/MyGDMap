package com.xq.mygdmap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.amap.android_location_markermove.MakerMoveMainActivity;
import com.amap.android_path_smooth.PathSmoothMainActivity;
import com.amap.androidobackgroundlocation.BgLocationActivity;
import com.amap.driveroute.DriveRouteMainActivity;
import com.amap.hightlocation.LocationHighActivity;
import com.amap.location.rotation.RotationMainActivity;
import com.amap.locationservicedemo.LocationServiceActivity;
import com.amap.navi.demo.NaviMasterActivity;
import com.amap.navifragement.NaviFragActivity;
import com.amap.naviquickstart.NaviQuickActivity;
import com.amap.requestpermissions.MapPermissionActivity;

import amap.android_multiple_infowindows.InfoWindowMainActivity;
import amap.com.android_moving_point.MovingPointMainActivity;
import amap.com.android_path_record.PathRecordActivity;
import apis.amap.com.move.SmoothMoveActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onclick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btn1:
                intent = new Intent(MainActivity.this, MapPermissionActivity.class);
                break;
            case R.id.btn2:
                intent = new Intent(MainActivity.this, BgLocationActivity.class);
                break;
            case R.id.btn3:
                intent = new Intent(MainActivity.this, LocationServiceActivity.class);
                break;
            case R.id.btn4:
                intent = new Intent(MainActivity.this, LocationHighActivity.class);
                break;

            /*导航*/
            case R.id.btn5:
                intent = new Intent(MainActivity.this, NaviFragActivity.class);
                break;
            case R.id.btn6:
                intent = new Intent(MainActivity.this, NaviQuickActivity.class);
                break;
            case R.id.btn7:
                intent = new Intent(MainActivity.this, NaviMasterActivity.class);
                break;

            /*轨迹*/
            case R.id.btn8:
                intent = new Intent(MainActivity.this, PathRecordActivity.class);
                break;
            case R.id.btn9:
                intent = new Intent(MainActivity.this, PathSmoothMainActivity.class);
                break;
            case R.id.btn10:
                intent = new Intent(MainActivity.this, SmoothMoveActivity.class);
                break;

            /* */
            case R.id.btn11:
                intent = new Intent(MainActivity.this, MakerMoveMainActivity.class);
                break;
            case R.id.btn12:
                intent = new Intent(MainActivity.this, RotationMainActivity.class);
                break;
            case R.id.btn13:
                intent = new Intent(MainActivity.this, MovingPointMainActivity.class);
                break;
            case R.id.btn14:
                intent = new Intent(MainActivity.this, DriveRouteMainActivity.class);
                break;
            case R.id.btn15:
                intent = new Intent(MainActivity.this, InfoWindowMainActivity.class);
                break;
        }
        startActivity(intent);
    }
}
