package android.netcom.ashu.materialtabs.App;

import android.app.Application;
import android.content.Context;
import android.netcom.ashu.materialtabs.database.MoviesDatabase;

/**
 * Created by KamaL on 29-08-2016.
 */
public class MyApplication extends Application {

    public static final String API_KEY_ROTTEN_TOMATOES = "54wzfswsa4qmjg8hjwa64d4c";
    private static MyApplication sInstance;

    private static MoviesDatabase mDataBase;

    public static MyApplication getsInstance(){
        return sInstance;
    }

    public static Context getAppContext(){
        return  sInstance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public synchronized static MoviesDatabase getWritableDataBase(){
        if(mDataBase == null){
            mDataBase = new MoviesDatabase(getAppContext());
        }
        return mDataBase;
    }
}
