package com.yunfei.rxandlamda;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tbruyelle.rxpermissions.RxPermissions;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Single;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
  Button button;
  @Inject Retrofit mRetrofit;
  @Inject OkHttpClient mOkHttpClient;
  private static final String TAG = "Rxjava";
  private PublishSubject<Set<String>> triggers = PublishSubject.create();
  public static final int DOWNLOAD_CHUNK_SIZE = 2048;
  private DownloadApi downloadApi;
  private int BUFFER_SIZE = 1024;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    App.getComponent().inject(this);
    downloadApi = mRetrofit.create(DownloadApi.class);
    button = (Button) findViewById(R.id.btn_start);
    button.setOnClickListener(this);
    //downloadApi.downLoadFile(
    //    "http://s1.music.126.net/download/android/CloudMusic_2.8.1_official_4.apk")
    //    .observeOn(Schedulers.io())
    //    .subscribe(new Action1<Response<ResponseBody>>() {
    //      @Override public void call(Response<ResponseBody> response) {
    //        if (response.isSuccessful()) {
    //          InputStream input = null;
    //          OutputStream output = null;
    //          try {
    //            if (response.isSuccessful()) {
    //              long total = response.body().contentLength();
    //              output = new FileOutputStream(
    //                  new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
    //                      "CloudMusic_2.apk"));
    //              input = response.body().byteStream();
    //              byte data[] = new byte[1024];
    //              Log.i("yunfei", "00000000000000000");
    //              long completed = 0;
    //              int count;
    //              while ((count = input.read(data)) != -1) {
    //                completed += count;
    //                output.write(data, 0, count);
    //                Log.i("yunfei", "11111111111111111");
    //              }
    //            } else {
    //            }
    //          } catch (Exception e) {
    //            e.printStackTrace();
    //          } finally {
    //            IOUtils.close(input, output);
    //          }
    //        }
    //      }
    //    });

    new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        .subscribe(new Action1<Boolean>() {
          @Override public void call(Boolean aBoolean) {

          }
        });

    //new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    //    .subscribe(new Action1<Boolean>() {
    //      @Override public void call(Boolean aBoolean) {
    //        Observable<DownloadItem> downloadItemObservable =
    //            downloadApi.downLoadFile("http://www.51job.com/client/51job_51JOB_1_AND2.9.3.apk")
    //                .flatMap(new Func1<Response<ResponseBody>, Observable<DownloadItem>>() {
    //                  @Override
    //                  public Observable<DownloadItem> call(final Response<ResponseBody> response) {
    //                    return Observable.create(new Observable.OnSubscribe<DownloadItem>() {
    //                      @Override public void call(Subscriber<? super DownloadItem> subscriber) {
    //                        if (response.isSuccessful()) {
    //                          InputStream input = null;
    //                          BufferedSink sink = null;
    //                          try {
    //                            ResponseBody body = response.body();
    //                            long total = body.contentLength();
    //                            sink = Okio.buffer(Okio.sink(new File(
    //                                Environment.getExternalStorageDirectory().getAbsolutePath(),
    //                                "CloudMusic_2.apk")));
    //                            long totalRead = 0;
    //                            long read = 0;
    //                            BufferedSource source = body.source();
    //                            while ((read = (source.read(sink.buffer(), DOWNLOAD_CHUNK_SIZE)))
    //                                != -1) {
    //                              totalRead += read;
    //                              Log.e("ddddddddddddddddd", "fffffffffffffffffffffff");
    //                              subscriber.onNext(new DownloadItem());
    //                            }
    //                            sink.writeAll(source);
    //                          } catch (Exception e) {
    //                            subscriber.onError(e);
    //                          } finally {
    //                            IOUtils.close(sink, input);
    //                          }
    //                        } else {
    //                          //请求失败
    //                        }
    //                      }
    //                    });
    //                  }
    //                });
    //
    //        downloadItemObservable.subscribe(new Action1<DownloadItem>() {
    //          @Override public void call(DownloadItem downloadItem) {
    //            Log.i("123", "1111111111111111111111");
    //          }
    //        }, new Action1<Throwable>() {
    //          @Override public void call(Throwable throwable) {
    //            Log.i("error", throwable.toString());
    //          }
    //        });

    //PublishSubject<DownloadItem> publishSubject = PublishSubject.create();
    //downloadItemObservable.subscribe(publishSubject);
    //
    //publishSubject.subscribe(new Action1<DownloadItem>() {
    //  @Override public void call(DownloadItem downloadItem) {
    //    Log.i("hot","烫烫 烫烫烫烫烫烫烫烫烫烫");
    //  }
    //});

    //  }
    //});

    //PublishSubject<DownloadItem> publishSubject = PublishSubject.create();
    //downloadItemObservable.subscribe(publishSubject);
    //
    //publishSubject.subscribe(new Action1<DownloadItem>() {
    //  @Override public void call(DownloadItem downloadItem) {
    //    Log.i("hot","烫烫 烫烫烫烫烫烫烫烫烫烫");
    //  }
    //});
    //.subscribeOn(Schedulers.io())
    //.observeOn(AndroidSchedulers.mainThread())
    //.subscribe(new Action1<DownloadItem>() {
    //  @Override public void call(DownloadItem downloadItem) {
    //
    //  }
    //});

    //new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    //    .flatMap(new Func1<Boolean, Observable<ResponseBody>>() {
    //      @Override public Observable<ResponseBody> call(Boolean aBoolean) {
    //        return downloadApi.downLoadFile2(
    //            "http://s1.music.126.net/download/android/CloudMusic_2.8.1_official_4.apk");
    //      }
    //    })
    //    .flatMap(new Func1<ResponseBody, Observable<DownloadItem>>() {
    //      @Override public Observable<DownloadItem> call(final ResponseBody responseBody) {
    //        return Observable.create(new Observable.OnSubscribe<DownloadItem>() {
    //          @Override public void call(Subscriber<? super DownloadItem> subscriber) {
    //            InputStream inputStream = null;
    //            OutputStream outputStream = null;
    //            try {
    //              DownloadItem downloadItem = new DownloadItem();
    //              long total = responseBody.contentLength();
    //              outputStream = new FileOutputStream(
    //                  new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
    //                      "CloudMusic_2.apk"));
    //              inputStream = responseBody.byteStream();
    //              byte data[] = new byte[1024];
    //              downloadItem.setTotalSize(total);
    //              downloadItem.setCompletedSize(0);
    //              downloadItem.setUrl("123");
    //              subscriber.onNext(downloadItem);
    //              Log.i("yunfei", "00000000000000000");
    //              long completed = 0;
    //              int count;
    //              while ((count = inputStream.read(data)) != -1) {
    //                completed += count;
    //                outputStream.write(data, 0, count);
    //                downloadItem.setCompletedSize(completed);
    //                subscriber.onNext(downloadItem);
    //                Log.i("yunfei", "11111111111111111");
    //              }
    //
    //              subscriber.onCompleted();
    //            } catch (Exception e) {
    //              IOUtils.close(inputStream, outputStream);
    //            }
    //          }
    //        });
    //      }
    //    })
    //    .subscribeOn(Schedulers.io())
    //    .observeOn(AndroidSchedulers.mainThread())
    //    .subscribe(new Action1<DownloadItem>() {
    //      @Override public void call(DownloadItem downloadItem) {
    //        Log.i("&&&&&&&&&&&& ", "totalsize = "
    //            + downloadItem.getTotalSize()
    //            + "     complete size = "
    //            + downloadItem.getCompletedSize());
    //      }
    //    }, new Action1<Throwable>() {
    //      @Override public void call(Throwable throwable) {
    //        Log.e("error", throwable.toString());
    //      }
    //    });

    //.flatMap(new Func1<Boolean, Observable<Response<ResponseBody>>>() {
    //  @Override public Observable<Response<ResponseBody>> call(Boolean aBoolean) {
    //    return downloadApi.downLoadFile(
    //        "http://s1.music.126.net/download/android/CloudMusic_2.8.1_official_4.apk");
    //  }
    //})
    //.flatMap(new Func1<Response<ResponseBody>, Observable<DownloadItem>>() {
    //
    //  @Override public Observable<DownloadItem> call(final Response<ResponseBody> response) {
    //
    //    return Observable.create(new Observable.OnSubscribe<DownloadItem>() {
    //      @Override public void call(Subscriber<? super DownloadItem> subscriber) {
    //        InputStream input = null;
    //        OutputStream output = null;
    //        DownloadItem downloadItem = new DownloadItem();
    //        try {
    //          if (response.isSuccessful()) {
    //            long total = response.body().contentLength();
    //            output = new FileOutputStream(
    //                new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
    //                    "CloudMusic_2.apk"));
    //            input = response.body().byteStream();
    //            byte data[] = new byte[1024];
    //            downloadItem.setTotalSize(total);
    //            downloadItem.setCompletedSize(0);
    //            downloadItem.setUrl(response.message());
    //            subscriber.onNext(downloadItem);
    //            Log.i("yunfei", "00000000000000000");
    //            long completed = 0;
    //            int count;
    //            while ((count = input.read(data)) != -1) {
    //              completed += count;
    //              output.write(data, 0, count);
    //              downloadItem.setCompletedSize(completed);
    //              subscriber.onNext(downloadItem);
    //              Log.i("yunfei", "11111111111111111");
    //            }
    //
    //            subscriber.onCompleted();
    //          } else {
    //            subscriber.onError(new IllegalAccessError("请求错误"));
    //          }
    //        } catch (Exception e) {
    //          e.printStackTrace();
    //          subscriber.onError(e);
    //        } finally {
    //          IOUtils.close(input, output);
    //        }
    //      }
    //    });
    //  }
    //})
    //
    //.subscribe(new Action1<DownloadItem>() {
    //  @Override public void call(DownloadItem downloadItem) {
    //    Log.i("&&&&&&&&&&&& ", "totalsize = "
    //        + downloadItem.getTotalSize()
    //        + "     complete size = "
    //        + downloadItem.getCompletedSize());
    //  }
    //}, new Action1<Throwable>() {
    //  @Override public void call(Throwable throwable) {
    //    Log.e("&&&&&&&&&&&& ", "throwable   " + throwable);
    //  }
    //}, new Action0() {
    //  @Override public void call() {
    //    Log.e("&&&&&&&&&&&& ", "完了 ");
    //  }
    //});
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

  private void testObject2File() {
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
            if (downloadItems == null) {
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

  private int singAdd(int a, int b) {
    Log.i(TAG, "singAdd");
    return a + b;
  }

  @Override public void onClick(View v) {

    //new Thread(new Runnable() {
    //  @Override public void run() {
    //    try{
    //      String fileURL = "http://s1.music.126.net/download/android/CloudMusic_2.8.1_official_4.apk";
    //      URL url = new URL(fileURL);
    //      HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
    //      int responseCode = httpConn.getResponseCode();
    //      // always check HTTP response code first
    //      if (responseCode == HttpURLConnection.HTTP_OK) {
    //        String fileName = "";
    //        String disposition = httpConn.getHeaderField("Content-Disposition");
    //        String contentType = httpConn.getContentType();
    //        int contentLength = httpConn.getContentLength();
    //
    //        if (disposition != null) {
    //          // extracts file name from header field
    //          int index = disposition.indexOf("filename=");
    //          if (index > 0) {
    //            fileName = disposition.substring(index + 10,
    //                disposition.length() - 1);
    //          }
    //        } else {
    //          // extracts file name from URL
    //          fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
    //              fileURL.length());
    //        }
    //
    //        System.out.println("Content-Type = " + contentType);
    //        System.out.println("Content-Disposition = " + disposition);
    //        System.out.println("Content-Length = " + contentLength);
    //        System.out.println("fileName = " + fileName);
    //
    //        // opens input stream from the HTTP connection
    //        InputStream inputStream = httpConn.getInputStream();
    //        String saveFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + fileName;
    //
    //        // opens an output stream to save into file
    //        FileOutputStream outputStream = new FileOutputStream(saveFilePath);
    //
    //        int bytesRead = -1;
    //        byte[] buffer = new byte[BUFFER_SIZE];
    //        int Total = 0;
    //        while ((bytesRead = inputStream.read(buffer)) != -1) {
    //          outputStream.write(buffer, 0, bytesRead);
    //          Total += bytesRead;
    //          Log.i("ffffffffffffff","bytes = "+Total);
    //        }
    //
    //        outputStream.close();
    //        inputStream.close();
    //
    //        System.out.println("File downloaded");
    //      } else {
    //        System.out.println("No file to download. Server replied HTTP code: " + responseCode);
    //      }
    //      httpConn.disconnect();
    //    }catch (Exception e){
    //
    //    }
    //
    //
    //  }
    //}).start();

    //new Thread(new Runnable() {
    //  @Override public void run() {
    //    Call<ResponseBody> responseCall = downloadApi.downloadFile3(
    //        "http://apk.dongao.com/other/xianchangbaoming.apk");
    //    OutputStream output = null;
    //    InputStream input = null;
    //    try {
    //      Log.i("starttime ====" ,System.currentTimeMillis()+"");
    //      Response<ResponseBody> response = responseCall.execute();
    //      Log.i("endtime ====" ,System.currentTimeMillis()+"");
    //
    //      DownloadItem downloadItem = new DownloadItem();
    //      if (response.isSuccessful()) {
    //        long total = response.body().contentLength();
    //        output = new FileOutputStream(
    //            new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
    //                "888.apk"));
    //        input = response.body().byteStream();
    //        byte data[] = new byte[1024];
    //        downloadItem.setTotalSize(total);
    //        downloadItem.setCompletedSize(0);
    //        downloadItem.setUrl(response.message());
    //        Log.i("yunfei", "00000000000000000");
    //        long completed = 0;
    //        int count;
    //        while ((count = input.read(data)) != -1) {
    //          completed += count;
    //          output.write(data, 0, count);
    //          downloadItem.setCompletedSize(completed);
    //          Log.i("yunfei", "11111111111111111");
    //        }
    //      } else {
    //      }
    //    } catch (Exception e) {
    //      e.printStackTrace();
    //    } finally {
    //      IOUtils.close(input, output);
    //    }
    //  }
    //}).start();

    new Thread(new Runnable() {
      @Override public void run() {
        Request request =
            new Request.Builder().url("http://apk.dongao.com/other/xianchangbaoming.apk").build();
        okhttp3.Call call = mOkHttpClient.newCall(request);
        OutputStream output = null;
        InputStream input = null;
        try {
          okhttp3.Response response = call.execute();

          if (response.isSuccessful()) {
            ResponseBody body = response.body();
            long total = response.body().contentLength();
            output = new FileOutputStream(
                new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "888.apk"));
            input = response.body().byteStream();
            byte data[] = new byte[1024];
            Log.i("yunfei", "00000000000000000");
            long completed = 0;
            int count;
            while ((count = input.read(data)) != -1) {
              completed += count;
              output.write(data, 0, count);
              Log.i("yunfei", "completed = " + completed);
            }
          } else {
          }
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          IOUtils.close(input, output);
        }
      }
    }).start();

    //Observable.create(new Observable.OnSubscribe<DownloadItem>() {
    //  @Override public void call(Subscriber<? super DownloadItem> subscriber) {
    //    Call<ResponseBody> responseCall = downloadApi.downloadFile3(
    //        "http://s1.music.126.net/download/android/CloudMusic_2.8.1_official_4.apk");
    //    try {
    //      Log.i("starttime" ,System.currentTimeMillis()+"");
    //      Response<ResponseBody> response = responseCall.execute();
    //      Log.i("starttime" ,System.currentTimeMillis()+"");
    //      if (response.isSuccessful()) {
    //        InputStream input = null;
    //        BufferedSink sink = null;
    //        try {
    //          ResponseBody body = response.body();
    //          sink = Okio.buffer(Okio.sink(new File(
    //              Environment.getExternalStorageDirectory().getAbsolutePath(),
    //              "xianchangbaoming.apk")));
    //          long totalRead = 0;
    //          long read = 0;
    //          BufferedSource source = body.source();
    //          while ((read = (source.read(sink.buffer(), DOWNLOAD_CHUNK_SIZE)))
    //              != -1) {
    //            totalRead += read;
    //            Log.e("ddddddddddddddddd", "fffffffffffffffffffffff");
    //            DownloadItem downloadItem = new DownloadItem();
    //            downloadItem.setCompletedSize(totalRead);
    //            subscriber.onNext(downloadItem);
    //          }
    //          sink.writeAll(source);
    //        } catch (Exception e) {
    //          subscriber.onError(e);
    //        } finally {
    //          IOUtils.close(sink, input);
    //        }
    //      } else {
    //        //请求失败
    //      }
    //    } catch (IOException e) {
    //      e.printStackTrace();
    //    }
    //  }
    //}).subscribeOn(Schedulers.io())
    //    .observeOn(AndroidSchedulers.mainThread())
    //
    //    .subscribe(new Action1<DownloadItem>() {
    //      @Override public void call(DownloadItem downloadItem) {
    //        Log.i("111111111111", downloadItem.toString());
    //      }
    //    }, new Action1<Throwable>() {
    //      @Override public void call(Throwable throwable) {
    //        Log.e("eeeee", throwable.toString());
    //      }
    //    });
  }
}
