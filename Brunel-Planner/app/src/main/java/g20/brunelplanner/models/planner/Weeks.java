package g20.brunelplanner.models.planner;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Weeks extends RealmObject {
    public static final String VAL = "val";

    @SerializedName("")
    private int val;

    public Weeks(){ }

    public Weeks setVal(int val){
        this.val = val;
        return this;
    }
    public int getVal(){
        return this.val;
    }
}