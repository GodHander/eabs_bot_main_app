package com.fbhaedware.library;

import android.app.Application;
import android.content.Context;

import com.fbhaedware.library.speech.asr.AsrManager;
import com.fbhaedware.library.speech.asr.AsrManagerImpl;
import com.fbhaedware.library.speech.tts.TtsManager;
import com.fbhaedware.library.speech.tts.TtsManagerImpl;
import com.fbhaedware.library.speech.wp.WakeUpManager;
import com.fbhaedware.library.speech.wp.WakeUpManagerImpl;

/**
 * 硬件驱动的管理类
 */
public class BDManager {


    public static AsrManager asrManager(){
        if(Ext.asrManager==null){
            AsrManagerImpl.init();
        }
        return Ext.asrManager;
    }

    public static TtsManager ttsManager(){
        if(Ext.ttsManager==null){
            TtsManagerImpl.init();
        }
        return Ext.ttsManager;
    }


    public static WakeUpManager wakeUpManager(){
        WakeUpManagerImpl.init();
        if(Ext.wakeUpManager==null){
        }
        return Ext.wakeUpManager;
    }


    public static class Ext {

        private static final String TAG = "Ext";

        private static Application app;
        private static AsrManager asrManager;
        private static TtsManager ttsManager;
        private static WakeUpManager wakeUpManager;          //语音唤醒


        public static void init(Application app) {
            if (Ext.app == null) {
                Ext.app = app;
            }
        }

        public static void setTtsManager(TtsManager ttsManager){
            Ext.ttsManager=ttsManager;
        }

        public static void setAsrManager(AsrManager asrManager) {
            Ext.asrManager = asrManager;
        }
        public static void setWakeUpManager(WakeUpManager wakeUpManager){
            Ext.wakeUpManager=wakeUpManager;
        }

        public static Context getContext(){
            return app.getApplicationContext();
        }

    }
}
