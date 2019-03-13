package com.gxj.videoad.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.gxj.videoad.R;


/**
 * @author gxj
 * @ description: 自定义VideoView 增加 加载前默认图
 * @ date :2018/9/14 11:19
 */
public class VideoGroup extends LinearLayout {
    private ProgressBar pbLoading;
    private ImageView startView;
    private VideoView videoView;
    private View videoBg;
    private TextView tvRestart;

    public VideoGroup(Context context) {
        super(context);
        initView(context);
    }

    public VideoGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public VideoGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.video_group_layout, this, true);
        videoView = findViewById(R.id.video_root_view);
        startView = findViewById(R.id.img_start_video);
        pbLoading = findViewById(R.id.pb_loading);
        videoBg = findViewById(R.id.help_video_bg);
        tvRestart = findViewById(R.id.tv_restart);
    }

    public VideoView getVideoView() {
        return videoView;
    }

    public ImageView getStartView() {
        return startView;
    }

    public ProgressBar getImgThumb() {
        return pbLoading;
    }

    /**
     * 视频结束
     * 试图切换
     */
    public void setVideoLayoutVisible(int isVisible) {
        startView.setVisibility(isVisible);
        videoBg.setVisibility(isVisible);
        tvRestart.setVisibility(isVisible);
    }

    /**
     * 获取视频遮罩
     */
    public View getBg() {
        return videoBg;
    }

    /**
     * 加载中。。。
     */
    public void setLoading(boolean isLoading) {
        pbLoading.setVisibility(isLoading ? VISIBLE : GONE);
    }


    /**
     * 重播按钮是否可见
     */
    public void setStartViewVisible(int visible) {
        startView.setVisibility(visible);
    }
}
