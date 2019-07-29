package com.fbhaedware.library.speech.tts;


import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.fbhaedware.library.BDManager;
import com.fbhaedware.library.EduInterface;

import java.io.File;

/**
 * zhangy
 * Created by zhangy on 2017/12/12.
 */

public class TtsManagerImpl implements TtsManager {

    private static final String TAG = "TtsManagerImpl";
    private static final String SAMPLE_DIR_NAME = "baiduTTS";
    private static final String SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female.dat";
    private static final String SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male.dat";
    private static final String TEXT_MODEL_NAME = "bd_etts_text.dat";
    private static final String LICENSE_FILE_NAME = "temp_license";
    private static final String ENGLISH_SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female_en.dat";
    private static final String ENGLISH_SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male_en.dat";
    private static final String ENGLISH_TEXT_MODEL_NAME = "bd_etts_text_en.dat";
    private static final int CHECK_DELAY = 1000;

    private String mSampleDirPath;
    private static final Object lock = new Object();
    private static volatile TtsManagerImpl instance;
    private SpeechSynthesizer mSpeechSynthesizer;
    private EduInterface.TTsCallBack ttsCallBack;
    private byte[] totalBytes = null; // 用来记录本次播报合成的音频数据(计算耗时用-百度TTS使用16000采样率16bits编码的pcm)
    private Handler mH = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
                Log.d(TAG,"handle speech msg");
                SpeechItem item = (SpeechItem) msg.obj;
                if(ttsIng){
                    Message msg2 = new Message();
                    msg2.what = 0;
                    Log.d(TAG,"to speech:"+item.getText());
                    msg2.obj = msg.obj;
                    mH.sendMessageDelayed(msg2,CHECK_DELAY);
                }else {
                    Log.d(TAG,"handle speech:"+item.getText());
                    speaks(item.getText(),item.getResultCallback());
                }
            }
        }
    };

    private TtsManagerImpl() {
        config();
    }

    public static void init() {
        if (instance == null) {
            synchronized (lock) {
                instance = new TtsManagerImpl();
            }
        }
        BDManager.Ext.setTtsManager(instance);
    }

    private void initialEnv() {
        if (mSampleDirPath == null) {
            String sdcardPath = Environment.getExternalStorageDirectory().toString();
            mSampleDirPath = sdcardPath + "/" + SAMPLE_DIR_NAME;
        }
        makeDir(mSampleDirPath);
        AssetsUtils.copyFromAssetsToSdcard(false, SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_FEMALE_MODEL_NAME);
        AssetsUtils.copyFromAssetsToSdcard(false, SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_MALE_MODEL_NAME);
        AssetsUtils.copyFromAssetsToSdcard(false, TEXT_MODEL_NAME, mSampleDirPath + "/" + TEXT_MODEL_NAME);
//        AssetsUtils.copyFromAssetsToSdcard(false, LICENSE_FILE_NAME, mSampleDirPath + "/" + LICENSE_FILE_NAME);
        AssetsUtils.copyFromAssetsToSdcard(false, "english/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_SPEECH_FEMALE_MODEL_NAME);
        AssetsUtils.copyFromAssetsToSdcard(false, "english/" + ENGLISH_SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_SPEECH_MALE_MODEL_NAME);
        AssetsUtils.copyFromAssetsToSdcard(false, "english/" + ENGLISH_TEXT_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_TEXT_MODEL_NAME);
    }

    private void makeDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private  boolean startTTs = false;
    private  volatile boolean ttsIng = false;
    private void config() {
        this.mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        this.mSpeechSynthesizer.setContext(BDManager.Ext.getContext());
        this.mSpeechSynthesizer.setSpeechSynthesizerListener(new SpeechSynthesizerListener() {
            // 合成开始
            @Override
            public void onSynthesizeStart(String s) {
                Log.e(TAG, "TTS onSynthesizeStart：" + s);
                // 合成前清空
                totalBytes = null;
            }
            // 正在合成
            @Override
            public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {
                Log.e(TAG, "TTS onSynthesizeDataArrived：" + i);
                // 合成过程中不断累加数据
                if (totalBytes == null) {
                    totalBytes = bytes;
                } else {
                    totalBytes = addBytes(totalBytes, bytes);
                }
            }

            // 合成完成
            @Override
            public void onSynthesizeFinish(String s) {
                Log.e(TAG, "TTS onSynthesizeFinish：" + s);

                // 合成完成计算总耗时
                int takeUpTime = totalBytes.length/32;
                Log.e(TAG, "朗读大约需要耗时毫秒数: " + takeUpTime);

                // 将耗时时间传给前端
                if(ttsCallBack != null)
                    ttsCallBack.onTtsStart(takeUpTime);

                if(ttsIng) {
                    startTTs = true;
                }else{
                    startTTs = false;
                }
            }

            // 播报完成
            @Override
            public void onSpeechStart(String s) {
                Log.e(TAG, "TTS onSpeechStart ：" + s);
            }

            // 正在播报
            @Override
            public void onSpeechProgressChanged(String s, int i) {
                Log.e(TAG, "TTS onSpeechProgressChanged ：" + i);
                ttsIng = true;
//                if(startTTs == true){
//                    stop();
//                    ttsIng = false;
//                    if(ttsCallBack != null)
//                        ttsCallBack.onTtsProgressChanged(i); //不正常的播报
//                }
            }

            //播报完成
            @Override
            public void onSpeechFinish(String s) {
                Log.e(TAG, "onSpeechFinish");
                ttsIng = false;
                if(ttsCallBack != null)
                    ttsCallBack.onTtsFinish(s);
            }

            @Override
            public void onError(String s, SpeechError speechError) {
                Log.d(TAG,"onError");
                ttsIng = false;
                if(ttsCallBack != null)
                    ttsCallBack.onTtsFailed(speechError.description);
            }
        });
        // 文本模型文件路径 (离线引擎使用)
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, mSampleDirPath + "/"
                + TEXT_MODEL_NAME);
        // 声学模型文件路径 (离线引擎使用)
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, mSampleDirPath + "/"
                + SPEECH_FEMALE_MODEL_NAME);
        // 本地授权文件路径,如未设置将使用默认路径.设置临时授权文件路径，LICENCE_FILE_NAME请替换成临时授权文件的实际路径，
        // 仅在使用临时license文件时需要进行设置，如果在[应用管理]中开通了正式离线授权，不需要设置该参数，建议将该行代码删除（离线引擎）
        // 如果合成结果出现临时授权文件将要到期的提示，说明使用了临时授权文件，请删除临时授权即可。
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, mSampleDirPath + "/"
                + LICENSE_FILE_NAME);
        // 请替换为语音开发者平台上注册应用得到的App ID (离线授权)
        this.mSpeechSynthesizer.setAppId("8535996"/*这里只是为了让Demo运行使用的APPID,请替换成自己的id。*/);
        // 请替换为语音开发者平台注册应用得到的apikey和secretkey (在线授权)
        this.mSpeechSynthesizer.setApiKey("MxPpf3nF5QX0pndKKhS7IXcB",
                "7226e84664474aa098296da5eb2aa434"/*这里只是为了让Demo正常运行使用APIKey,请替换成自己的APIKey*/);
        // 发音人（在线引擎），可用参数为0,1,2,3。。。（服务器端会动态增加，各值含义参考文档，
        // 以文档说明为准。0--普通女声，1--普通男声，2--特别男声，3--情感男声。。。）
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "4");
        // 设置Mix模式的合成策略
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
        // 授权检测接口(只是通过AuthInfo进行检验授权是否成功。)
        // AuthInfo接口用于测试开发者是否成功申请了在线或者离线授权，如果测试授权成功了，
        // 可以删除AuthInfo部分的代码（该接口首次验证时比较耗时），不会影响正常使用（合成使用时SDK内部会自动验证授权）
        AuthInfo authInfo = this.mSpeechSynthesizer.auth(TtsMode.MIX);
        if (authInfo.isSuccess()) {
            Log.e(TAG, "auth success");
        } else {
            String errorMsg = authInfo.getTtsError().getDetailMessage();
            Log.e(TAG, "auth failed errorMsg=" + errorMsg);
        }
        // 初始化tts
        mSpeechSynthesizer.initTts(TtsMode.MIX);
        // 加载离线英文资源（提供离线英文合成功能）
        int result =
                mSpeechSynthesizer.loadEnglishModel(mSampleDirPath + "/" + ENGLISH_TEXT_MODEL_NAME, mSampleDirPath
                        + "/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME);
        Log.e(TAG, "loadEnglishModel result=" + result);
    }


    @Override
    public void speaks(String message, EduInterface.TTsCallBack callBack) {
        Log.e(TAG,"准备播报新的任务！");
        if(ttsIng){
            // 停止正在播报的旧任务
            mSpeechSynthesizer.stop();
            ttsIng = false;
            // 创建新的任务
            Message msg = new Message();
            msg.what = 0;
            SpeechItem item = new SpeechItem(message,callBack);
            msg.obj = item;
            mH.sendMessageDelayed(msg,CHECK_DELAY);
            return;
        }
        this.ttsCallBack = callBack;
        ttsIng = true;
        int result = this.mSpeechSynthesizer.speak(message);
        Log.e(TAG, "TTS 合成结果：" + String.valueOf(result));
    }


    @Override
    public void stop() {
        mSpeechSynthesizer.stop();
        ttsIng = false;
    }

    @Override
    public void cancel() {
        mSpeechSynthesizer.stop();
    }

    @Override
    public void registerTtsListener(EduInterface.TTsCallBack callBack) {
        this.ttsCallBack = callBack;
    }
    private class SpeechItem {
        private String text;
        private EduInterface.TTsCallBack resultCallback;
        public SpeechItem(String text, EduInterface.TTsCallBack callBack){
            this.text = text;
            this.resultCallback = callBack;
        }

        public String getText() {
            return text;
        }

        public EduInterface.TTsCallBack getResultCallback() {
            return resultCallback;
        }
    }

    private byte[] addBytes(byte[] data1, byte[] data2) {
        byte[] data3 = new byte[data1.length + data2.length];
        System.arraycopy(data1, 0, data3, 0, data1.length);
        System.arraycopy(data2, 0, data3, data1.length, data2.length);
        return data3;
    }

}
