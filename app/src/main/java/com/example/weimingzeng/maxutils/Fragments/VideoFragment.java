package com.example.weimingzeng.maxutils.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.weimingzeng.maxutils.R;

import java.io.File;

public class VideoFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public VideoFragment() {
    }

    public static VideoFragment newInstance() {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        VideoView videoView = view.findViewById(R.id.video);
        MediaController mediaController = new MediaController(getContext());
        mediaController.setVisibility(View.INVISIBLE);//隐藏进度条和视频播放按钮
        File videoFile = new File("/storage/emulated/0/DCIM/Camera/VID_20180530_180649.mp4");

        // 先判断这个文件是否存在
        if (videoFile.exists()) {
            videoView.setVideoPath(videoFile.getAbsolutePath());
            // 设置VideView与MediaController建立关联
            videoView.setMediaController(mediaController);
            // 设置MediaController与VideView建立关联
            mediaController.setMediaPlayer(videoView);
            // 让VideoView获取焦点
            videoView.requestFocus();
            // 开始播放
            videoView.start();
        }else {
            Toast.makeText(getContext(), "视频文件不存在", Toast.LENGTH_LONG).show();
        }
        return view;
    }

}
