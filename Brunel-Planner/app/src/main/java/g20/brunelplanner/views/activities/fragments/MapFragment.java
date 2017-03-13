package g20.brunelplanner.views.activities.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
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
import g20.brunelplanner.controllers.databases.MapService;
import g20.brunelplanner.views.activities.MainActivity;
import g20.brunelplanner.views.activities.adapters.BuildingsAdapter;
import g20.brunelplanner.views.activities.adapters.RecyclerViewAdapter;

import static g20.brunelplanner.R.id.map;


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
                        double[] object = MapService.queryBuildingsDB(BuildingsAdapter.buildingtemp);
                        double mapLat = object[0];
                        double mapLong = object[1];
                        Log.d("Map", "onMapReady: " + mapLat);
                        Log.d("Map", "onMapReady: " + mapLong);
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng( mapLat, mapLong), 18.0f));
                        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        final LatLng MARKER = new LatLng( mapLat, mapLong);
                        Marker marker = googleMap.addMarker(new MarkerOptions()
                                .position(MARKER));


                        } else {
                        double[] object = MapService.queryRoomsDB(RecyclerViewAdapter.locationtemp);
                        double mapLat = object[0];
                        double mapLong = object[1];
                        Log.d("Map", "onMapReady: " + mapLat);
                        Log.d("Map", "onMapReady: " + mapLong);
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng( mapLat, mapLong), 18.0f));
                        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        final LatLng MARKER = new LatLng( mapLat, mapLong);
                        Marker marker = googleMap.addMarker(new MarkerOptions()
                                .position(MARKER));

                    }
                }
            });
        }
    }
}