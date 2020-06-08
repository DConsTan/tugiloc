package com.tug.tugiloc.type;

public class Step {
    private double mLength;
    private double mDirection;

    public Step(){}
    public Step(double length, double direction){
        this.mLength = length;
        this.mDirection = direction;
    }

    public double getDirection() {
        return mDirection;
    }

    public double getLength() {
        return mLength;
    }
}
