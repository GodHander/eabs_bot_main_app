package com.fubao.newedu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.fbhaedware.library.EduInterface;
import com.fbhaedware.library.answerdb.bean.TranslationWordBean;
import com.fbhaedware.library.face.FaceRectView;
import com.fubao.newedu.base.RxBaseActivity;
import com.fubao.newedu.moudle.entry.MisucFragment;
import com.fubao.newedu.moudle.entry.study.StudyFragment;
import com.fubao.newedu.moudle.entry.study.SuperWebX5Fragment;
import com.fubao.newedu.moudle.main.MainPageFragment;
import com.fubao.newedu.service.HardwareService;
import com.fubao.newedu.utils.ToastUtil;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends RxBaseActivity implements NavigationView.OnNavigationItemSelectedListener, EduInterface.WakeUpCallBack,
                                                            EduInterface.AsrCallBack {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.texture_preview)
    View previewView;
    @BindView(R.id.face_rect_view)
    FaceRectView faceRectView;

    private static final String TAG = "MainActivity";
    private Fragment[] fragments;
    private int currentTabIndex;
    private int index;
    private long exitTime;
    private MainPageFragment mMainPageFragment;
    private StudyFragment studyFragment;
    private Bundle mBundle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        //初始化Fragment
        initFragments();
        //初始化侧滑菜单
        initNavigationView();
        //初始化能力服务
        startBaseService();
    }


    private void startBaseService() {
        // 初始化服务协议格式
        HardwareService.getInstance().getToken();
        // 开启人脸识别
        HardwareService.getInstance().startFace(this,previewView,faceRectView);
        // 开启语音识别
        HardwareService.getInstance().startAsr();
        HardwareService.getInstance().registerAsrListener(this);

//        HardwareService.getInstance().startWakeUp();
//        HardwareService.getInstance().registerWakeUpListener(this);
    }

    /**
     * 初始化NavigationView
     */
    private void initNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(this);
        View headerView = mNavigationView.getHeaderView(0);
    }

    private void initFragments() {
        mMainPageFragment = MainPageFragment.newInstance();
        studyFragment = StudyFragment.newInstance(mBundle = new Bundle());
        MisucFragment misucFragment = MisucFragment.newInstance();
        fragments = new Fragment[]{
                mMainPageFragment,
                studyFragment,
                misucFragment
        };
        // 添加显示第一个fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, mMainPageFragment)
                .show(mMainPageFragment).commit();
    }

    @Override
    public void initToolBar() {
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.item_study:
                changeFragmentIndex(item, 0);
                return true;
            case R.id.item_game:
                changeFragmentIndex(item, 1);
                mBundle.putString(SuperWebX5Fragment.URL_KEY, "file:///android_asset/RobotPlayerDemo/robot-player.html");
                return true;
            case R.id.item_music:
                changeFragmentIndex(item, 2);
                return true;
        }
        return false;
    }


    /**
     * 切换Fragment的下标
     */
    private void changeFragmentIndex(MenuItem item, int currentIndex) {
        index = currentIndex;
        switchFragment();
        item.setChecked(true);
    }


    /**
     * Fragment切换
     */
    private void switchFragment() {
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        trx.hide(fragments[currentTabIndex]);
        if (!fragments[index].isAdded()) {
            trx.add(R.id.container, fragments[index]);
        }
        trx.show(fragments[index]).commit();
        currentTabIndex = index;
    }

    /**
     * Fragment切换
     */
    private void switchFragment(int index) {
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        trx.hide(fragments[currentTabIndex]);
        if (!fragments[index].isAdded()) {
            trx.add(R.id.container, fragments[index]);
        }
        trx.show(fragments[index]).commit();
        currentTabIndex = index;
    }


    /**
     * DrawerLayout侧滑菜单开关
     */
    public void toggleDrawer() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    /**
     * 监听back键处理DrawerLayout和SearchView
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mDrawerLayout.isDrawerOpen(mDrawerLayout.getChildAt(1))) {
                mDrawerLayout.closeDrawers();
            } else {
                exitApp();
            }
        }
        return true;
    }

    /**
     * 双击退出App
     */
    private void exitApp() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtil.ShortToast("再按一次退出");
            exitTime = System.currentTimeMillis();
            switchFragment(0);
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        HardwareService.getInstance().destory();
        super.onDestroy();
    }

    @Override
    public void WakeUpSuccess() {
//        HardwareService.getInstance().startAsr();
        HardwareService.getInstance().startTts("你好",null);
    }

    @Override
    public void WakeUpFailed() {

    }

    @Override
    public void WakeUpStop() {
//        HardwareService.getInstance().stopAsr();
    }

    @Override
    public void WakeUpStart() {

    }

    @Override
    public void onGetAsrSuccess(String result) {
        // 语音识别成功 - 未连接语义库
        Log.e(TAG, "onGetAsrSuccess -----> "+result);

//        if(result.equals("再见")){
//            HardwareService.getInstance().startTts("再见",null);
//            HardwareService.getInstance().stopAsr();
//        }
    }

    @Override
    public void onGetAnswer(String result, TranslationWordBean response) {
        if(response != null){
            if (response.getREP_HEAD().getTRAN_CODE().equals("000000")) {
                String robotSpeak = response.getREP_BODY().getSceneSet().get(0).getSpeakContent();
                List<String> commands = response.getREP_BODY().getSceneSet().get(0).getCommands();
                this.sendAsrResultToPlayer(0,"", result, robotSpeak, commands);
            } else {
                this.sendAsrResultToPlayer(1, "response数据为空!", result, null, null);
            }
        } else {
            this.sendAsrResultToPlayer(1, response.getREP_HEAD().getTRAN_RSPMSG(), result, null, null);
        }
    }

    @Override
    public void onGetAnswerFail(String result, String failReason) {
        this.sendAsrResultToPlayer(1, failReason, result, null, null);
    }

    @Override
    public void onGetAsrFailed(String reason) {
        this.sendAsrResultToPlayer(2, reason, null, null, null);
    }

    private void sendAsrResultToPlayer (int code, String failReason, String customerSaid, String speakContent, List<String> commands) {

        // 如果当前显示非绘本页
        if (studyFragment == this.fragments[currentTabIndex]) {
            try {
                JSONObject resultInfo = new JSONObject();
                resultInfo.put("customerSaid", customerSaid);
                resultInfo.put("speakContent", speakContent);
                resultInfo.put("commands", commands);

                JSONObject backData = new JSONObject();
                backData.put("resultCode", code);
                backData.put("failReason", failReason);
                backData.put("result", resultInfo);

                Log.d(TAG, "向Player发送语义识别结果事件 sendAsrResultToApp -----> " + code);

                studyFragment.sendAppEventToPlayer("asrResultEvent", backData.toString());
            } catch (Exception e) {
                Log.d(TAG,e.getMessage());
            }
        }

    }
}
