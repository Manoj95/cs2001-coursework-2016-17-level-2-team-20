package g20.brunelplanner.controllers.databases;

import android.util.Log;

import g20.brunelplanner.models.map.Locations;
import io.realm.Realm;
import io.realm.RealmResults;

import static android.text.TextUtils.substring;
import static io.realm.Case.INSENSITIVE;

public class MapService {

    private static RealmController realmController;

    public static double[] queryBuildingsDB(String building) {
        realmController = RealmController.getInstance();

        RealmResults<Locations> result = realmController.getRealm().where(Locations.class)
                .equalTo("building", building, INSENSITIVE)
                .findAllAsync();

        double[] cords = new double[2];

        cords[0] = result.first().getLat();
        cords[1] = result.first().getLong();

        realmController.closeRealm();

        return cords;
    }

    public static double[] queryRoomsDB(String rooms) {
        realmController = RealmController.getInstance();

        String first2chars = substring(rooms, 0, 2);
        RealmResults<Locations> result = realmController.getRealm().where(Locations.class)
                .beginsWith("room", first2chars, INSENSITIVE)
                .findAllAsync();

        double[] cords = new double[2];

        cords[0] = result.first().getLat();
        cords[1] = result.first().getLong();

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
                locations.setLong(mapLong);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
//                Log.v("YES", "stored ok");
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
        saveLocation(1, "AA", "Antonin Artaud", 51.530937, -0.474962);
        saveLocation(2, "CH", "Chadwick", 51.532718, -0.479091);
        saveLocation(3, "ES", "Eastern Gateway", 51.533511, -0.468578);
        saveLocation(4, "EL", "Elliot Jacques", 51.531864, -0.467464);
        saveLocation(5, "GB", "Gaskell", 51.533005, -0.478175);
        saveLocation(6, "H0", "Howell", 51.531997, -0.473255);
        saveLocation(7, "H1", "Howell", 51.531997, -0.473255);
        saveLocation(8, "H3", "Howell", 51.531997, -0.473255);
        saveLocation(9, "HA", "Halsbury", 51.533824, -0.472942);
        saveLocation(10, "HW", "Heinz Wolff", 51.534068, -0.474828);
        saveLocation(11, "IA", "Indoor Atletics Center", 51.532391, -0.469586);
        saveLocation(12, "IC", "Indoor Atletics Center", 51.532391, -0.469586);
        saveLocation(13, "JC", "John Crank", 51.533349, -0.472041);
        saveLocation(14, "LE", "Lecture Center", 51.533133, -0.472878);
        saveLocation(15, "LI", "Russell", 51.532088, -0.468454);
        saveLocation(16, "RB", "Russell", 51.532088, -0.468454);
        saveLocation(17, "MJ", "Marie Jahoda", 51.532964, -0.476786);
        saveLocation(18, "ML", "Michael Sterling", 51.532998, -0.474905);
        saveLocation(19, "MS", "Mary Seacole", 51.532840, -0.468527);
        saveLocation(20, "PA", "Sports Pavilion", 51.533388, -0.470243);
        saveLocation(21, "SJ", "St Johns", 51.534514, -0.469374);
        saveLocation(22, "TA", "Tower A", 51.532064, -0.474151);
        saveLocation(23, "TB", "Tower B", 51.531500, -0.474181);
        saveLocation(24, "TC", "Tower C", 51.531356, -0.473483);
        saveLocation(25, "TD", "Tower D", 51.531408, -0.472808);
    }

}
