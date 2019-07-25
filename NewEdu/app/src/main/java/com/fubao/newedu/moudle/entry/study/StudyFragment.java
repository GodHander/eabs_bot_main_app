package com.fubao.newedu.moudle.entry.study;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fubao.newedu.R;
import com.fubao.newedu.service.PictureBookService;
import com.fubao.newedu.utils.FileUtlis;
import com.fubao.newedu.widget.CustomEmptyView;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;

public class StudyFragment extends SuperWebX5Fragment {

    @BindView(R.id.empty_view)
    CustomEmptyView mCustomEmptyView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private boolean IsLoadFinsh = true;

    public static final StudyFragment newInstance(Bundle bundle){

        StudyFragment mJsSuperWebFragment = new StudyFragment();
        if (bundle != null) {
            mJsSuperWebFragment.setArguments(bundle);
        }
        return mJsSuperWebFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        LinearLayout mLinearLayout = (LinearLayout) view;
        LayoutInflater.from(getContext()).inflate(R.layout.fragment_js, mLinearLayout, true);
        super.onViewCreated(view, savedInstanceState);

        Log.i("Info", "add android:" + mSuperWebX5);
        if (mSuperWebX5 != null) {
            mSuperWebX5.getJsInterfaceHolder().addJavaObject("android", new AndroidInterface(mSuperWebX5, this.getActivity()));
        }

        // test - 使用测试绘本
        PictureBookService.getInstance().initBookLogicPageList();

        view.findViewById(R.id.one).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.two).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.three).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.four).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.five).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.evt_personCome).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.evt_personLeave).setOnClickListener(mOnClickListener);

        mSuperWebX5.getWebCreator().get().setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress == 100 && IsLoadFinsh == true) {
                    //加载完成
                    try {
                        JSONObject pageInfo = PictureBookService.getInstance().getLogicPageInfoByNO(1);
                        if (pageInfo != null) {
                            String fromAssets2 = FileUtlis.getFromAssets(getContext(), pageInfo.getString("filePath"), true);
                            String configString = FileUtlis.getFromAssets(getContext(), pageInfo.getString("configFilePath"), false);
                            Log.d(TAG, "初始化首页(配置数据): " + configString);
                            mSuperWebX5.getJsEntraceAccess().quickCallJs("callJsMethod","loadNewProjectFile",getPlayJson(true, 1, configString), fromAssets2);
                            IsLoadFinsh = false;
                            Log.d(TAG, "onProgressChanged: 111");
                        }
                    } catch (Exception e) {
                        Log.d(TAG,e.getMessage());
                    }
                }
            }
        });

    }


    private View.OnClickListener mOnClickListener = v -> {

        switch (v.getId()) {
            case R.id.one://手动播放指定文件
//                String fromAssets1 = FileUtlis.getFromAssets(getContext(), "PlayerAppDemo/img/PlayerEvtTest.sb3", true);
//                mSuperWebX5.getJsEntraceAccess().quickCallJs("callJsMethod","loadNewProjectFile",getPlayJson(false,1, 4), fromAssets1);
                break;

            case R.id.two: //自动播放文件
//                String fromAssets2 = FileUtlis.getFromAssets(getContext(), "PlayerAppDemo/img/PlayerEvtTest.sb3", true);
//                mSuperWebX5.getJsEntraceAccess().quickCallJs("callJsMethod","loadNewProjectFile",getPlayJson(true, 1, 4), fromAssets2);
                break;
            case R.id.three: //开始播放
                mSuperWebX5.getJsEntraceAccess().quickCallJs("callJsMethod","playStart","");
                break;
            case R.id.four: //停止播放
                mSuperWebX5.getJsEntraceAccess().quickCallJs("callJsMethod", "playStop","");
                break;
            //-----------------------------------------------------------------------------------------------------------
            case R.id.five: //自选文件
                //todo
//                String fromAssets = FileUtlis.getFromAssets(getContext(), "PlayerAppDemo/img/ScratchFuncTest.sb3", true);
//                mSuperWebX5.getJsEntraceAccess().quickCallJs("callJsMethod","loadNewProjectFile", getPlayJson(true, 1, 4), fromAssets);

                break;
            //---------------------------------------------------------------------------------------------------------
            case R.id.evt_personCome: //发现人来
                try {
                    JSONObject mJSONObject = new JSONObject();
                    mJSONObject.put("name", "马云");
                    mJSONObject.put("identity", "老师");
                    sendAppEventToPlayer("personFindEvent", mJSONObject.toString());
                } catch (Exception e) {
                    Log.d(TAG,e.getMessage());
                }
                break;
            case R.id.evt_personLeave: //发现人走
                sendAppEventToPlayer("personDisappearEvent", "");
                break;
            default:
                break;
        }
    };

    /**
     * 推送事件到前端播放器Player
     * @param evtName 事件名
     * @param jsonString 事件携带参数(Json格式字符串)
     */
    public void sendAppEventToPlayer (String evtName, String jsonString) {

        Log.d(TAG, "推送事件到播放器 eventName: " + evtName + " -- param: " + jsonString);

        try {
            this.mSuperWebX5.getJsEntraceAccess().quickCallJs("appEventHandler", evtName, jsonString);
        } catch (Exception e) {
            Log.d(TAG, "推送事件到播放器时异常: " + e.getMessage());
        }
    }

    private String getPlayJson(boolean flag, int type, String configString) {
        String result = "";
        try {
            JSONObject mJSONObject = new JSONObject();
            mJSONObject.put("autoPlay", flag);
            mJSONObject.put("type", type);
            mJSONObject.put("configString", configString);
            result = mJSONObject.toString();
        } catch (Exception e) {

        }
        return result;
    }

}
