package com.amap.navi.demo.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviRouteNotifyData;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

import java.util.LinkedList;

/**
 * 当前DEMO的播报方式是队列模式。其原理就是依次将需要播报的语音放入链表中，播报过程是从头开始依次往后播报。
 * <p>
 * 导航SDK原则上是不提供语音播报模块的，如果您觉得此种播报方式不能满足你的需求，请自行优化或改进。
 */
public class TTSController implements AMapNaviListener {

    private static final String TAG = TTSController.class.getSimpleName();

    /**
     * 请替换您自己申请的ID。
     */
    private final String appId = "57b3c4a9";

    public static TTSController ttsManager;
    private Context mContext;
    private SpeechSynthesizer mTts;
    private boolean isPlaying = false;
    private LinkedList<String> wordList = new LinkedList();
    private final int TTS_PLAY = 1;
    private final int CHECK_TTS_PLAY = 2;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TTS_PLAY:
                    synchronized (mTts) {
                        if (!isPlaying && mTts != null && wordList.size() > 0) {
                            isPlaying = true;
                            String playtts = wordList.removeFirst();
                            if (mTts == null) {
                                createSynthesizer();
                            }
                            mTts.startSpeaking(playtts, new SynthesizerListener() {
                                @Override
                                public void onCompleted(SpeechError arg0) {
                                    isPlaying = false;
                                    handler.obtainMessage(1).sendToTarget();
                                }

                                @Override
                                public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
                                }

                                @Override
                                public void onBufferProgress(int arg0, int arg1, int arg2, String arg3) {
                                    // 合成进度
                                    isPlaying = true;
                                }

                                @Override
                                public void onSpeakBegin() {
                                    //开始播放
                                    isPlaying = true;
                                }

                                @Override
                                public void onSpeakPaused() {
                                }

                                @Override
                                public void onSpeakProgress(int arg0, int arg1, int arg2) {
                                    //播放进度
                                    isPlaying = true;
                                }

                                @Override
                                public void onSpeakResumed() {
                                    //继续播放
                                    isPlaying = true;
                                }
                            });
                        }
                    }
                    break;
                case CHECK_TTS_PLAY:
                    if (!isPlaying) {
                        handler.obtainMessage(1).sendToTarget();
                    }
                    break;
            }

        }
    };

    private TTSController(Context context) {
        mContext = context.getApplicationContext();
        SpeechUtility.createUtility(mContext, SpeechConstant.APPID + "=" + appId);
        if (mTts == null) {
            createSynthesizer();
        }
    }

    private void createSynthesizer() {
        mTts = SpeechSynthesizer.createSynthesizer(mContext,
                new InitListener() {
                    @Override
                    public void onInit(int errorcode) {
                        if (ErrorCode.SUCCESS == errorcode) {
                        } else {
                            Toast.makeText(mContext, "语音合成初始化失败!", Toast.LENGTH_SHORT);
                        }
                    }
                });
    }

    public void init() {
        //设置发音人
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
        //设置语速,值范围：[0, 100],默认值：50
        mTts.setParameter(SpeechConstant.SPEED, "55");
        //设置音量
        mTts.setParameter(SpeechConstant.VOLUME, "tts_volume");
        //设置语调
        mTts.setParameter(SpeechConstant.PITCH, "tts_pitch");
    }

    public static TTSController getInstance(Context context) {
        if (ttsManager == null) {
            ttsManager = new TTSController(context);
        }
        return ttsManager;
    }

    public void stopSpeaking() {
        if (wordList != null) {
            wordList.clear();
        }
        if (mTts != null) {
            mTts.stopSpeaking();
        }
        isPlaying = false;
    }

    public void destroy() {
        if (wordList != null) {
            wordList.clear();
        }
        if (mTts != null) {
            mTts.destroy();
        }
    }

    /****************************************************************************
     * 以下都是导航相关接口
     ****************************************************************************/


    @Override
    public void onArriveDestination() {
        Log.e(TAG, "onArriveDestination==================" );
    }

    @Override
    public void onArrivedWayPoint(int arg0) {
        Log.e(TAG, "onArrivedWayPoint===================" + arg0);

    }

    @Override
    public void onCalculateRouteFailure(int arg0) {
        Log.e(TAG, "onCalculateRouteFailure=====================" + arg0);

        if (wordList != null)
            wordList.addLast("路线规划失败");
    }

    @Override
    public void onEndEmulatorNavi() {
        Log.e(TAG, "onEndEmulatorNavi==========================");

    }

    @Override
    public void onGetNavigationText(int arg0, String arg1) {
        Log.e(TAG, "onGetNavigationText===============" + arg0);
        Log.e(TAG, "onGetNavigationText===============" + arg1);

        if (wordList != null)
            wordList.addLast(arg1);
        handler.obtainMessage(CHECK_TTS_PLAY).sendToTarget();
    }

    @Override
    public void onGetNavigationText(String s) {
        Log.e(TAG, "onGetNavigationText===============" + s);

    }


    @Override
    public void onInitNaviFailure() {
        Log.e(TAG, "onInitNaviFailure===================");

    }

    @Override
    public void onInitNaviSuccess() {
        Log.e(TAG, "onInitNaviSuccess=====================");

    }

    @Override
    public void onLocationChange(AMapNaviLocation arg0) {
        Log.e(TAG, "onLocationChange====================" + arg0.toString());

    }

    @Override
    public void onReCalculateRouteForTrafficJam() {
        Log.e(TAG, "onReCalculateRouteForTrafficJam====================");

        if (wordList != null)
            wordList.addLast("前方路线拥堵，路线重新规划");
    }

    @Override
    public void onReCalculateRouteForYaw() {
        Log.e(TAG, "onReCalculateRouteForYaw==========================");

        if (wordList != null)
            wordList.addLast("路线重新规划");
    }

    @Override
    public void onStartNavi(int arg0) {
        Log.e(TAG, "onStartNavi================" + arg0);

    }

    @Override
    public void onTrafficStatusUpdate() {
        Log.e(TAG, "onTrafficStatusUpdate==========================");

    }

    @Override
    public void onGpsOpenStatus(boolean enabled) {
        Log.e(TAG, "onGpsOpenStatus====================" + enabled);

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviinfo) {
        Log.e(TAG, "onNaviInfoUpdate===================" + naviinfo.toString());

    }

    @Override
    public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {

    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] infoArray) {

    }

    @Override
    public void updateIntervalCameraInfo(AMapNaviCameraInfo aMapNaviCameraInfo, AMapNaviCameraInfo aMapNaviCameraInfo1, int i) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] infoArray) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {

    }

    @Override
    public void hideCross() {

    }

    @Override
    public void showModeCross(AMapModelCross aMapModelCross) {

    }

    @Override
    public void hideModeCross() {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] laneInfos, byte[] laneBackgroundInfo, byte[] laneRecommendedInfo) {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {

    }


    @Override
    public void hideLaneInfo() {

    }

    @Override
    public void onCalculateRouteSuccess(int[] routeIds) {

    }

    @Override
    public void notifyParallelRoad(int parallelRoadType) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] infos) {

    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

    }

    @Override
    public void onPlayRing(int type) {

    }

    @Override
    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {

    }

    @Override
    public void onCalculateRouteFailure(AMapCalcRouteResult aMapCalcRouteResult) {

    }

    @Override
    public void onNaviRouteNotify(AMapNaviRouteNotifyData aMapNaviRouteNotifyData) {

    }
}
