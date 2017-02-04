package g20.brunelplanner.views.activities.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import g20.brunelplanner.R;
import g20.brunelplanner.controllers.databases.RealmController;
import g20.brunelplanner.models.Timetable;
import g20.brunelplanner.views.activities.adapters.RecyclerViewAdapter;


public class TimetableFragment extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    protected final RealmController realmController = RealmController.getInstance();

    public TimetableFragment() {
        // Required empty public constructor
    }

    private void setUpRecyclerView() {
        // This is needed for some reason
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        // Set the adapter for the recycler view and populate with the realm data
        recyclerView.setAdapter(new RecyclerViewAdapter(this,
                realmController.getRealm().where(Timetable.class).findAllAsync()));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);
        ButterKnife.bind(this, view);
        setUpRecyclerView();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realmController.closeRealm();
    }

}
