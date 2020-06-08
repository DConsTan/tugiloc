package com.tug.tugiloc.ui.map;

import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tug.tugiloc.R;
import com.tug.tugiloc.run.RadioScanner;
import com.tug.tugiloc.run.particleFilter.Particle;
import com.tug.tugiloc.run.radioLoc.RadioLoc;
import com.tug.tugiloc.type.IndoorMap;
import com.tug.tugiloc.type.RadioGeoSample;
import com.tug.tugiloc.ui.itiTestData.itiWalls;
import com.tug.tugiloc.run.Compass;
import com.tug.tugiloc.run.particleFilter.ParticleFilterLoc;
import com.tug.tugiloc.type.Step;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay;
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions;
import org.osmdroid.views.overlay.simplefastpoint.SimplePointTheme;

import java.util.ArrayList;

public class MapFragment extends Fragment implements SensorEventListener {

    private ActionMode mRadioSamplesAction = null;
    private MapView map = null;
    private ViewModel mViewModel;
    private MapEventsReceiver mapEventRecv;
    private ScaleBarOverlay mScaleBarOverlay = null;
    private MinimapOverlay mMinimapOverlay = null;
    private CompassOverlay mCompassOverlay = null;
    private SimpleFastPointOverlay mParticleOverly = null;

    private InternalCompassOrientationProvider mInternalCompass = null;
    private FloatingActionButton fab_map_wifi;
    private FloatingActionButton fab_step;
    private FloatingActionButton fab_pos;
    private Context ctx;
    private ArrayList<Marker> radioSampleMarkers;
    private Marker radioPositionMarker = null;
    private Polygon radioPositionCircle = null;
    private Compass mCompass;
    private ParticleFilterLoc mLoc;
    private SensorManager mSM;
    private Sensor mSensorStepDetector;
    private RadioScanner mRadioScanner;

    private RadioLoc mRadioLoc;
    private ArrayList<RadioGeoSample> mRadioGeoSamples;

    private final double avg_stepLength = 0.6;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mLoc = new ParticleFilterLoc();
        mLoc.addWalls(itiWalls.getITIWalls());

