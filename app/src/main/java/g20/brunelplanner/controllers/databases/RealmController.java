package g20.brunelplanner.controllers.databases;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;

import g20.brunelplanner.models.Timetable;
import io.realm.Realm;
import io.realm.RealmResults;

public class RealmController {
    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {
        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }

        return instance;
    }

    public static RealmController with(Activity activity) {
        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }

        return instance;
    }

    public static RealmController with(Application application) {
        if (instance == null) {
            instance = new RealmController(application);
        }

        return instance;
    }

    public static RealmController getInstance() {
        return instance;
    }

    public Realm getRealm() {
        return realm;
    }

    public RealmResults<Timetable> getTimetable() {
        return realm.where(Timetable.class).findAll();
    }

    public Timetable getBook(String id) {
        return realm.where(Timetable.class).equalTo("id", id).findFirst();
    }

}
