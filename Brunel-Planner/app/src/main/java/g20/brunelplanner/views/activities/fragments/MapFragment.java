package g20.brunelplanner.views.activities.fragments;

/**
 * Created by rosuc on 3/2/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import g20.brunelplanner.R;
import g20.brunelplanner.maps.Locations;
import g20.brunelplanner.views.activities.MainActivity;
import g20.brunelplanner.views.activities.adapters.RecyclerViewAdapter;

import static g20.brunelplanner.R.id.map;
import static g20.brunelplanner.controllers.databases.RealmController.realm;


public class MapFragment extends Fragment {
    private SupportMapFragment mapFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentManager fm = getChildFragmentManager();
        mapFragment = (SupportMapFragment) fm.findFragmentById(map);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(map, mapFragment).commit();

        } else {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(final GoogleMap googleMap) {
                    if (MainActivity.count) {
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.533074, -0.475620), 16.0f));
                        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        //googleMap.setMyLocationEnabled(true);
                        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                        } else {
                        double lat1;
                        double longit1;
                        Locations object;
                        object = Locations.queryDB( realm, RecyclerViewAdapter.MyViewHolder.locationtemp);
                        lat1 = object.getLat();
                        longit1 = object.getLongit();
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng( lat1, longit1), 16.0f));
                        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        final LatLng MARKER = new LatLng( lat1, longit1);
                        Marker marker = googleMap.addMarker(new MarkerOptions()
                                .position(MARKER));


                        //googleMap.setMyLocationEnabled(true);
                    }
                }
            });
        }
    }
}