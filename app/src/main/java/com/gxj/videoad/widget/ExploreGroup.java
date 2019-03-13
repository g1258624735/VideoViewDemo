package com.gxj.videoad.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gxj.videoad.R;

import java.util.List;

/**
 * @author gxj
 * @ description: 自定义Exploer增加 加载前默认图
 * @ date :2018/9/14 11:19
 * 谷歌专业视频解码器类库
 */
public class ExploreGroup extends LinearLayout {
    private ImageView imgThumb;
    private ImageView startView;
    private XHGVideoPlayerView videoView;
    private View videoBg;
    private TextView tvRestart;

    public ExploreGroup(Context context) {
        super(context);
        initView(context);
    }

    public ExploreGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ExploreGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.explore_group_layout, this, true);
        videoView = findViewById(R.id.video_view);
        startView = findViewById(R.id.img_start_video);
        imgThumb = findViewById(R.id.img_thumb);
        videoBg = findViewById(R.id.help_video_bg);
        tvRestart = findViewById(R.id.tv_restart);
    }

    /**
     * 初始化视频
     */
    public void initPlayer() {
        videoView.initPlayer();
    }

    /**
     * 视频生命周期控制
     */
    public void setChangeState(XHGVideoPlayerView.PlayState playState) {
        videoView.setChangeState(playState);
    }

    /**
     * 视频遮罩背景的点击事件监听
     */
    public void setBgOnClickListener(OnClickListener onClickListener) {
        videoBg.setOnClickListener(onClickListener);
    }

    /**
     * 初始化视频 尺寸
     */
    public void setResizeMode(int resizeMode) {
        videoView.setResizeMode(resizeMode);
    }

    /**
     * 开始播放视频
     *
     * @param isLoopPlay 是否循环播放
     */
    public void playVideo(boolean isLoopPlay) {
        videoView.playVideo(isLoopPlay);
    }

    /**
     * 设置播放视频数据
     */
    public void setData(List<String> data) {
        if (videoView != null) {
            videoView.setData(data);
        }
    }

    /**
     * 视频资源释放
     */
    public void videoRelease() {
        videoView.videoRelease();
    }

    /**
     * 是否暂停视频播放
     */
    public void setPlayPause(boolean isPlay) {
        videoView.setPlayPause(isPlay);
    }

    /**
     * 开始播放视频按钮注册监听事件
     */
    public void setStartBtnOnClickListener(OnClickListener onClickListener) {
        startView.setOnClickListener(onClickListener);
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

    public ImageView getImgThumb() {
        return imgThumb;
    }

    /**
     * 图片路径
     *
     * @param res res
     */
    public void setImgThumb(int res) {
        imgThumb.setVisibility(VISIBLE);
        imgThumb.setImageResource(res);
    }

    /**
     * 图片bitmap
     *
     * @param bitmap bitmap
     */
    public void setImgThumb(Bitmap bitmap) {
        imgThumb.setVisibility(VISIBLE);
        imgThumb.setImageBitmap(bitmap);
    }

    /**
     * 默认图是否可见
     */
    public void setImgThumbVisible(int visible) {
        imgThumb.setVisibility(visible);
    }
}
