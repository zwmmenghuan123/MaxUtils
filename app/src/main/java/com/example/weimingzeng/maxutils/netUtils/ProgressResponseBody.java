package com.example.weimingzeng.maxutils.netUtils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Date: 2018/6/13
 * author:Weiming Max Zeng
 * 带进度的responseBody
 */
public class ProgressResponseBody extends ResponseBody {

    private ResponseBody responseBody;
    // BufferedSource 是okio库中的输入流，这里就当作inputStream来使用。
    private BufferedSource bufferedSource;
    private ProgressListener downloadListener;

    public ProgressResponseBody(ResponseBody responseBody, ProgressListener downloadListener) {
        this.responseBody = responseBody;
        this.downloadListener = downloadListener;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead;
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                // read() returns the number of bytes read, or -1 if this source is exhausted.
                long bytesRead = super.read(sink, byteCount);
                 totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                if (downloadListener != null) {
                    if (bytesRead != -1) {
                        downloadListener.onResponseProgress(bytesRead, contentLength(), (int) (totalBytesRead * 100 / contentLength()), null);
                    }
                }
                return super.read(sink, byteCount);
            }
        };
    }

}
