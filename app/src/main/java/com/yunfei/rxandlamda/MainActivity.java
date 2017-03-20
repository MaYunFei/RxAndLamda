package com.yunfei.rxandlamda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Single;
import rx.SingleSubscriber;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity {

  @Inject Retrofit mRetrofit;
  private static final String TAG = "Rxjava";
  private PublishSubject<Set<String>> triggers = PublishSubject.create();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    App.getComponent().inject(this);
    final DownloadApi downloadApi = mRetrofit.create(DownloadApi.class);
    //Observable<Response<RequestBody>> downLoadFile = downloadApi.downLoadFile(
    //    "http://s1.music.126.net/download/android/CloudMusic_2.8.1_official_4.apk");

    Object2File.getInstance().init(this, new Gson());
    DownloadItem downloadItem = new DownloadItem();
    downloadItem.setUrl("http://s1.music.126.net/download/android/CloudMusic_2.8.1_official_4.apk");
    downloadItem.setCompletedSize(123);
    downloadItem.setTotalSize(456);

    List<DownloadItem> list = new ArrayList<>();
    list.add(downloadItem);

    //Object2File.getInstance().putObj("yunfei", list).subscribe(new Action1<Boolean>() {
    //  @Override public void call(Boolean aBoolean) {
    //    if (aBoolean) {
    //
    //      Log.i("yunfei", "saveSuccess");
    //    } else {
    //
    //      Log.i("yunfei", "saveFaile");
    //    }
    //  }
    //});

    //Object2File.getInstance()
    //    .getObj("yunfei", DownloadItem.class)
    //    .subscribe(new Action1<DownloadItem>() {
    //      @Override public void call(DownloadItem downloadItem) {
    //        if (downloadItem == null) {
    //          Log.i("yunfei", "读取失败");
    //        } else {
    //          Log.i("yunfei", downloadItem.toString());
    //        }
    //      }
    //    });

    Object2File.getInstance().clear().subscribe(new Action1<Boolean>() {
      @Override public void call(Boolean aBoolean) {
        Log.e("yunfei", "delect is " + aBoolean);
      }
    });

    Object2File.getInstance()
        .getObjList("yunfei", DownloadItem.class, new TypeToken<List<DownloadItem>>() {
        }.getType())
        .subscribe(new Action1<List<DownloadItem>>() {
          @Override public void call(List<DownloadItem> downloadItems) {
            if (downloadItems == null){
              Log.i("yunfei", "没有文件");
              return;
            }
            for (DownloadItem it : downloadItems) {
              Log.i("yunfei", it.toString());
            }
          }
        }, new Action1<Throwable>() {
          @Override public void call(Throwable throwable) {
            Log.e("yunfei", throwable.toString());
          }
        });



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
