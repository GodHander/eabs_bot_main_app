package com.fbhaedware.library.answerdb.bean;

/**
 * zhangy
 * Created by 1 on 2018/8/28.
 */

public interface RenewTokenCallBack {

    void renewalTokenSuc(String token, String effectiveTime);

    void renewalTokenFail(String failReason);

}
