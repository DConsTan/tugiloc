package com.tug.tugiloc;

import com.tug.tugiloc.type.Wall;

import org.junit.Test;
import org.osmdroid.util.GeoPoint;

import static org.junit.Assert.*;

public class WallTest {
    @Test
    public void intersection_1(){
        GeoPoint a = new GeoPoint(-1.0,0.0);
        GeoPoint b = new GeoPoint( 1.0, 0.0);
        Wall wall = new Wall(a, b);


        GeoPoint r = new GeoPoint(0.0, 1.0);
        GeoPoint s = new GeoPoint( 0.0, -1.0);
        assertTrue(wall.intersects(r, s));
    }

    @Test
    public void intersection_2(){
        GeoPoint a = new GeoPoint(1,1.0);
        GeoPoint b = new GeoPoint( 3.0, 2.0);
        Wall wall = new Wall(a, b);


        GeoPoint r = new GeoPoint(1.0, 4.0);
        GeoPoint s = new GeoPoint( 2.0, -1.0);
        assertTrue(wall.intersects(r, s));
    }

    @Test
    public void no_intersection_1(){
        GeoPoint a = new GeoPoint(-1.0,0.0);
        GeoPoint b = new GeoPoint( 1.0, 0.0);
        Wall wall = new Wall(a, b);

        GeoPoint r = new GeoPoint(0.0, 2.0);
        GeoPoint s = new GeoPoint(0.0, 3.0);

        assertFalse(wall.intersects(r, s));
    }

    @Test
    public void no_intersection_2(){

        assertFalse(false);
    }
}
