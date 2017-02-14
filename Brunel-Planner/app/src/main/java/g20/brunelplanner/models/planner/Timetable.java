package g20.brunelplanner.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Timetable extends RealmObject {
    public static final String ID = "id";
    public static final String DAY = "day";
    public static final String ACTIVITY = "activity";
    public static final String DESCRIPTION = "description";
    public static final String START = "start";
    public static final String END = "end";
    public static final String WEEKS = "weeks";
    public static final String ROOM = "room";
    public static final String STAFF = "staff";
    public static final String TYPE = "type";

    @PrimaryKey
    @SerializedName("id")
    private int id;
    @SerializedName("day")
    private String day;
    @SerializedName("activity")
    private String activity;
    @SerializedName("description")
    private String description;
    @SerializedName("start")
    private String start;
    @SerializedName("end")
    private String end;
    @SerializedName("weeks")
    private RealmList<Weeks> weeks;
    @SerializedName("room")
    private String room;
    @SerializedName("staff")
    private String staff;
    @SerializedName("type")
    private String type;

    public Timetable(){ }

    public Timetable setId(int id){
        this.id = id;
        return this;
    }
    public int getId(){
        return this.id;
    }
    public Timetable setDay(String day){
        this.day = day;
        return this;
    }
    public String getDay(){
        return this.day;
    }
    public Timetable setActivity(String activity){
        this.activity = activity;
        return this;
    }
    public String getActivity(){
        return this.activity;
    }
    public Timetable setDescription(String description){
        this.description = description;
        return this;
    }
    public String getDescription(){
        return this.description;
    }
    public Timetable setStart(String start){
        this.start = start;
        return this;
    }
    public String getStart(){
        return this.start;
    }
    public Timetable setEnd(String end){
        this.end = end;
        return this;
    }
    public String getEnd(){
        return this.end;
    }
    public Timetable setWeeks(RealmList<Weeks> weeks){
        this.weeks = weeks;
        return this;
    }
    public RealmList<Weeks> getWeeks(){
        return this.weeks;
    }
    public Timetable setRoom(String room){
        this.room = room;
        return this;
    }
    public String getRoom(){
        return this.room;
    }
    public Timetable setStaff(String staff){
        this.staff = staff;
        return this;
    }
    public String getStaff(){
        return this.staff;
    }
    public Timetable setType(String type){
        this.type = type;
        return this;
    }
    public String getType(){
        return this.type;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || other.getClass() != getClass())
            return false;

        if (other == this)
            return true;

        Timetable otherObj = (Timetable) other;
        return id == otherObj.id;
    }
}