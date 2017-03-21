package com.yunfei.rxandlamda;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by mayunfei on 17-3-21.
 */

public class RxDownload {
  public static Observable RxDownload(DownloadApi downloadApi, String url) {
    downloadApi.downLoadFile(url)
        .flatMap(new Func1<Response<ResponseBody>, Observable<?>>() {
          @Override public Observable<?> call(Response<ResponseBody> response) {
            return null;
          }
        });
    return null;
  }
}
