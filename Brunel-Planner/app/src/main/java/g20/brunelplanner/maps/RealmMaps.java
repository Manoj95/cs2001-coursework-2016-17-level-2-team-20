package g20.brunelplanner.maps;


import io.realm.Realm;

import static g20.brunelplanner.controllers.databases.RealmController.realm;

public class RealmMaps {

    public RealmMaps(){
        realm = Realm.getDefaultInstance();

        //save_into_database(2,"LE","Lecture Center", 51.533133, -0.472878);


    }

}

