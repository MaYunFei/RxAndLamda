package com.yunfei.rxandlamda;

/**
 * Created by mayunfei on 17-3-17.
 */

public class DownloadItem {
  long totalSize;
  long completedSize;
  String url;

  public long getTotalSize() {
    return totalSize;
  }

  public void setTotalSize(long totalSize) {
    this.totalSize = totalSize;
  }

  public long getCompletedSize() {
    return completedSize;
  }

  public void setCompletedSize(long completedSize) {
    this.completedSize = completedSize;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Override public String toString() {
    return "DownloadItem{" +
        "totalSize=" + totalSize +
        ", completedSize=" + completedSize +
        ", url='" + url + '\'' +
        '}';
  }
}
