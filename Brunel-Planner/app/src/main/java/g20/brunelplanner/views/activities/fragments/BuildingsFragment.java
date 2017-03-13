package g20.brunelplanner.views.activities.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import g20.brunelplanner.R;
import g20.brunelplanner.controllers.databases.RealmController;
import g20.brunelplanner.models.map.Locations;
import g20.brunelplanner.views.activities.adapters.BuildingsAdapter;


public class BuildingsFragment extends Fragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    protected RealmController realmController;

    public BuildingsFragment() {
        // ...
    }

    private void setUpRecyclerView() {
        // There may be a better way to init this
        realmController = RealmController.getInstance();

        // This is needed for some reason
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        // Set the adapter for the recycler view and populate with the realm data
        recyclerView.setAdapter(new BuildingsAdapter(this,
                realmController.getRealm()
                        .where(Locations.class)
                        .distinct("building")));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        try {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("Buildings List");
        } catch (NullPointerException e) {
            Log.e("BuildingsFragment", "onCreateView: ", e);
        }
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
}