package g20.brunelplanner;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class BPApplication extends Application {

    // This runs first when the application starts

    @Override
    public void onCreate() {
        super.onCreate();
        initRealmConfiguration();

    }

    private void initRealmConfiguration() {
        // Start the Realm db and set it as the default db
        Realm.init(this);
        RealmConfiguration base = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(base);

    }

}
