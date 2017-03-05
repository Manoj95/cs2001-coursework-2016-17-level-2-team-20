package g20.brunelplanner.maps;

import com.google.gson.annotations.SerializedName;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Locations extends RealmObject {
    public static final String ID = "id";
    public static final String ROOM = "room";
    public static final String BUILDING = "building";
    public static final String LAT = "lat";
    public static final String LONGIT = "longit";

    @PrimaryKey
    @SerializedName("id")
    public int id;
    @SerializedName("room")
    public String room;
    @SerializedName("building")
    public String building;
    @SerializedName("lat")
    public double lat;
    @SerializedName("longit")
    public double longit;

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



}