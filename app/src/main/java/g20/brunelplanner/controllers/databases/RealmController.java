package g20.brunelplanner.controllers.databases;

import io.realm.Realm;

public class RealmController {

    private static RealmController mInstance;
    private Realm realm;

    public static RealmController getInstance() {
        if (mInstance == null) {
            mInstance = new RealmController();
        }
        return mInstance;
    }

    public Realm getRealm() {
        return realm;
    }

    public void closeRealm() {
        if (realm != null) {
            realm.close();
            mInstance = null;
        }
    }

    public void deleteRealm() {
        if (realm != null) {
            // Double check realm is closed
            // There might be a better way to do this
            realm.close();
            Realm.deleteRealm(realm.getConfiguration());
        }
    }


    private RealmController() {
        this.realm = Realm.getDefaultInstance();
    }


}