package g20.brunelplanner.views.activities.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import g20.brunelplanner.R;
import g20.brunelplanner.controllers.databases.MapService;

import static g20.brunelplanner.R.id.map;


public class MapFragment extends Fragment {

    private String mapLocation;
    private String type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapLocation = this.getArguments().getString("mapLocation");
        type = this.getArguments().getString("type");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentManager fm = getChildFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(map);

        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(map, mapFragment).commit();
        } else {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(final GoogleMap googleMap) {

                    double[] object;

                    if (type.equals("room")) {
                        object = MapService.queryRoomsDB(mapLocation);
                    } else {
                        object = MapService.queryBuildingsDB(mapLocation);
                    }

//                    // Not found
//                    if (object[0] == 0) {
//                        lo
//                    }

                    try {
                        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(mapLocation);
                    } catch (NullPointerException e) {
                        Log.e("MapFragment", "onCreateView: ", e);
                    }

                    double mapLat = object[0];
                    double mapLong = object[1];
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng( mapLat, mapLong), 18.0f));
                    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    googleMap.setBuildingsEnabled(true);
                    final LatLng MARKER = new LatLng(mapLat, mapLong);
                    googleMap.addMarker(new MarkerOptions()
                            .position(MARKER));

                }
            });
        }
    }
}