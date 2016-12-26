package g20.brunelplanner;

import android.app.Application;

public class BPApplication extends Application {

    private static BPApplication sInstance;

    public BPApplication() {
        sInstance = this;
    }

    public static synchronized BPApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

//    private void initializePreferences() {
//        PreferenceUtils.initializePreferences();
//    }

}
