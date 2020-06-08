package com.tug.tugiloc.run;

import android.content.Context;
import android.os.Environment;

import androidx.core.content.ContextCompat;

import com.tug.tugiloc.type.IndoorMap;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class IndoorMapManager {
    private Context ctx;
    private ArrayList<IndoorMap> maps = new ArrayList<IndoorMap>();
    private File[] externalStorageVolumes = ContextCompat.getExternalFilesDirs(ctx, null);

    static public IndoorMap loadMap(Context ctx, String mapName){
        IndoorMap ret = null;

        return  ret;
    }

    static public ArrayList<IndoorMap> loadMaps(Context ctx, String[] mapNames){
        ArrayList<IndoorMap> ret = new ArrayList<>();

        return ret;
    }

    static public void saveMap(Context ctx, IndoorMap map){

    }

    static public void deleteMap(Context ctx, IndoorMap map){

    }

    static private boolean isExternalStorageWriteable(){
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED;
    }

    static private boolean isExternalStorageReadable(){
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED || Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED_READ_ONLY;
    }
}
