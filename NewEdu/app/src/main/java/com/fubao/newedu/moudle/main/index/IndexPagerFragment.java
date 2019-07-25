package com.fubao.newedu.moudle.main.index;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fbhaedware.library.BDManager;
import com.fbhaedware.library.EduInterface;
import com.fbhaedware.library.face.FaceRectView;
import com.fubao.newedu.R;
import com.fubao.newedu.base.RxLazyFragment;
import com.fubao.newedu.service.HardwareService;

import butterknife.BindView;

public class IndexPagerFragment extends RxLazyFragment {

    private static final String TAG = "IndexPagerFragment";
    @BindView(R.id.iv_big_express)
    ImageView mIvBigExpress;
    @BindView(R.id.tv_tip)
    TextView mTvTip;
    @BindView(R.id.iv_charge_tip)
    ImageView mIvChargeTip;

    private int FaceStateOn = 0;
    private int FaceStateOff = 0;


    public static IndexPagerFragment newIntance() {
        return new IndexPagerFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_study_live;
    }

    @Override
    public void finishCreateView(View view, Bundle state) {
        checkPerson(); //检测人脸
    }

    @Override
    public void onResume() {
        super.onResume();

        hideChargeTip();
        mIvBigExpress.setVisibility(View.GONE);
        setBigExpress(R.drawable.bq);

        // Vip --- 设置唤醒提示
//        setTip(String.format("\"%s\"" + getResources().getString(R.string.wake_up_tip),
//                BuildConfig.WAKUP_STRING));

    }

    private void hideTip(){
        mTvTip.setVisibility(View.GONE);
        mTvTip.clearComposingText();
    }

    private void hideChargeTip() {
        mIvChargeTip.setVisibility(View.GONE);
    }

    private void setBigExpress(int id) {
        Glide.with(this).load(id).asGif().centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mIvBigExpress);
        mIvBigExpress.setVisibility(View.VISIBLE);
    }

    private void setTip(int id) {
        mTvTip.setVisibility(View.VISIBLE);
        mTvTip.setText(id);
    }

    private void setTip(String str) {
        mTvTip.setVisibility(View.VISIBLE);
        mTvTip.setText(str);
    }


    private void showChargeTip() {
        mIvChargeTip.setVisibility(View.VISIBLE);
    }

    private void checkPerson()
    {
        HardwareService.getInstance().registerFaceListener(new EduInterface.FaceCallBack() {
            @Override
            public void PersonComeOn(EduInterface.Person person) { //人来了
                    Log.d(TAG, "On: ");
                    FaceStateOn ++;
                    if(FaceStateOn == 50) {
                        setTip(getResources().getString(R.string.wake_up_tip));
                        FaceStateOff = 0;
                    }
            }

            @Override
            public void PersonComeOff(EduInterface.Person person) { //人走了
                    Log.d(TAG, "Off: ");
                    FaceStateOff ++;
                    if(FaceStateOff == 30) {
                        hideTip();
                        FaceStateOn = 0;
                    }


            }
        });
    }


}
