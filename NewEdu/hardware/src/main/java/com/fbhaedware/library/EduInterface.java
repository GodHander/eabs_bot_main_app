package com.fbhaedware.library;

import android.content.Context;
import android.view.View;

import com.fbhaedware.library.answerdb.bean.TranslationWordBean;
import com.fbhaedware.library.face.FaceRectView;

public interface EduInterface {


    /**
     * 开启语音唤醒
     */
    void startWakeUp();

    /**
     * 关闭语音唤醒
     */
    void stopWakeUp();


    void registerWakeUpListener(WakeUpCallBack callBack);


    /**
     * 打开语音识别
     */
    void startAsr();

    /**
     * 停止语音识别
     */
    void stopAsr();

    /**
     * 注册监听语音识别结果 ，当传入null的时候，代表注销监听
     * @param callBack
     */
     void registerAsrListener(AsrCallBack callBack);


    interface WakeUpCallBack {

        /**
         * 唤醒成功
         */
        void WakeUpSuccess();


        /**
         * 唤醒失败
         */
        void WakeUpFailed();

        /**
         * 唤醒停止
         */
        void WakeUpStop();

        /**
         * 唤醒开始
         */
        void WakeUpStart();
    }


    /**
     * 语音处理接口
     */
     interface AsrCallBack{

        /**
         * 语音识别成功
         * @param result  识别结果
         */
        void onGetAsrSuccess(String result);


        /**
         * 语音服务器处理之后的结果
         * @param response  智能知识库处理后的回调
         */
        void onGetAnswer(String result, TranslationWordBean response);

        /**
         * 请求语音服务器异常
         * @param failReason  失败原因
         */
        void onGetAnswerFail(String result, String failReason);

        /**
         * 语音识别失败
         * @param reason 识别异常信息
         */
        void onGetAsrFailed(String reason);
    }


    /**
     * 开始播报
     * @param txtMessage  播报文字
     */
    void startTts(String txtMessage,TTsCallBack callBack);

    /**
     * 停止播报
     */
    void stopTts();


    /**
     * 注册语音播报接口
     * @param callBack 语音播报接口回调，当传入null 代表注销
     */
     void registerTtsListener(TTsCallBack callBack);



     interface TTsCallBack{

        /**
         * 开始播报状态回调
         */
        void onTtsStart(int takeUpTime);

        /**
         *  正在播报状态回调
         */
        void onTtsProgressChanged(int i);

        /**
         * 播报完成状态回调
         * s -- 播报文字
         */
        void onTtsFinish(String s);

        /**
         * 报状态回调
         */
        void onTtsFailed(String error);
    }


    /**
     * 开启人脸识别
     */
    void startFace(Context context, View previewView, FaceRectView faceRectView);

    /**
     * 关闭人脸识别
     */
    void stopFace();

    /**
     * 注册语音播报接口
     * @param callBack 语音播报接口回调    当传入null 代表注销
     */
     void registerFaceListener(FaceCallBack callBack);


    /**
     * 人脸注册信息
     */
     interface FaceCallBack{

        /**
         * 有人脸
         * @param person 当前人信息，分Vip和 非Vip   --isvip
         */
        void PersonComeOn(Person person);

        /**
         * 无人脸
         * @param person  当前人信息，分Vip和 非Vip  --isvip
         */
        void PersonComeOff(Person person);

    }

    class Person{
        private String name;
        private boolean isvip;
        private int age;
        private int sex;
    }
}
