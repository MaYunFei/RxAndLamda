package com.yunfei.rxandlamda.dagger;

import com.yunfei.rxandlamda.MainActivity;
import dagger.Component;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by mayunfei on 17-3-17.
 */

@Component(modules = { AppModule.class }) @Singleton public interface AppComponent {
  void inject(MainActivity mainActivity);
}
