package g20.brunelplanner;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class BPApplication extends Application {

    // This runs first when the application starts

    @Override
    public void onCreate() {
        super.onCreate();
        initRealmConfiguration();
        initRealmConfiguration1();
        initializeStetho(this);

    }

    private void initRealmConfiguration() {
        // Start the Realm db and set it as the default db
        Realm.init(this);
        RealmConfiguration base = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(base);
        // Starts te Realm db for the locations
    }
    private void initRealmConfiguration1() {
        Realm.init(this);
        RealmConfiguration locationsDB = new RealmConfiguration.Builder()
                .name("LOCATIONSDB.realm")
                .build();
        //Realm.setDefaultConfiguration(locationsDB);
    }
    private void initializeStetho(final BPApplication context) {

        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(RealmInspectorModulesProvider.builder(this)
                        .withDescendingOrder()
                        .withLimit(1000)
                        .databaseNamePattern(Pattern.compile(".+\\.realm"))
                        .build())
                .build());
    }

}
