package com.yunfei.rxandlamda.dagger;

import dagger.Module;
import dagger.Provides;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by mayunfei on 17-3-17.
 */
@Module public class AppModule {
  @Singleton @Provides public OkHttpClient provideOkHttpClient() {
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
    return new OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .build();
  }

  @Singleton @Provides public Retrofit provideRetrofit(OkHttpClient okhttpClient) {
    Retrofit retrofit = new Retrofit.Builder().client(okhttpClient)
        //.addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
        .baseUrl("http://gank.io/api/")
        .build();
    return retrofit;
  }
}
