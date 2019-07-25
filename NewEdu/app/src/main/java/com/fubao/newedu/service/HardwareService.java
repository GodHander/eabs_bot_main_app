package com.fubao.newedu.service;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.fbhaedware.library.BDManager;
import com.fbhaedware.library.EduInterface;
import com.fbhaedware.library.HardWareConfig;
import com.fbhaedware.library.SharedPreferencesUtils;
import com.fbhaedware.library.answerdb.CapacityHttp;
import com.fbhaedware.library.answerdb.bean.GetTokenCallBack;
import com.fbhaedware.library.answerdb.bean.RenewTokenCallBack;
import com.fbhaedware.library.face.FaceHelper;
import com.fbhaedware.library.face.FaceRectView;

public class HardwareService implements EduInterface {

    private static final String TAG = "HardwareService";
    public Runnable runnable;
    private Handler handler = new Handler();
    private FaceHelper mFaceHelper;
    private static HardwareService instance;

    public static HardwareService getInstance(){

        if(instance == null){
            synchronized (HardwareService.class){
                if (instance == null){
                    instance = new HardwareService();
                }
            }
        }
        return instance;
    }


    @Override
    public void startWakeUp() {
        BDManager.wakeUpManager().startWakeUp();
    }

    @Override
    public void stopWakeUp() {
        BDManager.wakeUpManager().stopWakeUp();
    }

    @Override
    public void registerWakeUpListener(WakeUpCallBack callBack) {
        BDManager.wakeUpManager().rigsterWakeUpListener(callBack);
    }

    @Override
    public void startAsr() {
        BDManager.asrManager().start();
    }

    @Override
    public void stopAsr() {
        BDManager.asrManager().stop();
    }

    @Override
    public void registerAsrListener(AsrCallBack callBack) {
        BDManager.asrManager().rigsterAsrListener(callBack);
    }

    @Override
    public void startTts(String txtMessage, TTsCallBack callBack) {
        BDManager.ttsManager().speaks(txtMessage,callBack);
    }


    public void stopTts(){
        BDManager.ttsManager().stop();
    }

    @Override
    public void registerTtsListener(TTsCallBack callBack) {
        BDManager.ttsManager().registerTtsListener(callBack);
    }

    @Override
    public void startFace(Context context, View previewView, FaceRectView faceRectView) {
        mFaceHelper = new FaceHelper(context,previewView,faceRectView);
    }

    @Override
    public void stopFace() {
        if(mFaceHelper != null){
            mFaceHelper.destory();
            mFaceHelper = null;
        }
    }

    @Override
    public void registerFaceListener(FaceCallBack callBack) {
        mFaceHelper.registerFaceListener(callBack);
    }


    public void getToken() {
        Log.i(TAG, "发起初始化服务协议格式请求(获取Token)!");
        CapacityHttp.getToken(HardWareConfig.robotHashCode, HardWareConfig.url, new GetTokenCallBack() {
            @Override
            public void getTokenSuc(String token, String effectiveTime) {
                Log.i(TAG, "初始化服务协议格式成功(获取Token) - Token: " + token);
                //保存在共享参数里面去
                SharedPreferencesUtils.setParam(BDManager.Ext.getContext(), "String", token);
                //创建续签Token定时任务
                createNewalTokenTimeTask(token);
            }
            @Override
            public void getTokenFail(String failReason) {
                Log.i(TAG, "初始化服务协议格式失败(获取Token)! FailReason: " + failReason);
            }
        });
    }

    /**
     * 设置续签Token的定时任务，10分钟续签一次
     * @param token
     */
    private void createNewalTokenTimeTask(final String token) {
        if (runnable == null) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    // 获取当前正在使用的Token值
                    String currentToken = (String) SharedPreferencesUtils.getParam(BDManager.Ext.getContext(), "String", "");

                    Log.i(TAG, "定时续签Token任务触发，发起续签Token请求! 当前Token： " + currentToken);

                    CapacityHttp.RenewToken(currentToken, new RenewTokenCallBack() {
                        @Override
                        public void renewalTokenSuc(String token, String effectiveTime) {
                            Log.i(TAG, "续签Token成功! 可以继续使用token：" + token);
                        }

                        @Override
                        public void renewalTokenFail(String failReason) {
                            Log.i(TAG, "续签Token失败, 重新发起初始化服务协议格式请求(获取Token), 失败原因: " + failReason);
                            getToken();
                        }
                    });
                    handler.postDelayed(this, 600*1000);
                }
            };
            // 创建好后立刻启动定时任务，默认每十分钟执行一次runnable.
            handler.postDelayed(runnable, 600*1000);
        }
    }

    /**
     * 停止所有基础服务
     */
    public void stopAllService() {
        stopAsr();
        stopTts();
        stopFace();
    }

    /**
     * 销毁所有服务及任务 (关闭App时候调用)
     */
    public void destory() {
        Log.i(TAG, " - destory: 停止所有服务!");
        stopAllService();

        Log.i(TAG, " - destory: 销毁续签Token定时任务!");
        handler.removeCallbacks(runnable);
        runnable = null;
    }

}
