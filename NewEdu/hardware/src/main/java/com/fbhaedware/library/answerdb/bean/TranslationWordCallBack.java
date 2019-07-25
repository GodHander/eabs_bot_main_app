package com.fbhaedware.library.answerdb.bean;

/**
 * zhangy
 * Created by 1 on 2018/8/28.
 */

public interface TranslationWordCallBack {

    void TranslationResult(TranslationWordBean response);

    void TranslationFail(String failReason);
}
