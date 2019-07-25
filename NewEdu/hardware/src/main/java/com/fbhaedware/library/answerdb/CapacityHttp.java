package com.fbhaedware.library.answerdb;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fbhaedware.library.AsyncHttpManager;
import com.fbhaedware.library.BDManager;
import com.fbhaedware.library.SharedPreferencesUtils;
import com.fbhaedware.library.StringHttpCallBack;
import com.fbhaedware.library.answerdb.bean.FollowUpJudgeBean;
import com.fbhaedware.library.answerdb.bean.FollowUpJudgeCallback;
import com.fbhaedware.library.answerdb.bean.GetTokenBean;
import com.fbhaedware.library.answerdb.bean.GetTokenCallBack;
import com.fbhaedware.library.answerdb.bean.RenewTokenBean;
import com.fbhaedware.library.answerdb.bean.RenewTokenCallBack;
import com.fbhaedware.library.answerdb.bean.TranslationWordBean;
import com.fbhaedware.library.answerdb.bean.TranslationWordCallBack;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CapacityHttp {

    private static final String TAG = "CapacityHttp";
    private static String baseUrl = "http://47.103.87.29:7088/fbiks/";

    /**
     * 初始化服务协议格式
     * 获取token
     */
    public static void getToken(String robotHashCode, String url, final GetTokenCallBack getTokenCallBack){

        JSONObject jsonObject1=new JSONObject(); //thridPartyParam
        jsonObject1.put("appKey","ac5d5452");
        jsonObject1.put("isQuestionQuery","0");
        jsonObject1.put("msgID","1111");
        jsonObject1.put("platformConnType","2");
        jsonObject1.put("protocolId","5");
        jsonObject1.put("receiverId","600000000");
        jsonObject1.put("robotHashCode",robotHashCode);
        jsonObject1.put("talkerId","600000000");
        jsonObject1.put("type","text");
        jsonObject1.put("url",url);
        jsonObject1.put("userId","600000000");

        JSONObject jsonObject=new JSONObject();//REQ_BODY
        jsonObject.put("orgId", "000000001");
        jsonObject.put("termId", "00000001");
        jsonObject.put("thirdPartyParam",jsonObject1);

        JSONObject jsonObject2=new JSONObject();  //REQ_HEAD
        jsonObject2.put("tag","IntelligentKnowledgeService");
        jsonObject2.put("type","");
        jsonObject2.put("version","3.50");

        JSONObject jsonObject3=new JSONObject();
        jsonObject3.put("REQ_BODY",jsonObject);
        jsonObject3.put("REQ_HEAD",jsonObject2);

        String json=jsonObject3.toJSONString();

        final RequestParams requestParams = new RequestParams();
        requestParams.put("REQ_MESSAGE",json);

        Log.d(TAG, "getToken (Request) : "+json);

        AsyncHttpManager.get(baseUrl+ "IKInitService.tran", requestParams, new StringHttpCallBack() {
            @Override
            public void onSuccess(int statusCode, String str) {
                Log.d(TAG, "getToken (Response) - onSuccess: "+ str);

                JSONObject object = JSON.parseObject(str);
                GetTokenBean response=JSON.parseObject(object.toJSONString(),GetTokenBean.class);

                if (response.getREP_HEAD().getTRAN_CODE().equals("000000")) {
                    getTokenCallBack.getTokenSuc(response.getREP_BODY().getToken(), response.getREP_BODY().getEffectiveTime());
                } else {
                    getTokenCallBack.getTokenFail(response.getREP_HEAD().getTRAN_RSPMSG());
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable throwable, String error) {
                Log.d(TAG, "getToken (Response) - onFailure: "+ error);

                getTokenCallBack.getTokenFail(error);
            }
        });
    }


    /**
     * 接入智能库进行语义理解
     * @param questions
     * @param translationWordCallBack
     */
    public static void TranslationWord(final List<String> questions, final TranslationWordCallBack translationWordCallBack){

        String token = (String) SharedPreferencesUtils.getParam(BDManager.Ext.getContext(), "String", "");
        if(token.isEmpty()){
            return;
        }

        JSONObject jsonObject2 = new JSONObject(); //"REQ_BODY":
        jsonObject2.put("advanceResults",null);

        JSONArray jsonArray=new JSONArray();
        jsonArray.addAll(questions);

        jsonObject2.put("robotStateSet",null);// 状态  可为空 场景节点
        jsonObject2.put("serialNo",0); //知识唯一码
        jsonObject2.put("supportModel","010101");//
        jsonObject2.put("token",token);
        jsonObject2.put("uniqueCode",new Date());//当前时间的时间戳
        jsonObject2.put("questions",jsonArray);

        JSONObject jsonObject3=new JSONObject();
        jsonObject3.put("REQ_BODY",jsonObject2);
        jsonObject3.put("REQ_HEAD",null);

        String json= null;
        try {
            json = new String(jsonObject3.toJSONString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        final RequestParams requestParams=new RequestParams();
        requestParams.put("REQ_MESSAGE",json);

        Log.d(TAG, "TranslationWord (Request) : "+json);

        AsyncHttpManager.get(baseUrl+ "IK01.tran", requestParams, new StringHttpCallBack() {
            @Override
            public void onSuccess(int statusCode, String str) {
                Log.d(TAG, "TranslationWord (Response) - onSuccess: " + str );

                JSONObject object = JSON.parseObject(str);
                TranslationWordBean response=JSON.parseObject(object.toJSONString(),TranslationWordBean.class);
                translationWordCallBack.TranslationResult(response);
            }
            @Override
            public void onFailure(int statusCode, Throwable throwable, String error) {
                Log.d(TAG, "TranslationWord (Response) - onFailure: " + error );

                translationWordCallBack.TranslationFail(error);
            }
        });
    }

    /**
     * 续签token
     * @param token
     */
    public static void RenewToken(String token, final RenewTokenCallBack renewTokenCallBack) {

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("token", token);
        // 续期时长
//        jsonObject.put("renewalLong", token);

        JSONObject jsonObject1=new JSONObject();
        jsonObject1.put("REQ_BODY", jsonObject);
        jsonObject1.put("REQ_HEAD", null);

        String json= null;
        try {
            json = new String(jsonObject1.toJSONString().getBytes("UTF-8"));

            final RequestParams requestParams=new RequestParams();
            requestParams.put("REQ_MESSAGE", json);

            Log.d(TAG, "RenewToken (Request) : "+json);

            AsyncHttpManager.get(baseUrl+ "IKRenewalToken.tran", requestParams, new StringHttpCallBack() {
                @Override
                public void onSuccess(int statusCode, String str) {
                    Log.d(TAG, "RenewToken (Response) - onSuccess: " + str );

                    JSONObject object = JSON.parseObject(str);
                    RenewTokenBean response = JSON.parseObject(object.toJSONString(), RenewTokenBean.class);

                    if (response.getREP_HEAD().getTRAN_CODE().equals("000000")) {
                        renewTokenCallBack.renewalTokenSuc(response.getREP_BODY().getToken(), response.getREP_BODY().getEffectiveTime());
                    } else {
                        renewTokenCallBack.renewalTokenFail(response.getREP_HEAD().getTRAN_RSPMSG());
                    }
                }

                @Override
                public void onFailure(int statusCode, Throwable throwable, String error) {
                    Log.d(TAG, "RenewToken (Response) - onFailure: " + error );

                    renewTokenCallBack.renewalTokenFail(error);
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


    /**
     * 语义库同步状态
     * @param syncRobotState
     */
    public static void transitRobotState(Map syncRobotState) {
        String token = (String) SharedPreferencesUtils.getParam(BDManager.Ext.getContext(), "String", "");
        if(token.isEmpty()){
            return;
        }

        JSONObject jsonObject = new JSONObject(); //"REQ_BODY":
        jsonObject.put("token",token);
        jsonObject.put("robotId", "00000001");
        jsonObject.put("syncRobotState", syncRobotState);

        JSONObject jsonObject3=new JSONObject();
        jsonObject3.put("REQ_BODY",jsonObject);
        jsonObject3.put("REQ_HEAD",null);

        String json= null;
        try {
            json = new String(jsonObject3.toJSONString().getBytes("UTF-8"));

            final RequestParams requestParams=new RequestParams();
            requestParams.put("REQ_MESSAGE",json);

            Log.d(TAG, "transitRobotState (Request) : "+json);

            AsyncHttpManager.get(baseUrl+ "IKsyncRobotState.tran", requestParams, new StringHttpCallBack() {
                @Override
                public void onSuccess(int statusCode, String str) {
                    Log.d(TAG, "transitRobotState (Response) - onSuccess: " + str );
                }
                @Override
                public void onFailure(int statusCode, Throwable throwable, String error) {
                    Log.d(TAG, "transitRobotState (Response) - onFailure: " + error );
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 绘本跟读结果判定接口
     * @param followData
     */
    public static void judgeFollowUpResult (Map followData, final FollowUpJudgeCallback followUpJudgeCallBack) {
        String token = (String) SharedPreferencesUtils.getParam(BDManager.Ext.getContext(), "String", "");
        if(token.isEmpty()){
            return;
        }

        JSONObject jsonObject = new JSONObject(); //"REQ_BODY":
        jsonObject.put("token",token);
        jsonObject.put("uniqueCode", followData.get("uniqueCode"));
        jsonObject.put("speechText", followData.get("speechText"));
        jsonObject.put("followText", followData.get("followText"));

        JSONObject reqJsonObj=new JSONObject();
        reqJsonObj.put("REQ_BODY",jsonObject);
        reqJsonObj.put("REQ_HEAD",null);

        String json= null;
        try {

            json = new String(reqJsonObj.toJSONString().getBytes("UTF-8"));

            final RequestParams requestParams=new RequestParams();
            requestParams.put("REQ_MESSAGE",json);

            Log.d(TAG, "judgeFollowUpResult (Request): "+json);

            AsyncHttpManager.get(baseUrl+ "IKFOLLOWUP.tran", requestParams, new StringHttpCallBack() {
                @Override
                public void onSuccess(int statusCode, String str) {
                    Log.d(TAG, "judgeFollowUpResult (Response) - onSuccess: " + str );

                    JSONObject object = JSON.parseObject(str);
                    FollowUpJudgeBean response=JSON.parseObject(object.toJSONString(),FollowUpJudgeBean.class);
                    followUpJudgeCallBack.FollowUpJudgeResult(response);
                }
                @Override
                public void onFailure(int statusCode, Throwable throwable, String error) {
                    Log.d(TAG, "judgeFollowUpResult (Response) - onFailure: " + error );

                    followUpJudgeCallBack.FollowUpJudgeFail(error);
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
