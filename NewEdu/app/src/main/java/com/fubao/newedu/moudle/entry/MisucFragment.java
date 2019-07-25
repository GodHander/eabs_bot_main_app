package com.fubao.newedu.moudle.entry;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.fubao.newedu.MainActivity;
import com.fubao.newedu.R;
import com.fubao.newedu.base.RxLazyFragment;
import com.fubao.newedu.widget.CustomEmptyView;

import butterknife.BindView;

public class MisucFragment extends RxLazyFragment {

    @BindView(R.id.empty_view)
    CustomEmptyView mCustomEmptyView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static MisucFragment newInstance(){
        return new MisucFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_empty;
    }

    @Override
    public void finishCreateView(View view, Bundle state) {
        mToolbar.setTitle("音乐测试");
        mToolbar.setNavigationIcon(R.drawable.ic_launcher_background);
        mToolbar.setNavigationOnClickListener(v -> {
            Activity activity1 = getActivity();
            if (activity1 instanceof MainActivity) {
                ((MainActivity) activity1).toggleDrawer();
            }
        });
        mCustomEmptyView.setEmptyImage(R.drawable.ic_launcher_background);
        mCustomEmptyView.setEmptyText("暂时还没有实现哟");
    }
}
