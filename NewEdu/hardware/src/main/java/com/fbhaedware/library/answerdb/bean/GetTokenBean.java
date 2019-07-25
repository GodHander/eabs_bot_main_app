package com.fbhaedware.library.answerdb.bean;

/**
 * 获取token的对象封装
 * zhangy
 * Created by 1 on 2018/8/27.
 */

public class GetTokenBean {

    /**
     * REP_HEAD : {"TRAN_CODE":"000000","TRAN_RSPMSG":"成功"}
     * REP_BODY : {"feedback":{"faceId":null},"token":"e7cd13c21e49cf1cbb827a1aee821864","AISESSIONID":"246084f7-6d9a-468c-974c-266bafe9b569","error_code":0,"effectiveTime":"201808271441","customer":{"type":"UNKNOWN","uid":null,"name":null,"sex":null,"age":null,"faceliveness":true},"error_msg":""}
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
         * token : e7cd13c21e49cf1cbb827a1aee821864
         * effectiveTime : 201808271441
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
