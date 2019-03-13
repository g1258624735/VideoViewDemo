package com.gxj.videoad.ui.fragment;

import android.content.Intent;

import com.gxj.videoad.ui.activity.ExplorePlayerActivity;
import com.gxj.videoad.ui.activity.VideoViewPlayerActivity;

/**
 * VideoView 网络视频播放
 */
public class VideoViewNetFragment extends NetFragment {

    @Override
    protected String getTitle() {
        return "VideoView网络视频播放";
    }

    public static VideoViewNetFragment getInstance() {
        return new VideoViewNetFragment();
    }

    protected void onVideoOnClick(String url) {
        Intent intent = new Intent(mContext, ExplorePlayerActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}
