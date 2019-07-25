package com.fbhaedware.library.answerdb.bean;

import java.util.List;

/**
 * Created by wulingbiao on 2019/7/17
 **/
public class FollowUpJudgeBean {
    /**
     * REP_HEAD : {"TRAN_CODE":"000000","TRAN_RSPMSG":"成功"}
     * REP_BODY : {"speechText":"XXXX","followText":"YYYY","uniqueCode":"sDH78fs7", "score":"0.8","pYspeechTexty":"RRRRRR", "pYfollowText":"ZZZZ"}
     */

    private REPHEADBean REP_HEAD;
    private REPBODYBean REP_BODY;

    public REPHEADBean getREP_HEAD() {
        return REP_HEAD;
    }

    public void setREP_HEAD(REPHEADBean REP_HEAD) {
        this.REP_HEAD = REP_HEAD;
    }

    public REPBODYBean getREP_BODY() {
        return REP_BODY;
    }

    public void setREP_BODY(REPBODYBean REP_BODY) {
        this.REP_BODY = REP_BODY;
    }

    public static class REPHEADBean {
        /**
         * TRAN_CODE : 000000
         * TRAN_RSPMSG : 成功
         */

        private String TRAN_CODE;
        private String TRAN_RSPMSG;

        public String getTRAN_CODE() {
            return TRAN_CODE;
        }

        public void setTRAN_CODE(String TRAN_CODE) {
            this.TRAN_CODE = TRAN_CODE;
        }

        public String getTRAN_RSPMSG() {
            return TRAN_RSPMSG;
        }

        public void setTRAN_RSPMSG(String TRAN_RSPMSG) {
            this.TRAN_RSPMSG = TRAN_RSPMSG;
        }
    }

    public static class REPBODYBean {

        private String uniqueCode;
        private String score;
        private String speechText;
        private String followText;
        private String pYspeechTexty;
        private String pYfollowText;

        public String getUniqueCode() {
            return uniqueCode;
        }

        public String getScore() {
            return score;
        }

        public String getSpeechText() {
            return speechText;
        }

        public String getFollowText() {
            return followText;
        }

        public String getpYspeechTexty() {
            return pYspeechTexty;
        }

        public String getpYfollowText() {
            return pYfollowText;
        }

        public void setUniqueCode(String uniqueCode) {
            this.uniqueCode = uniqueCode;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public void setSpeechText(String speechText) {
            this.speechText = speechText;
        }

        public void setFollowText(String followText) {
            this.followText = followText;
        }

        public void setpYspeechTexty(String pYspeechTexty) {
            this.pYspeechTexty = pYspeechTexty;
        }

        public void setpYfollowText(String pYfollowText) {
            this.pYfollowText = pYfollowText;
        }
    }
}
