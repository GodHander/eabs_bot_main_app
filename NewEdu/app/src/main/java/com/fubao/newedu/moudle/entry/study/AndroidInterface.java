package com.fubao.newedu.moudle.entry.study;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.fanneng.android.web.SuperWebX5;
import com.fbhaedware.library.EduInterface;
import com.fbhaedware.library.answerdb.CapacityHttp;
import com.fbhaedware.library.answerdb.bean.FollowUpJudgeBean;
import com.fbhaedware.library.answerdb.bean.FollowUpJudgeCallback;
import com.fubao.newedu.service.HardwareService;
import com.fubao.newedu.service.PictureBookService;
import com.fubao.newedu.utils.FileUtlis;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * describe：公共加载fragment类
 *  js 返回给我结果
 *
 */
public class AndroidInterface {


    private static final String TAG = "AndroidInterface";
    private SuperWebX5 mSuperWeb;
    private Context context;
    private int  FaceStateOn = 0;
    private int  FaceStateOff = 0;

    public AndroidInterface(SuperWebX5 superWeb, Context context) {
        this.mSuperWeb = superWeb;
        this.context = context;
    }

    private Handler deliver = new Handler(Looper.getMainLooper());

    @JavascriptInterface
    public void callAndroidMethod(final String funcName, final String paramJson) {

//        deliver.post(() -> {

//            Type枚举项：
//            0-播报开始
//            1-播报异常
//            2-播报进度
//            3-播报完成
            if (funcName .equals("startTts")) { //播报事件
                Log.d(TAG, "playerEventHandler: -- startTts");
                JSONObject json = null;
                try {
                    json = new JSONObject(paramJson);
                    boolean needReceive = json.getBoolean("needReceive");
                    final String text = json.getString("text");

                    if(needReceive) {
                        HardwareService.getInstance().startTts(text, new EduInterface.TTsCallBack() {
                            @Override
                            public void onTtsStart() {
                                mSuperWeb.getJsEntraceAccess().quickCallJs("appEventHandler", "ttsStatusEvent", "{\"type\": "+0+",\"failReason\": \"\",\"param\": {\"process\": \"\"}}");
                            }
                            @Override
                            public void onTtsProgressChanged(int i) {
                                mSuperWeb.getJsEntraceAccess().quickCallJs("appEventHandler", "ttsStatusEvent","{\"type\": "+2+",\"failReason\": \"\",\"param\": {\"process\": \""+i+"\"}}");
                            }
                            @Override
                            public void onTtsFinish(String s) {
                                mSuperWeb.getJsEntraceAccess().quickCallJs("appEventHandler", "ttsStatusEvent", "{\"type\": "+3+",\"failReason\": \"\",\"param\": {\"process\": \"\"}}");
                            }

                            @Override
                            public void onTtsFailed(String error) {
                                mSuperWeb.getJsEntraceAccess().quickCallJs("appEventHandler", "ttsStatusEvent", "{\"type\": "+1+",\"failReason\": \""+error+"\",\"param\": {\"process\": \"\"}}");
                            }
                        });
                    }else{
                        HardwareService.getInstance().startTts(text,null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
//        });

        if (funcName.equals("openPageByNO")) { //播报器请求打开指定页码的文件
            Log.d(TAG, "callAndroidMethod: -- openPageByNO");
            JSONObject json = null;
            try {
                json = new JSONObject(paramJson);
                int wantPageNO = json.getInt("pageNO");
                JSONObject nextPageInfo = PictureBookService.getInstance().getLogicPageInfoByNO(wantPageNO);
                if (nextPageInfo != null) {
                    String fromAssets2 = FileUtlis.getFromAssets(context, nextPageInfo.getString("filePath"), true);
                    String configString = FileUtlis.getFromAssets(context, nextPageInfo.getString("configFilePath"), false);
                    mSuperWeb.getJsEntraceAccess().quickCallJs("callJsMethod","loadNewProjectFile",getPlayJson(true, 1, configString), fromAssets2);
                } else {
                    Log.d(TAG,"找不到要跳转的页码对应的文件信息，页码：" + wantPageNO);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (funcName.equals("transitStatusInfo")) { //状态同步
            Log.d(TAG, "callAndroidMethod: -- transitStatusInfo");
            JSONObject json = null;
            try {
                json = new JSONObject(paramJson);
                Map<String, String> parentState = PictureBookService.getInstance().getCourseState();
                JSONObject relativeState = new JSONObject(json.getString("stateInfo"));

                Map<String, String> fullState = new HashMap<>();
                fullState.put("taskId", parentState.get("taskId"));
                fullState.put("taskName", parentState.get("taskName"));
                fullState.put("sceneId", parentState.get("sceneId"));
                fullState.put("sceneName", parentState.get("sceneName"));
                fullState.put("coursewareId", parentState.get("coursewareId"));
                fullState.put("coursewareName", parentState.get("coursewareName"));
                fullState.put("fatherSliceId", relativeState.getString("fatherSliceId"));
                fullState.put("fatherSliceName", relativeState.getString("fatherSliceName"));
                fullState.put("sliceId", relativeState.getString("sliceId"));
                fullState.put("sliceName", relativeState.getString("sliceName"));
                fullState.put("pageId", relativeState.getString("pageId"));
                fullState.put("pageName", relativeState.getString("pageName"));
                fullState.put("state", relativeState.getString("state"));

                CapacityHttp.transitRobotState(fullState);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (funcName.equals("judgeFollowUpResult")) { //判定跟读结果
            Log.d(TAG, "callAndroidMethod: -- judgeFollowUpResult");
            JSONObject json = null;
            try {
                json = new JSONObject(paramJson);

                Map<String, String> inParam = new HashMap<>();
                inParam.put("uniqueCode", json.getString("uniqueCode"));
                inParam.put("speechText", json.getString("speechText"));
                inParam.put("followText", json.getString("followText"));

                Log.i(TAG, "发起请求: 跟读任务准确度判定!");
                CapacityHttp.judgeFollowUpResult(inParam, new FollowUpJudgeCallback(){
                    @Override
                    public void FollowUpJudgeResult(FollowUpJudgeBean response) {
                        JSONObject result = new JSONObject();
                        try {
                            if (response.getREP_HEAD().getTRAN_CODE().equals("000000")) {
                                result.put("score", response.getREP_BODY().getScore());
                                result.put("uniqueCode", response.getREP_BODY().getUniqueCode());
                                mSuperWeb.getJsEntraceAccess().quickCallJs("appEventHandler", "followUpResultEvent", result.toString());
                            } else {
                                Log.d(TAG, "judgeFollowUpResult 结果异常: " + response.getREP_HEAD().getTRAN_RSPMSG());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void FollowUpJudgeFail(String failReason) {
                        Log.i(TAG, "judgeFollowUpResult 结果异常: "+ failReason);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        HardwareService.getInstance().registerFaceListener(new EduInterface.FaceCallBack() {
            @Override
            public void PersonComeOn(EduInterface.Person person) { //人来了
                FaceStateOn ++;
                if(FaceStateOn == 1) {
                    Log.d(TAG,"人来了。。。");
                    mSuperWeb.getJsEntraceAccess().quickCallJs("appEventHandler", "personFindEvent", "");
                    FaceStateOff = 0;
                }
            }

            @Override
            public void PersonComeOff(EduInterface.Person person) { //人走了
                FaceStateOff ++;
                if(FaceStateOff == 1) {
                    Log.d(TAG,"人走了。。。");
                    mSuperWeb.getJsEntraceAccess().quickCallJs("appEventHandler", "personDisappearEvent", "");
                    FaceStateOn = 0;
                }
            }
        });
    }

    /**
     *  给播放器提供的事件
     * @param evtName  事件名称
     * @param paramJson  事件参数
     */
    @JavascriptInterface
    public void playerEventHandler(final String evtName, final String paramJson) {
        Log.i(TAG, "playerEventHandler---Thread:" + Thread.currentThread());
    }

    private String getPlayJson(boolean flag, int type, String configString) {
        String result = "";
        try {
            JSONObject mJSONObject = new JSONObject();
            mJSONObject.put("autoPlay", flag);
            mJSONObject.put("type", type);
            mJSONObject.put("configString", configString);
            result = mJSONObject.toString();
        } catch (Exception e) {

        }
        return result;
    }

}
