package com.gxj.videoad.widget;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.gxj.videoad.R;
import com.gxj.videoad.utils.Mp4ExtractorsFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * explayer 视频播放器
 */
public class XHGVideoPlayerView extends LinearLayout {

    private static final String TAG = "HalfVideoFrag";

    private Context mContext;

    private PlayerView exoPlayerView;
    private SimpleExoPlayer exoPlayer;
    private View rootView;

    private List<String> urls = new ArrayList<>();

    public XHGVideoPlayerView(Context context) {
        super(context);
        initView(context);
    }

    public XHGVideoPlayerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public XHGVideoPlayerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initView(Context context) {
        this.mContext = context;
        if (rootView == null) {
            rootView = View.inflate(mContext, R.layout.fragment_video_half, this);
        }
        exoPlayerView = rootView.findViewById(R.id.xhg_video_view);
//        playBtn = rootView.findViewById(R.id.icon_start_or_pause);
    }

    public void setData(List<String> videoPaths) {
        urls.clear();
        if (videoPaths == null || videoPaths.size() == 0) {
            urls = getImagePathFromSD();
        }
        if (videoPaths != null) {
            urls.addAll(videoPaths);
        }
    }

    /**
     * 设置屏幕尺寸大小
     *
     * @param resizeMode 类型
     */
    public void setResizeMode(@AspectRatioFrameLayout.ResizeMode int resizeMode) {
        exoPlayerView.setResizeMode(resizeMode);
    }

