package g20.brunelplanner.views.activities.adapters;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import g20.brunelplanner.R;
import g20.brunelplanner.models.map.Locations;
import g20.brunelplanner.views.activities.MainActivity;
import g20.brunelplanner.views.activities.fragments.BuildingsFragment;
import g20.brunelplanner.views.activities.fragments.MapFragment;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class BuildingsAdapter extends RealmRecyclerViewAdapter<Locations,
        BuildingsAdapter.MyBuildingsHolder> {

    public static String buildingtemp;

    public BuildingsAdapter(BuildingsFragment activity, RealmResults<Locations> data2) {
        super(activity.getActivity().getApplicationContext(), data2, true);
    }

    @Override
    public MyBuildingsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyBuildingsHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.row2, parent, false));
    }


    @Override
    public void onBindViewHolder(MyBuildingsHolder holder, int position) {
        Locations data2 = getData().get(position);
        holder.data2 = data2;
        holder.building.setText(data2.getBuilding());
    }

    public static class MyBuildingsHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView building;

        public Locations data2;

        public MyBuildingsHolder(View view) {
            super(view);
            cardView = (CardView) itemView.findViewById(R.id.card_view2);
            building = (TextView) view.findViewById(R.id.event_building);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    buildingtemp = String.valueOf(building.getText());
                    MainActivity.count = true;
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new MapFragment();
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, myFragment)
                            .addToBackStack(null)
                            .commit();
                }

            });
        }

        // Add more functions here
    }
}