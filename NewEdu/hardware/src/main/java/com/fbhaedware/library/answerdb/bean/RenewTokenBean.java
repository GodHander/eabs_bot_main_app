package com.fbhaedware.library.answerdb.bean;

/**
 * zhangy
 * Created by 1 on 2018/8/28.
 */

public class RenewTokenBean {

    /**
     * REP_HEAD : {"TRAN_CODE":"000000","TRAN_RSPMSG":"成功"}
     * REP_BODY : {"token":"26f9f5352ad1417b96479aca0aca594f","effectiveTime":"201808281620"}
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
        /**
         * token : 26f9f5352ad1417b96479aca0aca594f
         * effectiveTime : 201808281620
         */

        private String token;
        private String effectiveTime;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getEffectiveTime() {
            return effectiveTime;
        }

        public void setEffectiveTime(String effectiveTime) {
            this.effectiveTime = effectiveTime;
        }
    }
}
