package com.tug.tugiloc.run;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class Compass implements SensorEventListener  {
    private Context ctx;
    private SensorManager mSm;

    private Sensor mSensorMagnetometer;
    private Sensor mSensorAccelerometer;
    private Sensor mSensorRotation;

    private float[] mGravity = new float[3];
    private float[] mGeomagnetic = new float[3];

    private float mAzimuth = 0;
    private float mAzimuthOffset = 0;

    public Compass(Context ctx){
        this.ctx = ctx;

        mSm = (SensorManager) ctx.getSystemService(Context.SENSOR_SERVICE);
        mSensorAccelerometer =  mSm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorMagnetometer =   mSm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        mSm.registerListener(this, mSensorAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        mSm.registerListener(this, mSensorMagnetometer, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public float getAzimuth_deg(){
        return this.mAzimuth;
    }


    public void setAzimuthOffset(float offset){
        this.mAzimuthOffset = offset;
    }

    public float getAzimuthOffset(){
        return this.mAzimuthOffset;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float azimuth;
        switch (event.sensor.getType()){
            case Sensor.TYPE_ACCELEROMETER:
                onSensorChanged_Acceleormeter(event);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                onSensorChanged_Magnetometer(event);
                break;
            default:
                break;
        }

        try{
            azimuth = calcAzimuth(mGravity, mGeomagnetic);
        }catch (Exception e){
            Log.d("Compass","has no Rotation Matrix");
            azimuth = this.mAzimuth;
        }

        this.mAzimuth = azimuth;
    }

    private void onSensorChanged_Acceleormeter(SensorEvent event){
        final float alpha = 0.9f;

        for(int i = 0; i < 3; i++) {
            mGravity[i] = alpha * mGravity[i] + (1 - alpha) * event.values[i];
        }
    }

    private void onSensorChanged_Magnetometer(SensorEvent event){
        final float alpha = 0.9f;

        for(int i = 0; i < 3; i++) {
            mGeomagnetic[i] = alpha * mGeomagnetic[i] + (1 - alpha) * event.values[i];
        }
    }

    private void onSensorChanged_Rotation(SensorEvent event){

    }

    private float calcAzimuth(float[] gravity, float[] geomagnetic) throws Exception{
        float azimuth = 0;
        float[] R = new float[9];
        float[] I = new float[9];

        boolean hasRotationMatrix = SensorManager.getRotationMatrix(R, I, gravity, geomagnetic);

        if(hasRotationMatrix){
            float orientation[] = new float[3];
            SensorManager.getOrientation(R, orientation);

            azimuth = (float) Math.toDegrees(orientation[0]);
            azimuth = (azimuth + mAzimuthOffset + 360) % 360;

        }else{
            throw new Exception();
        }

        return azimuth;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
