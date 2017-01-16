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
import g20.brunelplanner.views.activities.MainActivity;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class RecyclerViewAdapter extends RealmRecyclerViewAdapter<Timetable, RecyclerViewAdapter.MyViewHolder> {

    private final MainActivity activity;

    public RecyclerViewAdapter(MainActivity activity, OrderedRealmCollection<Timetable> data) {
        super(activity, data, true);
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Timetable obj = getData().get(position);
        holder.data = obj;
        holder.activity.setText(obj.getActivity());
        holder.desc.setText(obj.getDescription());
        holder.room.setText(obj.getRoom());
        holder.desc.setText(obj.getStaff());
        holder.type.setText(obj.getType());

        int[] weeks = new int[obj.getWeeks().size()];
        for (int i = 0; i < obj.getWeeks().size(); i++) {
            weeks[i] = obj.getWeeks().get(i).getVal();
        }

        holder.weeks.setText(Arrays.toString(weeks));
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public TextView activity;
        public TextView desc;
        public TextView room;
        public TextView staff;
        public TextView type;
        public TextView weeks;
        public Timetable data;

        public MyViewHolder(View view) {
            super(view);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            activity = (TextView) view.findViewById(R.id.event_activity);
            desc = (TextView) view.findViewById(R.id.event_desc);
            room = (TextView) view.findViewById(R.id.event_room);
            staff = (TextView) view.findViewById(R.id.event_staff);
            type = (TextView) view.findViewById(R.id.event_type);
            weeks = (TextView) view.findViewById(R.id.event_weeks);
        }

        // Add more functions here
    }
}