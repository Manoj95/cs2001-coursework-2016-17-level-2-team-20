package g20.brunelplanner.views.activities.adapters;

import android.os.Bundle;
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
import g20.brunelplanner.views.activities.fragments.BuildingsFragment;
import g20.brunelplanner.views.activities.fragments.MapFragment;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class BuildingsAdapter extends RealmRecyclerViewAdapter<Locations,
        BuildingsAdapter.MyBuildingsHolder> {

    public BuildingsAdapter(BuildingsFragment activity, RealmResults<Locations> data2) {
        super(activity.getActivity().getApplicationContext(), data2, true);
    }

    @Override
    public MyBuildingsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyBuildingsHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.buildings_row, parent, false));
    }

    @Override
    public void onBindViewHolder(MyBuildingsHolder holder, int position) {
        Locations data = getData().get(position);
        holder.data = data;
        holder.building.setText(data.getBuilding());
    }

    static class MyBuildingsHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView building;

        public Locations data;

        MyBuildingsHolder(View view) {
            super(view);

            cardView = (CardView) itemView.findViewById(R.id.card_view_buildings);
            building = (TextView) view.findViewById(R.id.event_building);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Bundle bundle = new Bundle();
                    String mapLocationString = String.valueOf(building.getText());
                    bundle.putString("mapLocation", mapLocationString);
                    bundle.putString("type", "building");

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment mapFragment = new MapFragment();
                    mapFragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, mapFragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }

        // Add more functions here
    }
}