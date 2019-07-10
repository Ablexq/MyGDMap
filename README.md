
# demo来源：

https://github.com/amap-demo?utf8=%E2%9C%93&q=&type=&language=java

HT 轨迹跟踪sdk的demo
https://github.com/amap-demo/android-track-demo


# 华为全面屏适配

https://developer.huawei.com/consumer/cn/devservice/doc/50111

# 高德地图集成SDK

[高德地图 的Android Studio配置工程 ](https://lbs.amap.com/api/android-navi-sdk/guide/create-project/android-studio/)

``` 
buildscript {
    repositories {
        mavenCentral() //
        google()
        jcenter()
    }
}

allprojects {
    repositories {
        mavenCentral() //
        google()
        jcenter()
    }
}
```

``` 
android {
    defaultConfig {
        //设置支持的SO库架构（开发者可以根据需要，选择一个或多个平台的so）
        ndk {
            abiFilters "armeabi", "armeabi-v7a", "arm64-v8a", "x86", "arm64-v8a", "x86_64"
        }
    }
}

dependencies {
    //map3d和map2d只能存在一个，且导航只能使用map3d
    //navi导航SDK 5.0.0以后版本包含了3D地图SDK，所以请不要同时引入 map3d 和 navi SDK。
    //3D地图
    //implementation 'com.amap.api:3dmap:latest.integration'
    //2D地图
    //implementation 'com.amap.api:map2d:latest.integration'
    //导航
    implementation 'com.amap.api:navi-3dmap:latest.integration'
    //搜索
    implementation 'com.amap.api:search:latest.integration'
    //定位
    implementation 'com.amap.api:location:latest.integration'    
}
```


# 科大讯飞集成SDK

[科大讯飞 的SDK下载](https://console.xfyun.cn/app/myapp?currPage=1&keyword=)

![](imgs/科大讯飞.png)


# 测试

test2 ： 综合使用搜索和导航实现导航到周边餐馆功能示例

https://github.com/amap-demo/android-navi-quick-start

# 包名

``` 
//module当做application时applicationId要使用app的包名，
//打包的AndroidManifest.xml也会自动转换为app的包名
if (rootProject.ext.isPermissionApplication) {
    applicationId android.applicationId
}
```

# Android8.0适配

报错：
``` 
android.app.RemoteServiceException: Bad notification for startForeground: 
java.lang.RuntimeException: invalid channel for service notification: 
Notification(channel=null pri=0 contentView=null vibrate=null sound=null 
defaults=0x0 flags=0x40 color=0x00000000 vis=PRIVATE)
```
解决参考：

https://lbs.amap.com/api/android-location-sdk/guide/utilities/permision_8

# 错误：

java.lang.NoSuchFieldError: 
No static field navi_fragment of type I in class Lcom/amap/navifragement/R$id; 
or its superclasses (declaration of 'com.amap.navifragement.R$id' appears in base.apk)

或

Caused by: java.lang.NullPointerException: Attempt to invoke virtual method 
'void com.amap.api.maps.MapView.onCreate(android.os.Bundle)' on a null object reference

解决：

注意MapView所在的layout名称 不要和其他layout名称相同。

# 地图样式

官方文档：https://lbs.amap.com/api/android-sdk/guide/create-map/custom/
```  
public class MapStyleUtil {
    private static final String TAG = MapStyleUtil.class.getSimpleName();
    //自定义的样式
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

```

