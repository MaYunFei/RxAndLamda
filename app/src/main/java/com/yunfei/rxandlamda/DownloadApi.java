package com.yunfei.rxandlamda;

import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by mayunfei on 17-3-17.
 */

public interface DownloadApi {

  @GET Observable<Response<RequestBody>> downLoadFile(@Url String downloadUrl);
}
