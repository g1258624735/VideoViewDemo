package com.gxj.videoad.ui.fragment;

import android.content.Intent;

import com.gxj.videoad.ui.activity.VideoViewPlayerActivity;

/**
 * VideoView local 本地视频播放
 */
public class VideoViewLocalFragment extends LocalFragment {

    @Override
    protected String getTitle() {
        return "VideoView本地视频播放";
    }

    public static VideoViewLocalFragment getInstance() {
        return new VideoViewLocalFragment();
    }

    protected void onVideoOnClick(String url) {
        Intent intent = new Intent(mContext, VideoViewPlayerActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}
