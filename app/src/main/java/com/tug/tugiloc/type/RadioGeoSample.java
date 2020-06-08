package com.tug.tugiloc.type;

import android.location.Location;
import android.net.wifi.ScanResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

public class RadioGeoSample extends GeoPoint {
    private ArrayList<RadioNetworkSample> wifiScanResult = new ArrayList<RadioNetworkSample>();

    public RadioGeoSample(int aLatitudeE6, int aLongitudeE6) {
        super(aLatitudeE6, aLongitudeE6);
    }
    public RadioGeoSample(int aLatitudeE6, int aLongitudeE6, int aAltitude) {
        super(aLatitudeE6, aLongitudeE6, aAltitude);
    }
    public RadioGeoSample(double aLatitude, double aLongitude) {
        super(aLatitude, aLongitude);
    }
    public RadioGeoSample(double aLatitude, double aLongitude, double aAltitude) {
        super(aLatitude, aLongitude, aAltitude);
    }
    public RadioGeoSample(Location aLocation) {
        super(aLocation);
    }
    public RadioGeoSample(GeoPoint aGeopoint) {
        super(aGeopoint);
    }
    public RadioGeoSample(IGeoPoint pGeopoint) {
        super(pGeopoint);
    }
    public RadioGeoSample(double aLatitude, double aLongitude, ArrayList<RadioNetworkSample> aScanResults){
        super(aLatitude, aLongitude);
        this.setWifiScanResult(aScanResults);
    }
    public RadioGeoSample(GeoPoint aGeopoint, ArrayList<RadioNetworkSample> aScanResult){
        super(aGeopoint);
        this.setWifiScanResult(aScanResult);
    }
    public RadioGeoSample(IGeoPoint aGeopoint, ArrayList<RadioNetworkSample> aScanResult){
        super(aGeopoint);
        this.setWifiScanResult(aScanResult);
    }

    // ---------------------------------------------------------------------------------------------
    public void setWifiScanResult(ArrayList<RadioNetworkSample> wifiScanResult) {
        this.wifiScanResult = wifiScanResult;
    }

    public ArrayList<RadioNetworkSample> getWifiScanResult() {
        return wifiScanResult;
    }

    // ---------------------------------------------------------------------------------------------

    public JSONObject makeJSON(RadioNetworkSample s){
        JSONObject ret = new JSONObject();
        try {
            ret.put("ssid", s.SSID);
            ret.put("bssid", s.BSSID);
            ret.put("level", s.level);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return ret;
    }

    public JSONObject makeJSON(){
        JSONObject ret = new JSONObject();
        JSONArray scans = new JSONArray();

        try {
            for(RadioNetworkSample s : wifiScanResult){
                scans.put(makeJSON(s));
            }

            ret.put("latitude", this.getLatitude());
            ret.put("longitude", this.getLongitude());
            ret.put("scanResults", scans);

        }catch (JSONException e){
            e.printStackTrace();
        }

        return ret;
    }

    public static RadioGeoSample loadFromJSON(String json_str){
        JSONObject json = null;
        JSONArray json_scanResults = null;
        GeoPoint p = null;
        ArrayList<RadioNetworkSample> scanResults = new ArrayList<>();

        try{
            json = new JSONObject(json_str);
            json_scanResults = json.getJSONArray("scanResults");
            for(int i = 0; i < json_scanResults.length(); i++) {
                JSONObject json_scanResult = json_scanResults.getJSONObject(i);
            }



            p = new GeoPoint(json.getDouble("latitude"), json.getDouble("longitude"));


        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }

        return new RadioGeoSample(p, scanResults);
    }
}
