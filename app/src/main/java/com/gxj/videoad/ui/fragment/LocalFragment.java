package com.gxj.videoad.ui.fragment;

import android.Manifest;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gxj.videoad.R;
import com.gxj.videoad.base.BaseFragment;
import com.gxj.videoad.bean.VideoInfo;
import com.gxj.videoad.ui.activity.VideoViewPlayerActivity;
import com.gxj.videoad.utils.VideoScanner;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * local 本地视频播放
 */
public abstract class LocalFragment extends BaseFragment {
    private RecyclerView.Adapter adapter;
    private SwipeRefreshLayout refreshView;

    private List<VideoInfo> list = new ArrayList<>();//视频列表

    @Override
    protected String getTitle() {
        return "本地视频播放";
    }

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
        refreshView = v.findViewById(R.id.refresh_view);
        RecyclerView recyclerView = v.findViewById(R.id.recycle_view);

        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new VideoLocalAdapter();
        recyclerView.setAdapter(adapter);

        refreshView.setOnRefreshListener(this::initData);
        refreshView.setRefreshing(true);
    }

    @SuppressLint("CheckResult")
    private void initData() {
        RxPermissions rxPermissions = new RxPermissions(mContext);
        rxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(permission -> {
            if (permission.granted) {
                Observable.create((ObservableOnSubscribe<List<VideoInfo>>) emitter -> {
                    List<VideoInfo> list = VideoScanner.loadVideo(mContext);
                    emitter.onNext(list);
                }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                        .subscribe(o -> {
                            if (o != null) {
                                Log.e(LocalFragment.class.getSimpleName(), o.size() + "");
                                list = o;
                                adapter.notifyDataSetChanged();
                                refreshView.setRefreshing(false);
                            }
                        });
            }
        });
    }

    private class VideoLocalAdapter extends RecyclerView.Adapter<VideoLocalAdapter.VideoLocalHolder> {
        @NonNull
        @Override
        public VideoLocalAdapter.VideoLocalHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = getLayoutInflater().inflate(R.layout.adapter_video_loacal_item, viewGroup, false);
            return new VideoLocalAdapter.VideoLocalHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull VideoLocalAdapter.VideoLocalHolder viewHolder, int i) {
            if (list.get(i) != null && !TextUtils.isEmpty(list.get(i).getDataUrl())) {
                Glide.with(mContext).load(list.get(i).getDataUrl()).into(viewHolder.img);
            } else {
                Glide.with(mContext).load(R.mipmap.com_pop_pic_waiting).into(viewHolder.img);
            }
            viewHolder.img.setOnClickListener(v -> {
                        if (list.get(i) != null && !TextUtils.isEmpty(list.get(i).getDataUrl())) {
                            onVideoOnClick(list.get(i).getDataUrl());
                        }
                    }
            );
        }

        @Override
        public int getItemCount() {
            return list.size();
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
