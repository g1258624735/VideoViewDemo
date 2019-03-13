package com.gxj.videoad.ui.fragment;

import android.content.Intent;

import com.gxj.videoad.ui.activity.ExplorePlayerActivity;
import com.gxj.videoad.ui.activity.VideoViewPlayerActivity;

/**
 * Explore local 本地视频播放
 */
public class ExploreLocalFragment extends LocalFragment {

    @Override
    protected String getTitle() {
        return "Explore本地视频播放";
    }

    public static ExploreLocalFragment getInstance() {
        return new ExploreLocalFragment();
    }

    protected void onVideoOnClick(String url) {
        Intent intent = new Intent(mContext, ExplorePlayerActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}
