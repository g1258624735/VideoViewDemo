package com.gxj.videoad.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gxj.videoad.R;
import com.gxj.videoad.base.BaseFragment;

/**
 * 首页 入口
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {
    @Override
    protected String getTitle() {
        return "首页";
    }

    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container) {
        return LayoutInflater.from(mContext).inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View v) {
        Button btnVideoLocal = v.findViewById(R.id.btn_video_local);//VideoView   播放本地视频
        Button btnVideoNet = v.findViewById(R.id.btn_video_net);//VideoView   播放网络视频
        Button btnExploreLocal = v.findViewById(R.id.btn_explore_local);// explore  播放本地视频
        Button btnExploreNet = v.findViewById(R.id.btn_explore_net);//explore   播放网络视频
        btnVideoLocal.setOnClickListener(this);
        btnVideoNet.setOnClickListener(this);
        btnExploreLocal.setOnClickListener(this);
        btnExploreNet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_video_local:
                startFragment(VideoViewLocalFragment.getInstance());
                break;
            case R.id.btn_video_net:
                startFragment(VideoViewNetFragment.getInstance());
                break;
            case R.id.btn_explore_local:
                startFragment(ExploreLocalFragment.getInstance());
                break;
            case R.id.btn_explore_net:
                startFragment(ExploreNetFragment.getInstance());
                break;
        }

    }
}
