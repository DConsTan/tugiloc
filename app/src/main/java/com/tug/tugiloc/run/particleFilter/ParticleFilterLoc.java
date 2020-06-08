package com.tug.tugiloc.run.particleFilter;

import android.content.Context;
import android.util.Log;

import com.tug.tugiloc.type.Step;
import com.tug.tugiloc.type.Wall;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.Random;

public class ParticleFilterLoc {
    private ArrayList<Wall> mWalls;
    private ArrayList<Particle> mParticleSet;
    private double[] mParticleWeight;
    private int mParticleSetMaxSize;
    private ArrayList<Step> mPath;


    public ParticleFilterLoc() {
        mWalls = new ArrayList<Wall>();
        mParticleSet = new ArrayList<Particle>();
        mPath = new ArrayList<Step>();
    }

    public ParticleFilterLoc(Context ctx, int particleSetSize, GeoPoint tl, GeoPoint tr, GeoPoint br, GeoPoint bl) {
        this();
        //initParticleSet(particleSetSize, tl, tr, br, bl);
    }

    public ArrayList<Step> getPath() {
        return mPath;
    }

    public void setPath(ArrayList<Step> path) {
        mPath = path;
    }

    public void addStep(Step step) {
        mPath.add(step);
    }

    public void addWall(Wall w) {
        mWalls.add(w);
    }

    public void removeWall(Wall w) {
        mWalls.remove(w);
    }

    public void addWalls(ArrayList<Wall> walls){
        mWalls.addAll(walls);
    }

    public ArrayList<Particle> getParticleSet() {
        return mParticleSet;
    }

    public void setParticleSet(ArrayList<Particle> particleSet) {
        this.mParticleSet = particleSet;
        this.mParticleSetMaxSize = particleSet.size();
    }

    public void setParticleSetMaxSize(int size){
        this.mParticleSetMaxSize = size;
    }
    public int getParticleSetMaxSize(){
        return mParticleSetMaxSize;
    }


    public void makeStep(Step step){
        Random rand = new Random();
        final double fanFact = 0.0;

        addStep(step);
        Log.d("ParticleFilterLocalization","new Step length: " + step.getLength() + ", direction: " + step.getDirection());

        ArrayList<Particle> nextGen = new ArrayList<Particle>();
        //for(int i = 0; i < mParticleSet.size(); i++){
        for (Particle op : mParticleSet){
            final double lengthRand = (rand.nextGaussian()*0.2) + step.getLength();
            final double dirRand = (rand.nextGaussian()*45);

            GeoPoint pos = op.getPosition();
            Particle np1 = new Particle(pos.destinationPoint(step.getLength()+lengthRand, step.getDirection()+dirRand), step.getDirection()+dirRand);
            //Particle np2 = new Particle(pos.destinationPoint(step.getLength(), step.getDirection()-fanFact), step.getDirection());
            //Particle np3 = new Particle(pos.destinationPoint(step.getLength(), step.getDirection()+fanFact), step.getDirection());

            if(!hasHitWall(pos, np1.getPosition())){
                nextGen.add(np1);
            }
            /*
            if(!hasHitWall(pos, np2.getPosition())){
                nextGen.add(np2);
            }
            if(!hasHitWall(pos, np3.getPosition())){
                nextGen.add(np3);
            }*/
        }
        mParticleSet = nextGen;
        //calcParticleWeight();


    }

    private boolean hasHitWall(final GeoPoint a, final GeoPoint b){
        for(int i = 0; i < mWalls.size(); i++){
            if(mWalls.get(i).intersects(a, b)) return true;
        }

        return false;
    }


    private void fillParticleSet(){
        //calc mean position
        double meanLat = 0;
        double meanLong = 0;
        for(int i = 0; i < mParticleSet.size(); i++){
            meanLat += mParticleSet.get(i).getPosition().getLatitude();
            meanLong += mParticleSet.get(i).getPosition().getLongitude();
        }
        GeoPoint meanPos = new GeoPoint(meanLat, meanLong);

        for(int i = mParticleSet.size(); i < mParticleSetMaxSize; i++){

        }
    }

    /*
    public void initParticleSet(int n, GeoPoint tl, GeoPoint tr, GeoPoint br, GeoPoint bl){
        mParticleSet = new ArrayList<Particle>();
        for(int i = 0; i < n; i++){
            mParticleSet.add(newRandomPoint(tl, tr, br, bl));
        }
        calcParticleWeight();
    }
    */

    /**
     * initializes a Particle set around given point
     * @param n number of particles
     * @param c center of particles
     * @param stdDev_m standard deviation around c in metres
     */
    public void initParticleSet(int n, GeoPoint c, double stdDev_m){
        Random rand = new Random();
        mParticleSet = new ArrayList<Particle>();
        for(int i = 0; i < n; i++){
            double randDist = rand.nextGaussian()*stdDev_m;
            double randDir = rand.nextDouble()*360;
            double randOri = rand.nextDouble()*360;

            GeoPoint pos = c.destinationPoint(randDist, randDir);
            Particle np = new Particle(pos, randOri);

            mParticleSet.add(np);
        }
    }

    private void calcParticleWeight(){
        int size = mParticleSet.size();
        mParticleWeight = new double[size];
        for(int i = 0; i < mParticleWeight.length; i++){
            mParticleWeight[i] = 1/size;
        }
    }

    private static GeoPoint newRandomPoint(GeoPoint tl, GeoPoint tr, GeoPoint br, GeoPoint bl){
        double width  = tl.getLongitude() - tr.getLongitude();
        double height = tl.getLatitude() - bl.getLatitude();

        if(width < 0) width = width*-1;
        if(height < 0) height = height*-1;

        Random rand = new Random();
        double x = rand.nextDouble() * width;
        double y = rand.nextDouble() * height;

        return new GeoPoint(bl.getLatitude()+y, bl.getLongitude()+x);
    }
}
