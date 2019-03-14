package com.gxj.videoad.ui.activity;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;

import com.gxj.videoad.R;
import com.gxj.videoad.base.BaseFragmentActivity;
import com.gxj.videoad.ui.fragment.HomeFragment;
import com.gxj.videoad.widget.VideoGroup;

/**
 * VideoView 视频播放详情
 * @author Administrator-pc
 */
public class VideoViewPlayerActivity extends BaseFragmentActivity {

    private VideoGroup imgVideo;
    private String TAG = VideoViewPlayerActivity.class.getSimpleName();
    private String url;
    private MediaController mController;

    @Override
    protected int getContextView() {
        return R.layout.activity_video_player;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        mController = new MediaController(this);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        imgVideo = findViewById(R.id.video_view);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("VideoView视频播放详情");
        }
        imgVideo.setLoading(true);
        //视频准备
        imgVideo.getVideoView().setOnPreparedListener(mediaPlayer -> {
            imgVideo.setStartViewVisible(View.GONE);
            imgVideo.getVideoView().start();
            imgVideo.postDelayed(() -> {
                imgVideo.setLoading(false);
            }, 200);
        });
        //视频结束
        imgVideo.getVideoView().setOnCompletionListener(mp -> {
            if (mp != null) {
                mp.setDisplay(null);
                mp.reset();
                mp.setDisplay(imgVideo.getVideoView().getHolder());
            }
            imgVideo.setStartViewVisible(View.VISIBLE);
        });
        //视频异常
        imgVideo.getVideoView().setOnErrorListener((mp, what, extra) -> {
            stopPlaybackVideo();
            Log.e(TAG, "video start error");
            return true;
        });
        imgVideo.getStartView().setOnClickListener(v -> startVideo());
        startVideo();
    }

    /**
     * start Video
     */
    private void startVideo() {
        if (!TextUtils.isEmpty(url)) {
            imgVideo.getVideoView().setVideoPath(url);
            imgVideo.getVideoView().setMediaController(mController);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (imgVideo.getVideoView().canPause()) {
            imgVideo.getVideoView().pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlaybackVideo();
    }

    private void stopPlaybackVideo() {
        try {
            imgVideo.getVideoView().stopPlayback();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
