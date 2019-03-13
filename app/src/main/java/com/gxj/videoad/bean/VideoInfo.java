package com.gxj.videoad.bean;

/**
 * 视频信息模型
 */
public class VideoInfo {
    private long videoId;//获取id
    private String title;//获取名字
    private String dataUrl;//获取路径
    private long duration;//获取时长
    private long size;//获取大小
    private long addDate;//加入时间

    public VideoInfo(long videoId, String title, String dataUrl, long duration, long size, long addDate) {
        this.videoId = videoId;
        this.title = title;
        this.dataUrl = dataUrl;
        this.duration = duration;
        this.size = size;
        this.addDate = addDate;
    }

    public long getVideoId() {
        return videoId;
    }

    public void setVideoId(long videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getAddDate() {
        return addDate;
    }

    public void setAddDate(long addDate) {
        this.addDate = addDate;
    }
}
