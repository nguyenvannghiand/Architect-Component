package thaihn.android.architecturecomponent;

import android.app.Application;
import thaihn.android.architecturecomponent.data.room.db.AppDatabase;

public class MyApplication extends Application {

    private static MyApplication sInstance;
    private AppDatabase mAppDatabase;

    public static MyApplication getInstance() {
        return sInstance;
    }

    public AppDatabase getAppDatabase() {
        return mAppDatabase;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppDatabase = AppDatabase.getInstance(this);
        sInstance = this;
    }
}
