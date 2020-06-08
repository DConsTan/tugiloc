package com.tug.tugiloc.type;

import org.osmdroid.util.GeoPoint;

public class Wall {
    private GeoPoint[] mVertices;

    public Wall (GeoPoint[] vertices){
        if(vertices.length != 2) return;
        this.mVertices = vertices;
    }
    public Wall (GeoPoint a, GeoPoint b){
        mVertices = new GeoPoint[2];
        mVertices[0] = a;
        mVertices[1] = b;
    }

    public GeoPoint[] getVertices(){
        return mVertices;
    }

    /*
    public boolean intersects(GeoPoint m, GeoPoint n){
        double x1 = mVertices[0].getLongitude();
        double x2 = mVertices[1].getLongitude();
        double x3 = m.getLongitude();
        double x4 = n.getLongitude();
        double y1 = mVertices[0].getLatitude();
        double y2 = mVertices[1].getLatitude();
        double y3 = m.getLatitude();
        double y4 = n.getLatitude();

        double[][] A = new double[2][2];
        double[][] B_1 = new double[2][2];
        double[][] B_2 = new double[2][2];

        // Cramersche Regel
        A[0][0] = x2 - x1;
        A[0][1] = x4 - x3;
        A[1][0] = y2 - y1;
        A[1][1] = y4 - y3;

        B_1[0][0] = x3 - x1;
        B_1[0][1] = x4 - x3;
        B_1[1][0] = y3 - y1;
        B_1[1][1] = y4 - y3;

        B_2[0][0] = x2 - x1;
        B_2[0][1] = x3 - x1;
        B_2[1][0] = y2 - y1;
        B_2[1][1] = y3 - y1;

        double s = det(B_1)/det(A);
        double t = det(B_2)/det(A);

        //check for intersection
        if(!(s >= 0 && s <= 1)) return false;
        if(!(t >= 0 && t <= 1)) return false;
        return true;
    }
    */


    public boolean intersects(final GeoPoint pc, final GeoPoint pd){
        final GeoPoint pa = this.mVertices[0];
        final GeoPoint pb = this.mVertices[1];

        final double x1 = pa.getLongitude();
        final double y1 = pa.getLatitude();
        final double x2 = pb.getLongitude();
        final double y2 = pb.getLatitude();
        final double x3 = pc.getLongitude();
        final double y3 = pc.getLatitude();
        final double x4 = pd.getLongitude();
        final double y4 = pd.getLatitude();

        // LGS Ax = b
        double[][] A = new double[2][2];
        A[0][0] = x2 - x1;
        A[0][1] = x4 - x3;
        A[1][0] = y2 - y1;
        A[1][1] = y4 - y3;

        double[] b = new double[2];
        b[0] = x3 - x1;
        b[1] = y3 - y1;

        // solve for x={s, t}
        double[] x = solv(A, b);

        // check for intersection
        if(!(x[0] >= 0 && x[0] <= 1)) return false;
        if(!(x[1] >= 0 && x[1] <= 1)) return false;

        return true;
    }


    /**
     * solve 2x2 LGS Ax=b
     * @param A
     * @param b
     * @return solution x
     */
    static private double[] solv(final double[][] A, final double[] b){
        double[] x = new double[2];
        double[][] A1 = new double [2][2];
        A1[0][0] = b[0];
        A1[0][1] = A[0][1];
        A1[1][0] = b[1];
        A1[1][1] = A[1][1];

        double[][] A2 = new double [2][2];
        A2[0][0] = A[0][0];
        A2[0][1] = b[0];
        A2[1][0] = A[1][0];
        A2[1][1] = b[1];

        x[0] = det(A1)/det(A);
        x[1] = det(A2)/det(A);

        return x;
    }

    static private double det(final double[][] A){
        return (A[0][0] * A[1][1]) - (A[0][1] * A[1][0]);
    }

}
