package com.amap.hightlocation;

import android.content.Context;

import com.amap.api.maps.AMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MapStyleUtil {
    private static final String TAG = MapStyleUtil.class.getSimpleName();
    public static final String STYLE_NAME = "style_extra.data";
    //默认的样式
    public static final String STYLE1_NAME = "style.data";

    @SuppressWarnings({"deprecation", "AliDeprecation"})
    public static void setMapCustomStyle2File(Context context) {
        FileOutputStream outputStream = null;
        InputStream inputStream = null;
        String filePath;
        try {
            inputStream = context.getAssets().open(STYLE_NAME);
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);

            filePath = context.getFilesDir().getAbsolutePath();
            System.out.println("filePath====================" + filePath);
            File file = new File(filePath + "/" + STYLE_NAME);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            outputStream = new FileOutputStream(file);
            outputStream.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings({"deprecation", "AliDeprecation"})
    public static void setMapCustomStyle(Context context, AMap mMap) {
        String filePath = context.getFilesDir().getAbsolutePath() + "/" + STYLE_NAME;
        System.out.println("filePath====================" + filePath);
        mMap.setCustomMapStylePath(filePath);
        mMap.setMapCustomEnable(true);
        mMap.showMapText(true);
    }

}
