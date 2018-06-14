package com.example.weimingzeng.maxutils.netUtils;

/**
 * Date: 2018/6/13
 * author:Weiming Max Zeng
 */
public interface ProgressListener {
    /**
     * 载进度监听
     *
     * @param bytesRead     已经下载文件的大小
     * @param contentLength 文件的大小
     * @param progress      当前进度
     * @param filePath      文件路径
     */
    void onResponseProgress(long bytesRead, long contentLength, int progress, String filePath);
}
