package com.gxj.videoad.ui.fragment;

import android.content.Intent;

import com.gxj.videoad.ui.activity.ExplorePlayerActivity;

/**
 * VideoView 网络视频播放
 * @author Administrator-pc
 */
public class VideoViewNetFragment extends BaseNetFragment {

    @Override
    protected String getTitle() {
        return "VideoView网络视频播放";
    }

    public static VideoViewNetFragment getInstance() {
        return new VideoViewNetFragment();
    }

    @Override
    protected void onVideoOnClick(String url) {
        Intent intent = new Intent(mContext, ExplorePlayerActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}
