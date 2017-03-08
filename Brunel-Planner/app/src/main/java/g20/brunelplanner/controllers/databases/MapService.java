package g20.brunelplanner.controllers.databases;

import android.util.Log;

import g20.brunelplanner.models.map.Locations;
import io.realm.Realm;
import io.realm.RealmResults;

import static android.text.TextUtils.substring;
import static io.realm.Case.INSENSITIVE;

public class MapService {

    private static RealmController realmController;

    public static double[] queryDB(String rooms) {
        realmController = RealmController.getInstance();

        String first2chars = substring(rooms, 0, 2);
        RealmResults<Locations> result = realmController.getRealm().where(Locations.class)
                .beginsWith("room", first2chars, INSENSITIVE)
                .findAllAsync();

        double[] cords = new double[2];

        cords[0] = result.first().getLat();
        cords[1] = result.first().getLongit();

        realmController.closeRealm();

        return cords;

    }

    public static void saveLocation(final int id, final String room, final String building, final double mapLat, final double mapLong) {
        realmController = RealmController.getInstance();

        realmController.getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm location) {
                Locations locations = location.createObject(Locations.class, id);
                locations.setRoom(room);
                locations.setBuilding(building);
                locations.setLat(mapLat);
                locations.setLongit(mapLong);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.v("YES", "stored ok");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e("NO", error.getMessage());
            }
        });

        realmController.closeRealm();
    }

    public static void saveLocations() {
        saveLocation(1, "LE", "Lecture Center", 51.533133, -0.472878);
        saveLocation(2, "HA", "Halsbury", 51.533824, -0.472942);
        saveLocation(3, "ST", "ST Johns", 51.534514, -0.469374);
    }
}
