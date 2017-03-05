package g20.brunelplanner.views.activities.adapters;

import android.app.FragmentTransaction;
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

import butterknife.BindView;
import butterknife.OnClick;
import g20.brunelplanner.R;
import g20.brunelplanner.models.planner.Timetable;
import g20.brunelplanner.views.activities.fragments.MapFragment;
import g20.brunelplanner.views.activities.fragments.TimetableFragment;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;


public class RecyclerViewAdapter extends RealmRecyclerViewAdapter<Timetable,
        RecyclerViewAdapter.MyViewHolder> {

    public RecyclerViewAdapter(TimetableFragment activity, OrderedRealmCollection<Timetable> data) {
        super(activity.getActivity().getApplicationContext(), data, true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false));

    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
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

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView activity;
        TextView type_and_staff;
        TextView time;
        Button location;

        @BindView(R.id.event_location)
        Button open_maps;
        @OnClick(R.id.event_location)
        public void openMAPS() {
            MapFragment openmaps = new MapFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, openmaps, "Maps")
                    .addToBackStack("null")
                    .commit();
        }
        public Timetable data;

        MyViewHolder(View view) {
            super(view);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            activity = (TextView) view.findViewById(R.id.event_activity);
            type_and_staff = (TextView) view.findViewById(R.id.event_type_and_staff);
            time = (TextView) view.findViewById(R.id.event_time);
            location = (Button) view.findViewById(R.id.event_location);


        }

        // Add more functions here
    }



}