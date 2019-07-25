package com.fbhaedware.library.answerdb.bean;

/**
 * 智能知识库处理结果回调
 * zhangy
 * Created by 1 on 2018/8/27.
 */

public interface GetTokenCallBack {

    void getTokenSuc(String token, String effectiveTime);

    void getTokenFail(String failReason);

}
