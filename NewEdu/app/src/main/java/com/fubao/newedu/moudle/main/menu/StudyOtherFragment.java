package com.fubao.newedu.moudle.main.menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.fubao.newedu.R;
import com.fubao.newedu.base.RxLazyFragment;

public class StudyOtherFragment extends RxLazyFragment {


    public static Fragment newInstance() {
        return new StudyOtherFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_study_other;
    }

    @Override
    public void finishCreateView(View view, Bundle state) {

    }
}
