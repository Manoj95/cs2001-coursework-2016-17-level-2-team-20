package g20.brunelplanner;

import android.app.Application;

public class BPApplication extends Application {

    private static BPApplication sInstance;

    public static BPApplication getInstance(){
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
