package com.tug.tugiloc.type;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.GroundOverlay;

import java.util.ArrayList;

public class IndoorMap extends GroundOverlay {
    private String          mapName;

    public String           getMapName(){return mapName; }
    public void             setMapName(String mapName){this.mapName = mapName; }

    static public IndoorMap getSampleMap(){
        IndoorMap map = new IndoorMap();

        final String imgPath =  "/storage/D340-1035/Austria_Graz_Inffeldgasse_16_01.png";
        Matrix rot = new Matrix();
        rot.postRotate(270);
        Bitmap img = BitmapFactory.decodeFile(imgPath);
        img = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), rot, true);

        map.setImage(img);
        GeoPoint tr = new GeoPoint(47.058630, 15.458638);
        GeoPoint tl = new GeoPoint(47.058188, 15.457521);
        GeoPoint br = new GeoPoint(47.058502, 15.458745);
        GeoPoint bl = new GeoPoint(47.058062, 15.457632);


        map.setPosition(tr,br,bl,tl);
        map.setMapName("Austria, Graz, Inffeldgasse 16");

        return map;
    }
}

