package com.gxj.videoad.ui.activity;

import android.os.Bundle;

import com.gxj.videoad.R;
import com.gxj.videoad.base.BaseFragmentActivity;
import com.gxj.videoad.ui.fragment.HomeFragment;

/**
 * @author Administrator-pc
 */
public class MainActivity extends BaseFragmentActivity {

    @Override
    protected int getContextView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startFragment(new HomeFragment());
    }
}
