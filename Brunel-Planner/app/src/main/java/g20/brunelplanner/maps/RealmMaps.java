package g20.brunelplanner.maps;


import android.util.Log;

import io.realm.Realm;

import static g20.brunelplanner.controllers.databases.RealmController.realm;

public class RealmMaps {

    public RealmMaps(){
        realm = Realm.getDefaultInstance();
        save_into_database(1,"LE","Lecture Center",51.533133,-0.472878);
        save_into_database(2,"LE","Lecture Center",51.533133,-0.472878);


    }
    private void save_into_database(final int id, final String room, final String building, final double lat, final double longit){


        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Locations locations = bgRealm.createObject(Locations.class);
                locations.setId(id);
                locations.setRoom(room);
                locations.setBuilding(building);
                locations.setLat(lat);
                locations.setLongit(longit);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.v("YES","stored ok");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e("NO", error.getMessage());
            }
        });
    }
}