    /**
     * 初始化player
     */
    public void initPlayer() {
        //1. 创建一个默认的 TrackSelector
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();
        //2.创建ExoPlayer
        exoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector, loadControl);
        //3.为SimpleExoPlayer设置播放器
        exoPlayerView.setPlayer(exoPlayer);
        exoPlayerView.setUseController(false);
        exoPlayerView.setControllerAutoShow(true);

    }

    /**
     * 准备播放
     */
    public void playVideo(boolean isLoopPlay) {
        //测量播放过程中的带宽。 如果不需要,可以为null。
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        // 生成加载媒体数据的DataSource实例。
        DataSource.Factory dataSourceFactory
                = new DefaultDataSourceFactory(mContext,
                Util.getUserAgent(mContext, getResources().getString(R.string.app_name)), bandwidthMeter);
        // 生成用于解析媒体数据的Extractor实例。
        // ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        // MediaSource代表要播放的媒体。
        Uri[] uris = new Uri[urls.size()];
        for (int i = 0; i < urls.size(); i++) {
            String url = urls.get(i);
//            String path = VideoHelp.getPath(url, FileUtil.getYDHalfVideoPath());
            uris[i] = Uri.parse(url);
        }
        MediaSource[] mediaSources = new MediaSource[uris.length];
        for (int i = 0; i < uris.length; i++) {
            mediaSources[i] = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .setExtractorsFactory(new Mp4ExtractorsFactory()).createMediaSource(uris[i]);
        }
        //LoopingMediaSource loopingMediaSource = new LoopingMediaSource(firstSource, 2);
        MediaSource mediaSource = mediaSources.length == 1 ? mediaSources[0] : new ConcatenatingMediaSource(mediaSources);
//        MediaSource videoSource = new ExtractorMediaSource(Uri.parse(playerUri), dataSourceFactory, extractorsFactory,
//                null, null);
        //设置全部循环
        if (isLoopPlay) {
            exoPlayer.setRepeatMode(Player.REPEAT_MODE_ALL);
        } else {
            exoPlayer.setRepeatMode(Player.REPEAT_MODE_OFF);
        }
        //Prepare the player with the source.
        exoPlayer.prepare(mediaSource);
        //添加监听的listener
        // mSimpleExoPlayer.setVideoListener(mVideoListener);
        exoPlayer.addListener(eventListener);
        // mSimpleExoPlayer.setTextOutput(mOutput);
        exoPlayer.setPlayWhenReady(true);
    }


    private Player.EventListener eventListener = new Player.EventListener() {

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            Log.e(TAG, "onTracksChanged");
//            if (state != null) {
//                state.onVideoChangeState(Player.STATE_ENDED);
//            }
        }

        @Override
        public void onLoadingChanged(boolean isLoading) {
            Log.i(TAG, "onLoadingChanged");
        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            Log.i(TAG, "onPlayerStateChanged: playWhenReady = " + String.valueOf(playWhenReady)
                    + " playbackState = " + playbackState);
            if (state != null) {
                state.onVideoChangeState(playbackState);
            }
            switch (playbackState) {
                case Player.STATE_ENDED:
                    Log.i(TAG, "Playback ended!");
                    //Stop playback and return to start position
                    setPlayPause(false);
//                    if (isShowPlaybtn) {
//                        playBtn.setVisibility(View.VISIBLE);
//                    }
                    exoPlayer.seekTo(0);
                    break;
                case Player.STATE_READY:
//                    playBtn.setVisibility(View.GONE);
                    Log.i(TAG, "ExoPlayer ready! pos: " + exoPlayer.getCurrentPosition());
                    break;
                case Player.STATE_BUFFERING:
                    Log.i(TAG, "Playback buffering!");
                    break;
                case Player.STATE_IDLE:
                    Log.i(TAG, "ExoPlayer idle!");
                    break;
            }
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            Log.i(TAG, "onPlaybackError: " + error.getMessage());
            if (state != null) {
                state.onErrorState(error);
            }
        }

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            Log.i(TAG, "MainActivity.onPlaybackParametersChanged." + playbackParameters.toString());
        }
    };

    /**
     * 判断是否播放，true为播放，false停止
     *
     * @param play True if playback should be started
     */
    public void setPlayPause(boolean play) {
        exoPlayer.setPlayWhenReady(play);
    }

    public void videoRelease() {
        if (exoPlayer != null) {
            exoPlayer.release();
        }
    }

    /**
     * 从sd卡获取图片资源
     *
     * @return
     */
    private List<String> getImagePathFromSD() {
        // 图片列表
        List<String> imagePathList = new ArrayList<>();
        // 得到sd卡内image文件夹的路径   File.separator(/)
        String filePath = getSDCardPath() + File.separatorChar + "Movies";
        // 得到该路径文件夹下所有的文件
        File fileAll = new File(filePath);
        File[] files = fileAll.listFiles();
        if (files == null || files.length == 0) {
            return imagePathList;
        }
        // 将所有的文件存入ArrayList中,并过滤所有mp4格式的文件
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (checkIsImageFile(file.getPath())) {
                imagePathList.add(file.getPath());
            }
        }
        // 返回得到的图片列表
        return imagePathList;
    }

    /**
     * 检查扩展名，得到mp4格式的文件
     *
     * @param fName 文件名
     * @return
     */
    private boolean checkIsImageFile(String fName) {
        boolean isImageFile;
        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("mp4")) {
            isImageFile = true;
        } else {
            isImageFile = false;
        }
        return isImageFile;
    }

    private PlayState state;

    public void setChangeState(PlayState changeState) {
        this.state = changeState;
    }

    public interface PlayState {
        void onVideoChangeState(int state);

        void onErrorState(ExoPlaybackException args);
    }

    /**
     * <br> Description: 获取SDCard的目录路径功能
     * <br> Author:      fangbingran
     * <br> Date:        2018/4/8 21:47
     *
     */
    private String getSDCardPath() {
        File sdcardDir = null;
        // 判断SDCard是否存在
        boolean sdcardExist = Environment.MEDIA_MOUNTED.equals(
                Environment.getExternalStorageState());
        if (sdcardExist) {
            sdcardDir = Environment.getExternalStorageDirectory();
        }
        return sdcardDir != null ? sdcardDir.toString() : "";
    }
}
