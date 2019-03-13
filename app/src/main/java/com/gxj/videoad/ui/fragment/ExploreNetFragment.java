package com.gxj.videoad.ui.fragment;

import android.content.Intent;

import com.gxj.videoad.ui.activity.VideoViewPlayerActivity;

/**
 *  Explore 网络视频播放
 */
public class ExploreNetFragment extends NetFragment {

    @Override
    protected String getTitle() {
        return " Explore网络视频播放";
    }

    public static ExploreNetFragment getInstance() {
        return new ExploreNetFragment();
    }

    protected void onVideoOnClick(String url) {
        Intent intent = new Intent(mContext, VideoViewPlayerActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}
