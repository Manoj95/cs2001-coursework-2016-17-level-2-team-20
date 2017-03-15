package g20.brunelplanner.views.activities.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import g20.brunelplanner.R;
import g20.brunelplanner.controllers.databases.RealmController;
import g20.brunelplanner.models.planner.Timetable;
import g20.brunelplanner.utils.CurrentWeek;
import g20.brunelplanner.views.activities.adapters.TimetableAdapter;


public class TimetableFragment extends Fragment implements WeekDialogFragment.WeekDialogListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private int week;

    protected RealmController realmController;

    public TimetableFragment() {
        // ...
    }

    private void setUpRecyclerView() {
        // There may be a better way to init this
        realmController = RealmController.getInstance();

        try {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Week " + week);
        } catch (NullPointerException e) {
            Log.e("TimetableFragment", "onCreateView: ", e);
        }

        // This is needed for some reason
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        // Set the adapter for the recycler view and populate with the realm data
        recyclerView.setAdapter(new TimetableAdapter(this, realmController.getRealm()
                        .where(Timetable.class)
                        .equalTo("weeks.val", week)
                        .findAllAsync()));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case R.id.action_change_week:
                changeWeek();
                return true;
            case R.id.action_reset_week:
                week = CurrentWeek.getCurrentWeek();
                setUpRecyclerView();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        setUpRecyclerView();

        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_timetable, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        realmController.closeRealm();
    }

    public void changeWeek() {
        FragmentManager fm = getFragmentManager();
        WeekDialogFragment weekDialogFragment = new WeekDialogFragment();
        weekDialogFragment.setTargetFragment(TimetableFragment.this, 300);
        weekDialogFragment.show(fm, "week_dialog");
    }

    @Override
    public void onFinishWeekDialog(int week) {
        this.week = week;
        setUpRecyclerView();
    }

}