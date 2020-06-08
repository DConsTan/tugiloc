package com.tug.tugiloc.ui.radio;

import androidx.lifecycle.ViewModelProviders;

import android.net.wifi.ScanResult;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tug.tugiloc.R;
import com.tug.tugiloc.adapter.ScanResultAdapter;
import com.tug.tugiloc.type.RadioGeoSample;
import com.tug.tugiloc.run.RadioScanner;
import com.tug.tugiloc.type.IndoorMap;

import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;

public class RadioFragment extends Fragment {
    private RadioScanner mRs;
    private Button btn_scan;
    private Button btn_save;
    private TextView txt_long;
    private TextView txt_lat;

    private RecyclerView            mRecyclerView;
    private ScanResultAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ScanResult> mScanResults;
    private MapView mMap;
    private MapEventsReceiver mMapEventRecv;
    private Marker mMarker;
    private RadioScanner mRadioScanner;


    private RadioViewModel mViewModel;

    public static RadioFragment newInstance() {
        return new RadioFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_radio, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RadioViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        btn_scan = (Button) getActivity().findViewById(R.id.btn_scan);
        btn_save = (Button) getActivity().findViewById(R.id.btn_saveSample);
        txt_lat = (TextView) getActivity().findViewById(R.id.txt_lat);
        txt_long = (TextView) getActivity().findViewById(R.id.txt_long);

        txt_lat.setText("0");
        txt_long.setText("0");

        mRadioScanner = new RadioScanner(getContext());

        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.rview_scanResults);
        mMap = (MapView) getActivity().findViewById(R.id.radio_map);
        //mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ScanResultAdapter(mScanResults);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        updateRadio();
        setupMap();

        mMap.post(
            new Runnable(){
                @Override
                public void run(){
                    //BoundingBox inffeldgasse = new BoundingBox(50, -2, 46, 5);
                    BoundingBox inffeldgasse = new BoundingBox(47.0588, 15.4586, 47.0580, 15.4576);
                    mMap.zoomToBoundingBox(inffeldgasse, false);
                }
            }
        );


        btn_scan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                updateRadio();
                Toast.makeText(getContext(), "found " + mScanResults.size() + " networks", Toast.LENGTH_SHORT).show();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mMarker == null){
                    Toast.makeText(getContext(), "Mark position first", Toast.LENGTH_LONG).show();
                    return;
                }
                GeoPoint p = new GeoPoint(mMarker.getPosition());
                RadioGeoSample rgs = mRadioScanner.makeRadioGeoSample(p);

                Log.d("RadioFragment","new WiFi Sample");
                Log.d("RadioFragment", rgs.makeJSON().toString());

                String fileName = "" + System.currentTimeMillis() + ".json";
                mRadioScanner.saveRadioGeoSample(rgs, fileName);


                Toast.makeText(getContext(), "Radio Sample Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void writeFile(String fileName){

    }

    private void makeSingleMarker(GeoPoint pos){
        if(mMarker != null){
            clearSingleMarker();
        }

        mMarker = new Marker(mMap);
        mMarker.setPosition(pos);
        mMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mMap.getOverlays().add(mMarker);
        mMap.invalidate();
    }

    private void clearSingleMarker(){
        txt_lat.setText("0");
        txt_long.setText("0");
        mMap.getOverlays().remove(mMarker);
        mMap.invalidate();
    }

    private void updatePosition(){
        txt_long.setText(""+mMarker.getPosition().getLongitude());
        txt_lat.setText(""+mMarker.getPosition().getLatitude());
    }

    private void updateRadio(){
        mScanResults = mRadioScanner.scanWiFi();
        mAdapter.setItems(mScanResults);
        mAdapter.notifyDataSetChanged();
    }

    private void setupMap(){
        Configuration.getInstance().setUserAgentValue(getContext().getPackageName());
        mMap = (MapView) getActivity().findViewById(R.id.radio_map);
        mMap.setTileSource(TileSourceFactory.MAPNIK);
        mMapEventRecv = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                //Toast.makeText(getActivity(), (String) "position: " + p.toDoubleString(), Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                //Toast.makeText(getActivity(), (String) "position: " + p.toDoubleString(), Toast.LENGTH_LONG).show();
                makeSingleMarker(p);
                updatePosition();
                return true;
            }
        };
        mMap.getOverlays().add(new MapEventsOverlay(mMapEventRecv));

        mMap.setMultiTouchControls(true);
        mMap.getOverlayManager().add(IndoorMap.getSampleMap());

    }
}
