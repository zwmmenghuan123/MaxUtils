package com.example.weimingzeng.maxutils.netUtils;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.weimingzeng.maxutils.MaxApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Date: 2018/6/13
 * author:Weiming Max Zeng
 */
public abstract class DownLoadObserver extends BaseObserver<ResponseBody> {

    private String fileName;

    public DownLoadObserver(String fileName) {
        this.fileName = fileName;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onSuccess(ResponseBody response) {
        Observable.just(response)
               .subscribeOn(Schedulers.io())
               .subscribe(new Consumer<ResponseBody>() {
                   @Override
                   public void accept(ResponseBody responseBody) throws Exception {
                       saveFile(responseBody, fileName, new ProgressListener() {
                           @Override
                           public void onResponseProgress(final long bytesRead, final long contentLength, final int progress, final String filePath) {
                                Observable.just(progress)
                                        .distinct()
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Consumer<Integer>() {
                                            @Override
                                            public void accept(Integer integer) throws Exception {
                                                success(bytesRead, contentLength, progress, filePath);
                                            }
                                        });
                           }
                       });
                   }
               });
    }

    @Override
    public void onError(String msg) {

    }

    /**
     * 成功回调
     */
    protected abstract void success(long bytesRead, long contentLength, float progress, String filePath);

    private void saveFile(ResponseBody response, final String destFileName, ProgressListener listener) {
        String destFileDir = MaxApplication.getAppContext().getExternalFilesDir(null) + File.separator;
        InputStream in = response.byteStream();
        byte[] bytes;
        int len = 0;
        long sum = 0;
        int progress;
        File dir = new File(destFileDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, destFileName);
        try {
            FileOutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[2048];
            while((len = in.read(buf)) != -1) {
                sum += len;
                out.write(buf, 0, len);
                progress = (int) ((sum * 1.0f / response.contentLength()) * 100);
                Log.d("progress", progress +"");
                listener.onResponseProgress(sum, response.contentLength(), progress, file.getAbsolutePath());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
