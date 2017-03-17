package g20.brunelplanner.views.activities.adapters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import g20.brunelplanner.R;
import g20.brunelplanner.models.planner.Timetable;
import g20.brunelplanner.views.activities.fragments.MapFragment;
import g20.brunelplanner.views.activities.fragments.TimetableFragment;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class TimetableAdapter extends RealmRecyclerViewAdapter<Timetable,
        TimetableAdapter.MyViewHolder> {

    private int[] eventsInDays = {-1, -1, -1, -1, -1};

    public TimetableAdapter(TimetableFragment activity, RealmResults<Timetable> data) {
        super(activity.getActivity().getApplicationContext(), data, true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        getDays();
        return new MyViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.timetable_row,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // Display event
        Timetable data = getData().get(position);
        holder.data = data;
        String desc = data.getDescription();

        if (!desc.isEmpty()) {
            holder.activity.setText(data.getDescription());
        } else {
            holder.activity.setText(data.getActivity());
        }

        String staff = data.getStaff();

        if (staff.isEmpty()) {
            holder.type_and_staff.setText(data.getType());
        } else {
            List<String> fullName = Arrays.asList(staff.split(",[ ]*"));
            Collections.reverse(fullName);
            holder.type_and_staff
                    .setText(data.getType() + " / " + fullName.get(0) + " " + fullName.get(1));
        }

        String time = data.getStart() + " - " + data.getEnd();
        holder.time.setText(time);
        holder.location.setText(data.getRoom());
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView activity;
        TextView type_and_staff;
        Button time;
        Button location;

        public Timetable data;

        MyViewHolder(View view) {
            super(view);
            cardView = (CardView) itemView.findViewById(R.id.card_view_timetable);
            activity = (TextView) view.findViewById(R.id.event_activity);
            type_and_staff = (TextView) view.findViewById(R.id.event_type_and_staff);
            time = (Button) view.findViewById(R.id.event_time);
            location = (Button) view.findViewById(R.id.event_location);

            location.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Bundle bundle = new Bundle();
                    String mapLocationString = String.valueOf(location.getText());
                    bundle.putString("mapLocation", mapLocationString);
                    bundle.putString("type", "room");

                    AppCompatActivity rootActivity = (AppCompatActivity) view.getContext();
                    Fragment mapFragment = new MapFragment();
                    mapFragment.setArguments(bundle);
                    rootActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, mapFragment)
                            .addToBackStack(null)
                            .commit();
                }

            });
        }

    }

//    private void getDays() {
//        OrderedRealmCollection<Timetable> data = getData();
//
//        try {
//            eventsInDays[0] = data.where().equalTo("day", "Monday").findFirst().getId();
//            eventsInDays[1] = data.where().equalTo("day", "Tuesday").findFirst().getId();
//            eventsInDays[2] = data.where().equalTo("day", "Wednesday").findFirst().getId();
//            eventsInDays[3] = data.where().equalTo("day", "Thursday").findFirst().getId();
//            eventsInDays[4] = data.where().equalTo("day", "Friday").findFirst().getId();
//        } catch (NullPointerException e) {
//            Log.e("Adapter", "getDays: ", e);
//        }
//
//    }

    @Nullable
    @Override
    public OrderedRealmCollection<Timetable> getData() {
        return super.getData();
    }

}