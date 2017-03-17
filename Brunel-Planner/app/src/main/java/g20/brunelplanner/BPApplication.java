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
//        initializeStetho(this);

    }

    private void initRealmConfiguration() {
        // Start the Realm db and set it as the default db
        Realm.init(this);
        RealmConfiguration base = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(base);
    }

//    private void initializeStetho(final BPApplication context) {
//        Stetho.initialize(Stetho.newInitializerBuilder(this)
//                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
//                .enableWebKitInspector(RealmInspectorModulesProvider.builder(this)
//                        .withDescendingOrder()
//                        .withLimit(1000)
//                        .databaseNamePattern(Pattern.compile(".+\\.realm"))
//                        .build())
//                .build());
//    }

}
