package g20.brunelplanner.views.activities.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;

import g20.brunelplanner.R;
import g20.brunelplanner.models.Timetable;
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
        Timetable obj = getData().get(position);
        holder.data = obj;
        holder.activity.setText(obj.getActivity());
        holder.room_and_type.setText(obj.getRoom() + " [" + obj.getType() + "]");
        holder.staff.setText(obj.getStaff());

        int[] weeks = new int[obj.getWeeks().size()];
        for (int i = 0; i < obj.getWeeks().size(); i++) {
            weeks[i] = obj.getWeeks().get(i).getVal();
        }

        holder.weeks.setText(Arrays.toString(weeks));
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView activity;
        TextView room_and_type;
        TextView staff;
        TextView weeks;
        public Timetable data;

        MyViewHolder(View view) {
            super(view);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            activity = (TextView) view.findViewById(R.id.event_activity);
            room_and_type = (TextView) view.findViewById(R.id.event_room_and_type);
            staff = (TextView) view.findViewById(R.id.event_staff);
            weeks = (TextView) view.findViewById(R.id.event_weeks);
        }

        // Add more functions here
    }
}