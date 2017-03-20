package com.yunfei.rxandlamda;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import rx.Observable;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 存储对象
 * Created by mayunfei on 17-3-20.
 */

public class Object2File {
  private Gson mGson;
  private File mCacheDir;
  private static Object2File INSTANCE = new Object2File();
  private static final String PATH_NAME = "Object2File";

  public static Object2File getInstance() {
    return INSTANCE;
  }


  public void init(Context context, Gson gson) {
    mGson = gson;
    try {
      mCacheDir = new File(context.getFilesDir(), PATH_NAME);
      if (!mCacheDir.exists()) {
        mCacheDir.mkdirs();
      }
    } catch (Exception e) {
      Log.e(PATH_NAME, "缓存创建失败");
    }
  }

  private Object2File() {
  }

  /**
   * 存储对象
   */
  public Single<Boolean> putObj(@NonNull final String key, @NonNull final Object value) {
    return Single.create(new Single.OnSubscribe<Boolean>() {
      @Override public void call(SingleSubscriber<? super Boolean> singleSubscriber) {
        FileWriter fileWriter = null;
        try {
          String json = mGson.toJson(value);
          File file = new File(mCacheDir, key);
          fileWriter = new FileWriter(file);
          fileWriter.write(json);
          singleSubscriber.onSuccess(true);
        } catch (Exception e) {
          singleSubscriber.onSuccess(false);
        } finally {
          if (fileWriter != null) {
            try {
              fileWriter.flush();
              fileWriter.close();
            } catch (IOException ignored) {
            }
          }
        }
      }
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
  }

  /**
   * 获得对象
   */
  public <T> Single<T> getObj(@NonNull final String key, @NonNull final Class<T> classOfT) {
    return Single.create(new Single.OnSubscribe<T>() {
      @Override public void call(SingleSubscriber<? super T> singleSubscriber) {

        try {
          File file = new File(mCacheDir, key);
          if (!file.exists()) {
            singleSubscriber.onSuccess(null);
            return;
          }
          FileReader fileReader = new FileReader(file);
          JsonReader reader = new JsonReader(fileReader);
          singleSubscriber.onSuccess((T) mGson.fromJson(reader, classOfT));
        } catch (Exception e) {
          singleSubscriber.onSuccess(null);
        }
      }
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
  }

  /**
   * 获得list
   */
  public <T> Single<List<T>> getObjList(@NonNull final String key, @NonNull Class<T> classOfT,
      @NonNull final Type type) {
    return Single.create(new Single.OnSubscribe<List<T>>() {
      @Override public void call(SingleSubscriber<? super List<T>> singleSubscriber) {
        try {
          new TypeToken<List<T>>() {
          }.getType();
          File file = new File(mCacheDir, key);
          if (!file.exists()) {
            singleSubscriber.onSuccess(null);
            return;
          }
          FileReader fileReader = new FileReader(file);
          JsonReader reader = new JsonReader(fileReader);
          singleSubscriber.onSuccess((List<T>) mGson.fromJson(reader, type));
        } catch (Exception e) {
          singleSubscriber.onSuccess(null);
        }
      }
    });
  }

  public Observable<Boolean> clear() {
    return Observable.just(1).map(new Func1<Integer, Boolean>() {
      @Override public Boolean call(Integer integer) {
        boolean success = true;
        try {
          String[] fileList = mCacheDir.list();
          for (String tmpFileName : fileList) {
            File file = new File(mCacheDir,tmpFileName);
            if (file.exists() && file.isFile()) {
              if (!file.delete()) {
                success = false;
              }
            }
          }
        } catch (Exception e) {
          return false;
        }

        return success;
      }
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
  }
}
