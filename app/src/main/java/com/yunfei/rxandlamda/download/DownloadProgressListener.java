package com.yunfei.rxandlamda.download;

/**
 * Created by mayunfei on 17-3-20.
 */

public interface DownloadProgressListener {
  void update(long bytesRead, long contentLength, boolean done);
}
