package com.gxj.videoad.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.gxj.videoad.R;
import com.gxj.videoad.base.BaseFragmentActivity;
import com.gxj.videoad.widget.ExploreGroup;
import com.gxj.videoad.widget.VideoGroup;
import com.gxj.videoad.widget.XHGVideoPlayerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Explore 视频播放详情
 */
public class ExplorePlayerActivity extends BaseFragmentActivity {

    private ExploreGroup imgVideo;
    private String TAG = ExplorePlayerActivity.class.getSimpleName();
    private String url;

    @Override
    protected int getContextView() {
        return R.layout.activity_explore_player;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        imgVideo = findViewById(R.id.exo_view);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Explore视频播放详情");
        }
//        imgVideo.setLoading(true);
        imgVideo.initPlayer();
        imgVideo.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        imgVideo.setChangeState(new XHGVideoPlayerView.PlayState() {

            @Override
            public void onVideoChangeState(int state) {
                if (state == Player.STATE_ENDED) {//视频播放完成  需要判断是否需要更新视频播放地址
                    startVideo();
                } else if (state == Player.STATE_READY) {//视频播放开始
                    imgVideo.setImgThumbVisible(View.GONE);
                }
            }

            @Override
            public void onErrorState(ExoPlaybackException args) {
                Log.e(TAG, "video start error" + args.getMessage());
            }
        });
        startVideo();
    }

    /**
     * start Video
     */
    private void startVideo() {
        if (!TextUtils.isEmpty(url)) {
            List<String> list = new ArrayList<>();
            list.add(url);
            imgVideo.setData(list);
            imgVideo.playVideo(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        imgVideo.setPlayPause(false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        imgVideo.videoRelease();
    }
}
