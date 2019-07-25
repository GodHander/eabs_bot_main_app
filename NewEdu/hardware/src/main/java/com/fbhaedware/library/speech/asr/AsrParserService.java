package com.fbhaedware.library.speech.asr;

import android.content.Context;
import android.util.Log;

import com.fbhaedware.library.answerdb.CapacityHttp;
import com.fbhaedware.library.answerdb.bean.TranslationWordCallBack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class AsrParserService {

    private static final String TAG = "AsrParserService";
    public static volatile AsrParserService sInstance= null;

    public static AsrParserService getInstance() {
        if (sInstance == null) {
            synchronized (AsrParserService.class) {
                if (sInstance == null) {
                    sInstance = new AsrParserService();
                }
            }
        }
        return sInstance;
    }


    public void parserWord(String result, final TranslationWordCallBack translationWordCallBack){
        if(!result.isEmpty()){
            //连接智能知识库
            final List<String> list = new ArrayList<>();
            list.add(result);

            CapacityHttp.TranslationWord(list, translationWordCallBack);
        }
    }
}
