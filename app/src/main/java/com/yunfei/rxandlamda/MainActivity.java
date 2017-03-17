package com.yunfei.rxandlamda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import java.util.Set;
import javax.inject.Inject;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Single;
import rx.SingleSubscriber;
import rx.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity {

  @Inject Retrofit mRetrofit;
  private static final String TAG = "Rxjava";
  private PublishSubject<Set<String>> triggers = PublishSubject.create();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    App.getComponent().inject(this);
    singleTest();
    DownloadApi downloadApi = mRetrofit.create(DownloadApi.class);
    Observable<Response<RequestBody>> downLoadFile = downloadApi.downLoadFile(
        "http://s1.music.126.net/download/android/CloudMusic_2.8.1_official_4.apk");
  }

  private void singleTest() {
    //Single.just(singAdd(1, 1)).subscribe(new SingleSubscriber<Integer>() {
    //  @Override public void onSuccess(Integer integer) {
    //
    //  }
    //
    //  @Override public void onError(Throwable error) {
    //
    //  }
    //});

    Single.just(singAdd(1, 2));
  }

  private int singAdd(int a, int b) {
    Log.i(TAG, "singAdd");
    return a + b;
  }
}
