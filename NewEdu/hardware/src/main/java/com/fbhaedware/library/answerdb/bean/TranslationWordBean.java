package com.fbhaedware.library.answerdb.bean;


import java.util.List;

/**
 * zhangy
 * Created by 1 on 2018/8/28.
 */

public class TranslationWordBean {


    /**
     * REP_HEAD : {"TRAN_CODE":"000000","TRAN_RSPMSG":"成功"}
     * REP_BODY : {"sceneSet":[{"code":100,"serialNo":135628,"relevant":1,"question":null,"speakContent":"请咨询导诊台。","parameterSet":null,"contentFlag":null,"contentType":null,"regularSpeakC":null,"commands":[],"files":[],"showContent":null,"recommendQs":null,"buttonContents":null,"otherContent":{}}],"sceneCount":1,"token":"26f9f5352ad1417b96479aca0aca594f","robotState":"OnService","customerSaid":"123","uniqueCode":1535436139182,"termId":"00000256"}
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
         * sceneSet : [{"code":100,"serialNo":135628,"relevant":1,"question":null,"speakContent":"请咨询导诊台。","parameterSet":null,"contentFlag":null,"contentType":null,"regularSpeakC":null,"commands":[],"files":[],"showContent":null,"recommendQs":null,"buttonContents":null,"otherContent":{}}]
         * sceneCount : 1
         * token : 26f9f5352ad1417b96479aca0aca594f
         * robotState : OnService
         * customerSaid : 123
         * uniqueCode : 1535436139182
         * termId : 00000256
         */

        private int sceneCount;
        private String token;
        private String robotState;
        private String customerSaid;
        private long uniqueCode;
        private String termId;
        private List<SceneSetBean> sceneSet;

        public int getSceneCount() {
            return sceneCount;
        }

        public void setSceneCount(int sceneCount) {
            this.sceneCount = sceneCount;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getRobotState() {
            return robotState;
        }

        public void setRobotState(String robotState) {
            this.robotState = robotState;
        }

        public String getCustomerSaid() {
            return customerSaid;
        }

        public void setCustomerSaid(String customerSaid) {
            this.customerSaid = customerSaid;
        }

        public long getUniqueCode() {
            return uniqueCode;
        }

        public void setUniqueCode(long uniqueCode) {
            this.uniqueCode = uniqueCode;
        }

        public String getTermId() {
            return termId;
        }

        public void setTermId(String termId) {
            this.termId = termId;
        }

        public List<SceneSetBean> getSceneSet() {
            return sceneSet;
        }

        public void setSceneSet(List<SceneSetBean> sceneSet) {
            this.sceneSet = sceneSet;
        }

        public static class SceneSetBean {
            /**
             * code : 100
             * serialNo : 135628
             * relevant : 1
             * question : null
             * speakContent : 请咨询导诊台。
             * parameterSet : null
             * contentFlag : null
             * contentType : null
             * regularSpeakC : null
             * commands : []
             * files : []
             * showContent : null
             * recommendQs : null
             * buttonContents : null
             * otherContent : {}
             */

            private int code;
            private int serialNo;
            private int relevant;
            private Object question;
            private String speakContent;
            private Object parameterSet;
            private Object contentFlag;
            private Object contentType;
            private Object regularSpeakC;
            private Object showContent;
            private Object recommendQs;
            private Object buttonContents;
            private OtherContentBean otherContent;
            private List<String> commands;
            private List<?> files;

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }

            public int getSerialNo() {
                return serialNo;
            }

            public void setSerialNo(int serialNo) {
                this.serialNo = serialNo;
            }

            public int getRelevant() {
                return relevant;
            }

            public void setRelevant(int relevant) {
                this.relevant = relevant;
            }

            public Object getQuestion() {
                return question;
            }

            public void setQuestion(Object question) {
                this.question = question;
            }

            public String getSpeakContent() {
                return speakContent;
            }

            public void setSpeakContent(String speakContent) {
                this.speakContent = speakContent;
            }

            public Object getParameterSet() {
                return parameterSet;
            }

            public void setParameterSet(Object parameterSet) {
                this.parameterSet = parameterSet;
            }

            public Object getContentFlag() {
                return contentFlag;
            }

            public void setContentFlag(Object contentFlag) {
                this.contentFlag = contentFlag;
            }

            public Object getContentType() {
                return contentType;
            }

            public void setContentType(Object contentType) {
                this.contentType = contentType;
            }

            public Object getRegularSpeakC() {
                return regularSpeakC;
            }

            public void setRegularSpeakC(Object regularSpeakC) {
                this.regularSpeakC = regularSpeakC;
            }

            public Object getShowContent() {
                return showContent;
            }

            public void setShowContent(Object showContent) {
                this.showContent = showContent;
            }

            public Object getRecommendQs() {
                return recommendQs;
            }

            public void setRecommendQs(Object recommendQs) {
                this.recommendQs = recommendQs;
            }

            public Object getButtonContents() {
                return buttonContents;
            }

            public void setButtonContents(Object buttonContents) {
                this.buttonContents = buttonContents;
            }

            public OtherContentBean getOtherContent() {
                return otherContent;
            }

            public void setOtherContent(OtherContentBean otherContent) {
                this.otherContent = otherContent;
            }

            public List<String> getCommands() {
                return commands;
            }

            public void setCommands(List<String> commands) {
                this.commands = commands;
            }

            public List<?> getFiles() {
                return files;
            }

            public void setFiles(List<?> files) {
                this.files = files;
            }

            public static class OtherContentBean {
            }
        }
    }
}
