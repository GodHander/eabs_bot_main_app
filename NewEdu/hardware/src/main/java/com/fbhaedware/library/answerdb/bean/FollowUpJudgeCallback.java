package com.fbhaedware.library.answerdb.bean;

/**
 * Created by wulingbiao on 2019/7/17
 **/
public interface FollowUpJudgeCallback {

    void FollowUpJudgeResult(FollowUpJudgeBean response);

    void FollowUpJudgeFail(String failReason);

}
