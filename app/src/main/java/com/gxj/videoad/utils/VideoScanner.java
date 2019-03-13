package com.gxj.videoad.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.gxj.videoad.bean.VideoInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：gxj<br/>
 * 说明：视频ContentProvide相关操作---生成视频List
 */
public class VideoScanner {
    public static String[] projection = new String[]{
            MediaStore.Video.Media._ID,//ID
            MediaStore.Video.Media.TITLE,//名称
            MediaStore.Video.Media.DURATION,//时长
            MediaStore.Video.Media.DATA,//路径
            MediaStore.Video.Media.SIZE,//大小
            MediaStore.Video.Media.DATE_ADDED//添加的时间
    };
    /**
     * 歌曲集合
     */
    private static List<VideoInfo> videos = new ArrayList<>();

    /**
     * 读取音频
     */
    public static List<VideoInfo> loadVideo(final Context context) {
        if (videos.size() != 0) {
            return videos;
        }
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    projection, "", null,
                    "date_added desc", null);
            if (cursor != null) {
                // 根据字段获取数据库中数据的索引
                int songIdIdx = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
                int titleIdx = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
                int durationIdx = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
                int dataUrlIdx = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
                int sizeIdx = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE);
                int addDateIdx = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED);
                while (cursor.moveToNext()) {
                    long videoId = cursor.getLong(songIdIdx);//获取id
                    String title = cursor.getString(titleIdx);//获取名字
                    String dataUrl = cursor.getString(dataUrlIdx);//获取路径
                    long duration = cursor.getLong(durationIdx);//获取时长
                    long size = cursor.getLong(sizeIdx);//获取大小
                    long addDate = cursor.getLong(addDateIdx);//加入时间
                    videos.add(new VideoInfo(videoId, title, dataUrl, duration, size, addDate));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return videos;
    }
}
