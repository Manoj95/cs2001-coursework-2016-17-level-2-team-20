package g20.brunelplanner;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class BPApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initRealmConfiguration();

    }

    private void initRealmConfiguration() {
        Realm.init(this);
        RealmConfiguration baseTimetable = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(baseTimetable);

    }

}