        mRadioScanner = new RadioScanner(getContext());
        mRadioGeoSamples = mRadioScanner.loadRadioGeoSamples();
        mRadioLoc = new RadioLoc();
        mRadioLoc.setRadioGeoSamples(mRadioGeoSamples);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_marker, menu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void startWiFiMapAction(){
        if(mRadioSamplesAction == null) {
            mRadioSamplesAction = getActivity().startActionMode(mMarkerActionCallback, ActionMode.TYPE_PRIMARY);
            drawRadioSampleMarker();
            Toast.makeText(getActivity(), "New Action", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(), "old Action", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupFloatingButton(){
        fab_map_wifi = (FloatingActionButton) getView().findViewById(R.id.fab_map_wifi);
        fab_step = (FloatingActionButton) getView().findViewById(R.id.fab_step);
        fab_pos = (FloatingActionButton) getView().findViewById(R.id.fab_pos);


        fab_map_wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startWiFiMapAction();
            }
        });
        fab_step.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(getContext(),"Step", Toast.LENGTH_SHORT).show();
                Step step = new Step(avg_stepLength, mCompass.getAzimuth_deg());
                mLoc.makeStep(step);
                drawParticleSet(mLoc.getParticleSet());
            }
        });
        fab_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ScanResult> sr =  mRadioScanner.scanWiFi();
                RadioLoc.LocalizationResult lr = mRadioLoc.localize(sr, 3);
                makeRadioPositionMarker(lr.p, lr.uncert_m);
                Log.d("MapFragment", "new radio Positon: " + lr.p.getLatitude() + ":" + lr.p.getLongitude());
            }
        });
    }

    private void setupMap(){
        Configuration.getInstance().setUserAgentValue(ctx.getPackageName());
        map = (MapView) getActivity().findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);
        map.getOverlays().add(new MapEventsOverlay(mapEventRecv));
    }

    private void setupCompassOverlay(){
        mInternalCompass = new InternalCompassOrientationProvider(ctx);
        this.mCompassOverlay = new CompassOverlay(ctx, mInternalCompass, map);
        this.mCompassOverlay.enableCompass();
        map.getOverlays().add(this.mCompassOverlay);

    }

    private void setupScaleBar(){
        final DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
        mScaleBarOverlay = new ScaleBarOverlay(map);
        mScaleBarOverlay.setCentred(true);
        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels/2, 10);
        map.getOverlays().add(this.mScaleBarOverlay);
    }

    private void setupMiniMap(){
        final DisplayMetrics dm = ctx.getResources().getDisplayMetrics();

        mMinimapOverlay = new MinimapOverlay(ctx, map.getTileRequestCompleteHandler());
        mMinimapOverlay.setWidth(dm.widthPixels / 5);
        mMinimapOverlay.setHeight(dm.heightPixels / 5);
        mMinimapOverlay.setZoomDifference(4);
        map.getOverlays().add(this.mMinimapOverlay);
    }

    private void zoomToInffeld(){
        map.post(
            new Runnable(){
                @Override
                public void run(){
                    BoundingBox inffeldgasse = new BoundingBox(47.0588, 15.4586, 47.0580, 15.4576);
                    map.zoomToBoundingBox(inffeldgasse, false);
                }
            }
        );
        //drawITIWalls();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        ctx = getContext();
        initSensor(ctx);

        mapEventRecv = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                if(mRadioSamplesAction == null){
                    makeParticleSet(p);
                }else{
                    makeRadioMarker(p);
                }
                return true;
            }
        };

        //setupContexMenu();
        setupFloatingButton();

        setupMap();
        setupScaleBar();
        //setupMiniMap();
        setupFloatingButton();
        setupCompassOverlay();

        zoomToInffeld();

        map.getOverlayManager().add(IndoorMap.getSampleMap());

        // Marker on TUG Inffeldgasse
        //Marker tug = new Marker(map);
        //tug.setPosition(new GeoPoint(47.0583, 15.4581));
        //tug.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        //map.getOverlays().add(tug);
    }

    @Override
    public void onResume(){
        super.onResume();
        map.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
        map.onPause();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MapViewModel.class);
    }

    private Marker makeMarker(GeoPoint pos){
        Marker mark = new Marker(map);

        mark.setPosition(pos);
        mark.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(mark);
        map.invalidate();

        return mark;
    }

    private void makeRadioPositionMarker(GeoPoint pos, double uncert){
        if(radioPositionMarker != null){
            map.getOverlays().remove(radioPositionMarker);
        }
        if(radioPositionCircle != null){
            map.getOverlays().remove(radioPositionCircle);
        }
        map.invalidate();

        //For Marker
        radioPositionMarker = new Marker(map);
        radioPositionMarker.setPosition(pos);
        radioPositionMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(radioPositionMarker);

        //For circle
        radioPositionCircle = new Polygon();
        ArrayList<GeoPoint> cirlePoints = new ArrayList<>();
        for(float f = 0; f < 360; f+=1){
            cirlePoints.add(new GeoPoint(pos.getLatitude(), pos.getLongitude()).destinationPoint(uncert, f));
        }
        radioPositionCircle.setPoints(cirlePoints);
        map.getOverlays().add(radioPositionCircle);

        mLoc.initParticleSet(1000, pos, uncert/2);
        drawParticleSet(mLoc.getParticleSet());

        map.invalidate();
    }

    private void makeParticleSet(GeoPoint pos){
        final double deviation_m = 1.0;
        mLoc.initParticleSet(1000, pos, deviation_m);
        drawParticleSet( mLoc.getParticleSet());
    }

    private void drawParticleSet(ArrayList<Particle> set){
        ArrayList<IGeoPoint> part = new ArrayList<IGeoPoint>();

        for(Particle p : set){
            part.add(p.getPosition());
        }

        SimplePointTheme pt = new SimplePointTheme((ArrayList<IGeoPoint>) part, false);
        SimpleFastPointOverlayOptions opt = SimpleFastPointOverlayOptions.getDefaultStyle()
                .setAlgorithm(SimpleFastPointOverlayOptions.RenderingAlgorithm.MAXIMUM_OPTIMIZATION)
                .setRadius(7)
                .setIsClickable(false)
                .setCellSize(15);
        if(mParticleOverly != null){
            map.getOverlays().remove(mParticleOverly);
        }
        mParticleOverly = new SimpleFastPointOverlay(pt, opt);
        map.getOverlays().add(mParticleOverly);
        map.invalidate();
    }

    private void drawRadioSampleMarker(){
        for(RadioGeoSample rgs : mRadioGeoSamples){
            makeRadioMarker(rgs);
        }
    }

    private void drawITIWalls(){
        ArrayList<Polyline> itiW = itiWalls.getITIWalls_poly();

        for(int i = 0; i < itiW.size(); i++){
            map.getOverlays().add(itiW.get(i));
        }
        map.invalidate();

    }

    private Marker makeRadioMarker(GeoPoint pos){
        Log.d("MapFragment", "new RadioMarker " + pos.getLatitude() + ", " + pos.getLongitude());

        Marker mark = new Marker(map);
        mark.setTitle("test");
        MarkerInfoWindow iw = new MarkerInfoWindow(R.layout.sample_radio_marker_view, map);

        mark.setInfoWindow(iw);

        mark.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                Toast.makeText(ctx, "You touched me", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        Drawable markIcon = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_radio_location, null);

        mark.setPosition(pos);
        mark.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mark.setIcon(markIcon);
        mark.setDraggable(true);
        map.getOverlays().add(mark);
        map.invalidate();

        radioSampleMarkers.add(mark);

        return mark;
    }

    private ActionMode.Callback mMarkerActionCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            radioSampleMarkers = new ArrayList<>();
            mode.getMenuInflater().inflate(R.menu.menu_marker, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            Toast.makeText(ctx, "Pushed item " + item.getTitle(), Toast.LENGTH_LONG).show();
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            radioSampleMarkers = null;
            mRadioSamplesAction = null;
        }
    };




/*
    *****************************************
    SENSOR Logic
    *****************************************
 */
    private void initSensor(Context ctx){
        mSM = (SensorManager) ctx.getSystemService(Context.SENSOR_SERVICE);
        mSensorStepDetector =    mSM.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        mCompass = new Compass(ctx);
        mCompass.setAzimuthOffset(4.37f);

        mSM.registerListener(this, mSensorStepDetector, SensorManager.SENSOR_DELAY_FASTEST);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        switch(event.sensor.getType()){
            case Sensor.TYPE_STEP_DETECTOR:
                onSensorChanged_stepDetector(event);
                break;
            default:
                Log.d("MapFragmnet","unhandled sensor event ...");
                break;
        }
    }

    private void onSensorChanged_stepDetector(SensorEvent event){
        mLoc.makeStep(new Step(avg_stepLength, mCompass.getAzimuth_deg()));
        drawParticleSet(mLoc.getParticleSet());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}
