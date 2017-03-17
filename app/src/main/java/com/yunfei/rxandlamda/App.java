package com.yunfei.rxandlamda;

import android.app.Application;
import android.content.Context;
import com.yunfei.rxandlamda.dagger.AppComponent;
import com.yunfei.rxandlamda.dagger.AppModule;
import com.yunfei.rxandlamda.dagger.DaggerAppComponent;

/**
 * Created by mayunfei on 17-3-17.
 */

public class App extends Application {

  private static AppComponent appComponent;

  @Override public void onCreate() {
    super.onCreate();
    appComponent = DaggerAppComponent.builder().appModule(new AppModule()).build();
  }

  public static AppComponent getComponent() {
    return appComponent;
  }
}
