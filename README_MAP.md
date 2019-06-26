
``` 
public LatLng convertToLatLng(LatLonPoint point) {
    return new LatLng(point.getLatitude(),point.getLongitude());
}
```
# 改变地图状态

``` 
//直接改变状态，没有动画效果
public final void moveCamera(CameraUpdate update)

//带动画
public final void animateCamera(CameraUpdate update)
```
 AMap.animateCamera(CameraUpdate) or AMap.moveCamera(CameraUpdate)

 使用CameraUpdateFactory 类里面的一系列方法可以构造CameraUpdate 对象。
 如 CameraUpdateFactory.zoomIn()


# 设置地图的中心点

``` 
aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
aMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
```
获取LatLng：
``` 
/**
 * 把LatLonPoint对象转化为LatLon对象
 */
public static LatLng convertToLatLng(LatLonPoint latLonPoint) {
    if (latLonPoint ==null){
        return null;
    }
    return new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
}
```

# 设置显示在规定屏幕范围内的地图经纬度范围

``` 
mAMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
```

获取bounds：
``` 
private LatLngBounds getBounds(List<LatLng> pointlist) {
    LatLngBounds.Builder b = LatLngBounds.builder();
    if (pointlist == null) {
        return b.build();
    }
    for (int i = 0; i < pointlist.size(); i++) {
        b.include(pointlist.get(i));
    }
    return b.build();

}
```

# search

com.amap.api.services.route	
路径查询包，包含了在公交、自驾或步行路线规划中的详细信息。


RouteSearch.FromAndTo
构造路径规划的起点和终点坐标。
``` 
FromAndTo(LatLonPoint from, LatLonPoint to)
```

RouteSearch.DriveRouteQuery
此类定义了驾车路径查询规划。
``` 
DriveRouteQuery(RouteSearch.FromAndTo fromAndTo, 
                int mode, 
                java.util.List<LatLonPoint> passedByPoints, 
                java.util.List<java.util.List<LatLonPoint>> avoidpolygons, 
                java.lang.String avoidRoad)
fromAndTo - 路径的【起点终点】。
mode - 【计算路径的模式】。可选，默认为速度优先 RouteSearch.DrivingDefault。
passedByPoints - 【途经点】，可选。最多支持16个途经点。
avoidpolygons - 【避让区域】，可选。区域避让，支持32个避让区域，每个区域最多可有16个顶点。
                如果是四边形则有4个坐标点，如果是五边形则有5个坐标点。
                同时传入避让区域及避让道路，仅支持避让道路。
avoidRoad - 【避让道路】名称，可选。目前只支持一条避让道路。
```

mode:
``` 
public static final int DRIVING_SINGLE_DEFAULT
速度优先
public static final int DRIVING_SINGLE_SAVE_MONEY
费用优先（不走收费路的最快道路）。
public static final int DRIVING_SINGLE_SHORTEST
距离优先。
public static final int DRIVING_SINGLE_NO_EXPRESSWAYS
不走快速路。
public static final int DRIVING_SINGLE_AVOID_CONGESTION
避免拥堵。
public static final int DRIVING_MULTI_STRATEGY_FASTEST_SAVE_MONEY_SHORTEST
同时使用速度优先、费用优先、距离优先三个策略计算路径。
public static final int DRIVING_SINGLE_NO_HIGHWAY
不走高速。
public static final int DRIVING_SINGLE_NO_HIGHWAY_SAVE_MONEY
不走高速且避免收费。
public static final int DRIVING_SINGLE_SAVE_MONEY_AVOID_CONGESTION
避免收费与拥堵。
public static final int DRIVING_SINGLE_NO_HIGHWAY_SAVE_MONEY_AVOID_CONGESTION
不走高速且躲避收费和拥堵。
``` 

示例：
```
RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(mStartPoint, mEndPoint);

// 驾车路径规划
RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, mode, null, null, "");
// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，
// 第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路

// 异步路径规划驾车模式查询
mRouteSearch.calculateDriveRouteAsyn(query);
``` 
onDriveRouteSearched(DriveRouteResult driveRouteResult, int errorCode)
驾车路径规划结果的回调方法。
driveRouteResult - 驾车路径规划的结果集。
errorCode - 返回结果成功或者失败的响应码。
            1000为成功(V3.2.1之后搜索成功响应码为1000，之前版本为0)，
            其他为失败（详细信息参见网站开发指南-错误码对照表）。

规划完毕后在此回调进行画路线。










