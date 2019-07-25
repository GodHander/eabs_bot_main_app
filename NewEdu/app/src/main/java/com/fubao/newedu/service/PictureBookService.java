package com.fubao.newedu.service;

import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wulingbiao on 2019/6/27
 **/
public class PictureBookService {

    private static final String TAG = "PictureBookService";
    private ArrayList<JSONObject> bookLogicPageList = new ArrayList();
    private Map<String, String> courseState;

    private static PictureBookService instance;

    public static PictureBookService getInstance(){

        if(instance == null){
            synchronized (PictureBookService.class){
                if (instance == null){
                    instance = new PictureBookService();
                }
            }
        }
        return instance;
    }

    public PictureBookService(){
        this.bookLogicPageList.clear();
        try {
            this.courseState = new HashMap<>();
            this.courseState.put("taskId", "wMQOVPWT");
            this.courseState.put("taskName", "学知识");
            this.courseState.put("sceneId", "wMQOVPWT");
            this.courseState.put("sceneName", "学知识");
            this.courseState.put("coursewareId", "2485D66B64F0435fB34D5139864F4816");
            this.courseState.put("coursewareName", "我爸爸");
        } catch (Exception e) {

        }
    }

    /**
     * 初始化绘本内所有逻辑页信息
     */
    public void initBookLogicPageList() {

        this.bookLogicPageList.clear();

        try {
            // 绘本文件 - 逻辑页1
            JSONObject pageInfo1 = new JSONObject();
            pageInfo1.put("pageNO",1);
            pageInfo1.put("filePath","RobotPlayerDemo/book/MyFather/MyFatherPage1.sb3");
            pageInfo1.put("configFilePath","RobotPlayerDemo/book/MyFather/MyFatherPage1_Config.json");
            // 测试绘本文件 - 片段2
            JSONObject pageInfo2 = new JSONObject();
            pageInfo2.put("pageNO",2);
            pageInfo2.put("filePath","RobotPlayerDemo/book/MyFather/MyFatherPage2.sb3");
            pageInfo2.put("configFilePath","RobotPlayerDemo/book/MyFather/MyFatherPage2_Config.json");
            // 测试绘本文件 - 片段3
            JSONObject pageInfo3 = new JSONObject();
            pageInfo3.put("pageNO",3);
            pageInfo3.put("filePath","RobotPlayerDemo/book/MyFather/MyFatherPage3.sb3");
            pageInfo3.put("configFilePath","RobotPlayerDemo/book/MyFather/MyFatherPage3_Config.json");
            // 测试绘本文件 - 片段4
            JSONObject pageInfo4 = new JSONObject();
            pageInfo4.put("pageNO",4);
            pageInfo4.put("filePath","RobotPlayerDemo/book/MyFather/MyFatherPage4.sb3");
            pageInfo4.put("configFilePath","RobotPlayerDemo/book/MyFather/MyFatherPage4_Config.json");

            this.bookLogicPageList.add(pageInfo1);
            this.bookLogicPageList.add(pageInfo2);
            this.bookLogicPageList.add(pageInfo3);
            this.bookLogicPageList.add(pageInfo4);
        } catch (Exception e) {
            Log.e(TAG, "initBookSliceList异常: " + e.getMessage());
        }
    }

    public JSONObject getLogicPageInfoByNO (int pageNO) {
        if (pageNO > 0) {
            try {
                for (int index=0; index<this.bookLogicPageList.size(); index++ ) {
                    JSONObject temp = this.bookLogicPageList.get(index);
                    if (temp.getInt("pageNO") == pageNO) {
                        return temp;
                    }
                }
            } catch (Exception e) {
                Log.d(TAG,e.getMessage());
                return null;
            }
        }
        return null;
    }

    public ArrayList getBookLogicPageList() {
        return this.bookLogicPageList;
    }

    public Map<String, String> getCourseState() {
        return this.courseState;
    }

}
