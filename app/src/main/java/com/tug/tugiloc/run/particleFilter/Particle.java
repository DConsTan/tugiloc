package com.tug.tugiloc.run.particleFilter;

import com.tug.tugiloc.type.Step;
import com.tug.tugiloc.type.Wall;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

public class Particle {
    private double mWheight;
    private GeoPoint mPos;
    private double mDirection;

    final double alpha = 0.7;

    public Particle(){}

    public Particle(GeoPoint pos, double dir){
        this();
        this.setPosition(pos);
        this.setDirection(dir);
    }
    public void setPosition(GeoPoint pos){
        this.mPos = pos;
    }
    public GeoPoint getPosition(){
        return this.mPos;
    }
    public void setDirection(double dir){
        this.mDirection = dir;
    }
    public double getDirection(){
        return this.mDirection;
    }

    public boolean step(Step step, ArrayList<Wall> walls){
        GeoPoint dest = mPos.destinationPoint(step.getLength(), step.getDirection());
        for(Wall w : walls){
            if(w.intersects(this.getPosition(), dest)) return false;
        }

        this.setPosition(dest);
        return true;
    }


    /**
     * make a step for a point and check for collisions
     * @p point of origin
     * @s step from origin
     * @return GeoPoint
     * @return null if wall has been hitted
     * */
    /*
    private GeoPoint pointStep(GeoPoint p, Step s){
        GeoPoint dest = p.destinationPoint(s.getLength(), s.getDirection());
        for (int i = 0; i < mWalls.size(); i++){
            if(mWalls.get(i).intersects(p, dest)){
                return null;
            }
        }
        return dest;
    }
    */

}
