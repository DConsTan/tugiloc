package com.tug.tugiloc.run.radioLoc;

import android.location.Location;
import android.net.wifi.ScanResult;
import android.provider.MediaStore;
import android.util.Log;

import com.tug.tugiloc.type.RadioGeoSample;
import com.tug.tugiloc.type.RadioNetworkSample;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.LinkedList;

public class RadioLoc {
    private final String LOG_TAG = "RadioLoc";
    private ArrayList<RadioGeoSample> radioGeoSamples;

    public RadioLoc(){
        radioGeoSamples = new ArrayList<>();
    }
    public RadioLoc(ArrayList<RadioGeoSample> samples){
        radioGeoSamples = samples;
    }



    //----------------------------------------------------------------------------------------------
    // Localization
    public class LocalizationResult{
        public GeoPoint p;
        public double uncert_m;
        public double euclideanDistance;
    }

    public LocalizationResult localize(ArrayList<ScanResult> samples, int nSize){
        ArrayList<RadioAnchorDist> neighborhood = new ArrayList<>();

        for(RadioGeoSample rgs : radioGeoSamples){
            RadioAnchorDist rad = new RadioAnchorDist();
            rad.distance = distance(rgs, samples);
            rad.pos = rgs;

            // insert in ordered results list
            for(int i = 0; i < neighborhood.size(); i++){
                if(rad.distance < neighborhood.get(i).distance){
                    neighborhood.add(i, rad);
                    break;
                }
            }

            // fill list if needed
            if(neighborhood.size() < nSize){
                neighborhood.add(rad);
            }



            // shrink List to neighborhood size if needed
            if(neighborhood.size() >= nSize){
                for(int i = nSize; i < neighborhood.size(); i++){
                    neighborhood.remove(i);
                }
            }
        }
        Log.d(LOG_TAG, "neighborhood has " + neighborhood.size() + " samples");
        for(RadioAnchorDist d : neighborhood){
            Log.d(LOG_TAG, "\t" + d.pos.getLongitude() + ":" + d.pos.getLatitude() + " dist: " + d.distance);
        }

        // calculate neighborhood geoSize,
        double avgDist = 0.0;
        double nGeoDistance = 0.0;
        double nGeoAvg_lat  = 0.0;
        double nGeoAvg_long = 0.0;
        for(RadioAnchorDist rad : neighborhood){
            avgDist += rad.distance;
            nGeoAvg_lat  += rad.pos.getLatitude();
            nGeoAvg_long += rad.pos.getLongitude();
        }
        avgDist = avgDist/nSize;
        nGeoAvg_lat = nGeoAvg_lat/nSize;
        nGeoAvg_long = nGeoAvg_long/nSize;
        GeoPoint nGeoAvg = new GeoPoint(nGeoAvg_lat, nGeoAvg_long);
        for(RadioAnchorDist rad : neighborhood){
            float[] res = new float[3];
            Location.distanceBetween(nGeoAvg_lat, nGeoAvg_long, rad.pos.getLatitude(), rad.pos.getLongitude(), res);
            nGeoDistance += res[0];
        }
        nGeoDistance = nGeoDistance/nSize;


        LocalizationResult res = new LocalizationResult();
        res.euclideanDistance = avgDist;
        res.p = nGeoAvg;
        res.uncert_m = nGeoDistance;

        return res;
    }


    /**
     * Euclidean distance between two RadioGeoSamples
     * @param a
     * @param b
     * @return
     */
    private double distance(RadioGeoSample a, RadioGeoSample b){
        double dist = 0.0;
        boolean hasMatch = false;

        for(RadioNetworkSample arns : a.getWifiScanResult()){
            for(RadioNetworkSample brns : b.getWifiScanResult()){
                if(arns.BSSID == brns.BSSID){
                    hasMatch = true;
                    dist += (arns.level - brns.level)^2;
                }
            }
        }

        dist = Math.sqrt(dist);

        if(!hasMatch) return Double.POSITIVE_INFINITY;
        return dist;
    }

    private double distance(RadioGeoSample a, ArrayList<ScanResult> b){
        double dist = 0.0;
        boolean hasMatch = false;

        for(RadioNetworkSample arns : a.getWifiScanResult()){
            for(ScanResult brns : b){
                if(arns.BSSID.equals(brns.BSSID)){
                    hasMatch = true;
                    dist += ((-1 / arns.level) - (-1 / brns.level))^2;
                }else{
                    dist += (0 - (-1 / arns.level))^2;
                    Log.d(LOG_TAG, "" + arns.BSSID  +"!="+ brns.BSSID);
                }
            }
        }

        dist = Math.sqrt(dist);

        if(!hasMatch) return Double.POSITIVE_INFINITY;
        return dist;
    }

    private class RadioAnchorDist{
        public double distance;
        public GeoPoint pos;

    }

    // ---------------------------------------------------------------------------------------------



    // ---------------------------------------------------------------------------------------------
    // GET - SET
    public ArrayList<RadioGeoSample> getRadioGeoSamples() {
        return radioGeoSamples;
    }
    public void setRadioGeoSamples(ArrayList<RadioGeoSample> radioGeoSamples) {
        this.radioGeoSamples = radioGeoSamples;
    }
    public void addRadioSample(RadioGeoSample rgs){
        this.radioGeoSamples.add(rgs);
    }
    // ---------------------------------------------------------------------------------------------
}
