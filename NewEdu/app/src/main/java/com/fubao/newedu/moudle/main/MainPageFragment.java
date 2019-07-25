package com.fubao.newedu.moudle.main;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.fubao.newedu.R;
import com.fubao.newedu.adapter.pager.HomePagerAdapter;
import com.fubao.newedu.base.RxLazyFragment;

import butterknife.BindView;


public class MainPageFragment extends RxLazyFragment {

    private static final String TAG = "MainPageFragment";

    @BindView(R.id.view_pager)
    ViewPager mViewPager;


    public static MainPageFragment newInstance(){
        return new MainPageFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_study_pager;
    }

    @Override
    public void finishCreateView(View view, Bundle state) {
        initViewPager();

    }

    private void initViewPager() {
        HomePagerAdapter mHomeAdapter = new HomePagerAdapter(getChildFragmentManager(), getApplicationContext());
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mHomeAdapter);
        mViewPager.setCurrentItem(0);
    }



}
