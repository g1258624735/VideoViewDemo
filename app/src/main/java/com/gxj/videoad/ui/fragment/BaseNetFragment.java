package com.gxj.videoad.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gxj.videoad.R;
import com.gxj.videoad.base.BaseFragment;
import com.gxj.videoad.ui.activity.VideoViewPlayerActivity;

/**
 * 网络视频播放
 */
public abstract class BaseNetFragment extends BaseFragment {
    /**
     * 列表
     */
    private String[] list = {"http://psrecycle.oss-cn-hangzhou.aliyuncs.com/recycle/idcard/20180910100624030/4.mp4",
            "http://jzvd.nathen.cn/f55530ba8a59403da0621cbf4faef15e/adae4f2e3ecf4ea780beb057e7bce84c-5287d2089db37e62345123a1be272f8b.mp4",
            "http://jzvd.nathen.cn/25a8d119cfa94b49a7a4117257d8ebd7/f733e65a22394abeab963908f3c336db-5287d2089db37e62345123a1be272f8b.mp4"};

    @Override
    protected String getTitle() {
        return "网络视频播放";
    }

    /**
     * 点击事件
     * @param  url 视
     */
    protected abstract void onVideoOnClick(String url);

    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container) {
        return LayoutInflater.from(mContext).inflate(R.layout.fragment_video_local_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }

    private void initView(View v) {
        SwipeRefreshLayout refreshView = v.findViewById(R.id.refresh_view);
        RecyclerView recyclerView = v.findViewById(R.id.recycle_view);

        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        RecyclerView.Adapter adapter = new VideoNetAdapter();
        recyclerView.setAdapter(adapter);

        refreshView.setOnRefreshListener(this::initData);
    }

    @SuppressLint("CheckResult")
    private void initData() {

    }

    private class VideoNetAdapter extends RecyclerView.Adapter<VideoNetAdapter.VideoLocalHolder> {
        @NonNull
        @Override
        public VideoNetAdapter.VideoLocalHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = getLayoutInflater().inflate(R.layout.adapter_video_loacal_item, viewGroup, false);
            return new VideoNetAdapter.VideoLocalHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull VideoNetAdapter.VideoLocalHolder viewHolder, int i) {
            if (!TextUtils.isEmpty(list[i])) {
                Glide.with(mContext).load(list[i]).into(viewHolder.img);
            } else {
                Glide.with(mContext).load(R.mipmap.com_pop_pic_waiting).into(viewHolder.img);
            }
            viewHolder.img.setOnClickListener(v -> {
                        if (!TextUtils.isEmpty(list[i])) {
                            onVideoOnClick(list[i]);
                        }
                    }
            );
        }

        @Override
        public int getItemCount() {
            return list.length;
        }

        private class VideoLocalHolder extends RecyclerView.ViewHolder {
            private ImageView img;

            private VideoLocalHolder(@NonNull View itemView) {
                super(itemView);
                img = (ImageView) itemView;
            }
        }
    }
}
