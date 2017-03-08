package g20.brunelplanner.maps;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

import static android.text.TextUtils.substring;
import static io.realm.Case.INSENSITIVE;

public class Locations extends RealmObject {
    public static final String ID = "id";
    public static final String ROOM = "room";
    public static final String BUILDING = "building";
    public static final String LAT = "lat";
    public static final String LONGIT = "longit";

    @PrimaryKey
    @SerializedName("id")
    private int id;
    @SerializedName("room")
    private String room;
    @SerializedName("building")
    private String building;
    @SerializedName("lat")
    private double lat;
    @SerializedName("longit")
    private double longit;

    public Locations(){ }

    public Locations setId(int id){
        this.id = id;
        return this;
    }
    public int getId(){
        return this.id;
    }

    public Locations setRoom(String room){
        this.room = room;
        return this;
    }
    public String getRoom(){
        return this.room;
    }

    public Locations setBuilding(String building){
        this.building = building;
        return this;
    }
    public String getBuilding(){
        return this.building;
    }


    public Locations setLat(double lat){
        this.lat = lat;
        return this;
    }
    public double getLat(){
        return this.lat;
    }

    public Locations setLongit(double longit){
        this.longit = longit;
        return this;
    }
    public double getLongit(){
        return this.longit;
    }

    public static Locations queryDB(Realm realm1, String locations1) {
        //Realm locationsDB = Realm.getDefaultInstance();
        Locations object;
        String rooms = locations1;
        String first2chars = substring(rooms, 0, 2);
        RealmResults<Locations> result = realm1.where(Locations.class)
                .beginsWith("room", first2chars, INSENSITIVE)
                .findAll();
        object = result.first();
        //locationsDB.close();
        return object;

    }
    public static void save_into_database(final int id, final String room, final String building, final double lat, final double longit){
        Realm locationsDB = Realm.getDefaultInstance();
        locationsDB.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm locationsDB) {
                Locations locations = locationsDB.createObject(Locations.class, id);
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
        locationsDB.close();
    }
    public static void writing_into_database() {
        Realm locationsDB = Realm.getDefaultInstance();
        Locations.save_into_database(1,"LE","Lecture Center", 51.533133, -0.472878);
        Locations.save_into_database(2,"HA","Halsbury", 51.533824, -0.472942);
        Locations.save_into_database(3,"ST","ST Johns", 51.534514, -0.469374);
        locationsDB.close();
    }

}