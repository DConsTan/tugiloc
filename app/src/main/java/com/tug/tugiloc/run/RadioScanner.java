package com.tug.tugiloc.run;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import com.tug.tugiloc.type.RadioGeoSample;
import com.tug.tugiloc.type.RadioNetworkSample;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.util.GeoPoint;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class RadioScanner extends BroadcastReceiver {
    private final String LOG_TAG = "RadioScanner";
    private Context ctx;
    private WifiManager wifiManager;
    private IntentFilter intentFilter = new IntentFilter();
    private ArrayList<ScanResult> curWiFi = new ArrayList<>();
    private boolean hasNew_curWiFi = false;

    public RadioScanner(Context ctx){
        this.ctx = ctx;
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        ctx.registerReceiver(this, intentFilter);
        ctx.registerReceiver(this, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        wifiManager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
    }


    public ArrayList<ScanResult> scanWiFi(){
        wifiManager.startScan();

        // while(!hasNew_curWiFi){}
        this.hasNew_curWiFi = false;

        Log.d(LOG_TAG, "found " + this.curWiFi.size() + " WiFi networks");

        return (ArrayList<ScanResult>) this.curWiFi;
    }

    public ArrayList<RadioNetworkSample> scanWiFi_rns(){
        ArrayList<RadioNetworkSample> res = new ArrayList<>();

        for(ScanResult sr : this.curWiFi){
            RadioNetworkSample rns = new RadioNetworkSample();
            rns.BSSID = sr.BSSID;
            rns.SSID = sr.SSID;
            rns.level = sr.level;

            res.add(rns);
        }

        return res;
    }

    public RadioGeoSample makeRadioGeoSample(GeoPoint p){
        RadioGeoSample ret = new RadioGeoSample(p);
        ret.setWifiScanResult(this.scanWiFi_rns());
        return ret;
    }

    public void saveRadioGeoSample(RadioGeoSample rgs, String fileName){
        try {
            File sampleDir = new File(ctx.getFilesDir(), "wifi_samples");
            File of = new File(sampleDir, fileName);

            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(ctx.getFilesDir() + "/wifi_samples/" + fileName));
            osw.write(rgs.makeJSON().toString());
            osw.close();
        }catch ( IOException e){
            e.printStackTrace();
        }
    }

    public ArrayList<RadioGeoSample> loadRadioGeoSamples(){
        ArrayList<RadioGeoSample> ret = new ArrayList<>();
        ArrayList<String> fileNames = new ArrayList<>();
        File sampleDir = new File(ctx.getFilesDir(), "wifi_samples");
        File[] sampleFiles = sampleDir.listFiles();

        Log.d(LOG_TAG, "found " + sampleFiles.length + " files in directory " + sampleDir.getPath());

        for (File f : sampleFiles){
            ret.add(json2rgs(loadRadioGeoSample_file(f)));
        }

        return ret;
    }

    private JSONObject loadRadioGeoSample_file(File file){
        Log.d(LOG_TAG, "\tloading " + file);
        String json_str = null;
        JSONObject ret = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            json_str = br.readLine();
        } catch (IOException e){
            Log.d(LOG_TAG, "IO Exception");
            e.printStackTrace();
            return null;
        }

        try{
            ret = new JSONObject(json_str);
        }catch (JSONException e){
            Log.d(LOG_TAG, "JSON Exception");
            e.printStackTrace();
            return null;
        }

        return ret;
    }

    private RadioGeoSample json2rgs(JSONObject json){
        GeoPoint p = null;
        ArrayList<RadioNetworkSample> scanRes = null;

        try {
            p = new GeoPoint(json.getDouble("latitude"), json.getDouble("longitude"));
            scanRes = new ArrayList<>();
            JSONArray jscanRes = json.getJSONArray("scanResults");
            for(int i = 0; i < jscanRes.length(); i++){
                JSONObject sr = jscanRes.getJSONObject(i);
                RadioNetworkSample rns = new RadioNetworkSample();
                rns.SSID = sr.getString("ssid");
                rns.BSSID = sr.getString("bssid");
                rns.level = sr.getInt("level");
                scanRes.add(rns);
            }
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }

        return new RadioGeoSample(p, scanRes);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        switch(intent.getAction()){
            case WifiManager.SCAN_RESULTS_AVAILABLE_ACTION:
                this.curWiFi = (ArrayList) wifiManager.getScanResults();
                this.hasNew_curWiFi = true;
                break;

            default:
                Toast.makeText(ctx, "WiFi changed", Toast.LENGTH_SHORT).show();
        }

    }
}
