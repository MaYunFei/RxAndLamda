package com.yunfei.rxandlamda;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by mayunfei on 17-3-17.
 */

public interface DownloadApi {
  @Streaming @GET Observable<Response<ResponseBody>> downLoadFile(@Url String downloadUrl);

  @Streaming @GET Observable<ResponseBody> downLoadFile2(@Url String downloadUrl);

  @Streaming @GET Call<ResponseBody> downloadFile3(@Url String downloadUrl);
}
