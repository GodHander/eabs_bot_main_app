package com.fbhaedware.library.speech.wp;

import android.util.Log;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;
import com.fbhaedware.library.BDManager;
import com.fbhaedware.library.EduInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * zahngy
 * Created by zhangy on 2017/12/12.
 */

public class WakeUpManagerImpl implements WakeUpManager{

    private static final String TAG = "WakeUpManagerImpl";
    private static final String TYPE_WP = "wp";

    private static final Object lock = new Object();
    private static volatile WakeUpManagerImpl instance;

    private EventManager wp;
    private EduInterface.WakeUpCallBack wakeUpCallBack;

    private WakeUpManagerImpl() {
        config();
    }

    private void config() {
        wp = EventManagerFactory.create(BDManager.Ext.getContext(), TYPE_WP);
        wp.registerListener(new EventListener() {
            @Override
            public void onEvent(String name, String params, byte[] data, int offset, int length) {
//                String logTxt = "name: " + name;
//                if (params != null && !params.isEmpty()) {
//                    logTxt += " ;params :" + params;
//                } else if (data != null) {
//                    logTxt += " ;data length=" + data.length;
//                }
//                printLog(logTxt);
                if (name.equals(SpeechConstant.CALLBACK_EVENT_WAKEUP_SUCCESS)) {
                    try {
                        JSONObject json = new JSONObject(params);
                        int errorCode = json.getInt("errorCode");
                        if (errorCode == 0) {
                            Log.e(TAG, "唤醒成功");
                            if (wakeUpCallBack != null)
                                wakeUpCallBack.WakeUpSuccess();
                        } else {
                            Log.e(TAG, "唤醒失败");
                            if (wakeUpCallBack != null)
                                wakeUpCallBack.WakeUpFailed();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (SpeechConstant.CALLBACK_EVENT_WAKEUP_STOPED.equals(name)) {
                    if (wakeUpCallBack != null)
                        wakeUpCallBack.WakeUpStop();
                    Log.e(TAG, "唤醒停止");
                }else if(SpeechConstant.CALLBACK_EVENT_WAKEUP_READY.equals(name)){
                    if (wakeUpCallBack != null)
                        wakeUpCallBack.WakeUpStart();
                }
            }
        });

    }
//    private boolean logTime = true;

//    private void printLog(String text) {
//        if (logTime) {
//            text += "  ;time=" + System.currentTimeMillis();
//        }
//        text += "\n";
//        Log.i(getClass().getName(), text);
//        Log.d(TAG, "printLog: ------>"+ text + "\n");
//    }

    public static void init() {
        if(instance==null){
            synchronized (lock) {
                if (instance == null) {
                    instance = new WakeUpManagerImpl();
                }
            }
        }
        BDManager.Ext.setWakeUpManager(instance);
    }

    @Override
    public void startWakeUp() {

        Map<String, Object> params = new TreeMap<String, Object>();
        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
        params.put(SpeechConstant.WP_WORDS_FILE, "assets:///WakeUp.bin");
        params.put(SpeechConstant.APP_ID,"16508494");

        String json = null; // 这里可以替换成你需要测试的json
        json = new JSONObject(params).toString();


        Log.d(TAG, "startWakeUp: "+json);

        wp.send(SpeechConstant.WAKEUP_START, json, null, 0, 0);
    }

    @Override
    public void stopWakeUp() {
        wp.send(SpeechConstant.WAKEUP_STOP, null, null, 0, 0);
    }

    @Override
    public void rigsterWakeUpListener(EduInterface.WakeUpCallBack callBack) {
        this.wakeUpCallBack = callBack;
    }


}
