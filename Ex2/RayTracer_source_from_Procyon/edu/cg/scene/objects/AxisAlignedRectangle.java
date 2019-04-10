// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.scene.objects;

import edu.cg.algebra.Vec;
import edu.cg.algebra.Hit;
import edu.cg.algebra.Ray;
import edu.cg.algebra.Point;

public class AxisAlignedRectangle extends Shape
{
    private Point minPoint;
    private Point maxPoint;
    private String name;
    private static int CURR_IDX;
    
    public AxisAlignedRectangle(final Point minPoint, final Point maxPoint) {
        this.name = "";
        this.minPoint = minPoint;
        this.maxPoint = maxPoint;
        this.name = new String("Box " + AxisAlignedRectangle.CURR_IDX);
        ++AxisAlignedRectangle.CURR_IDX;
        this.fixBoundryPoints();
    }
    
    public AxisAlignedRectangle() {
        this.name = "";
        this.minPoint = new Point(-1.0, -1.0, -1.0);
        this.maxPoint = new Point(1.0, 1.0, 1.0);
    }
    
    @Override
    public String toString() {
        final String endl = System.lineSeparator();
        return String.valueOf(this.name) + endl + "Min Point: " + this.minPoint + endl + "Max Point: " + this.maxPoint + endl;
    }
    
    public AxisAlignedRectangle initMinPoint(final Point minPoint) {
        this.minPoint = minPoint;
        this.fixBoundryPoints();
        return this;
    }
    
    public AxisAlignedRectangle initMaxPoint(final Point maxPoint) {
        this.maxPoint = maxPoint;
        this.fixBoundryPoints();
        return this;
    }
    
    @Override
    public Hit intersect(final Ray ray) {
        double tNear = -1.0E8;
        double tFar = 1.0E8;
        final double[] rayP = ray.source().asArray();
        final double[] rayD = ray.direction().asArray();
        final double[] minP = this.minPoint.asArray();
        final double[] maxP = this.maxPoint.asArray();
        for (int i = 0; i < 3; ++i) {
            if (Math.abs(rayD[i]) <= 1.0E-5) {
                if (rayP[i] < minP[i] || rayP[i] > maxP[i]) {
                    return null;
                }
            }
            else {
                double t1 = findIntersectionParameter(rayD[i], rayP[i], minP[i]);
                double t2 = findIntersectionParameter(rayD[i], rayP[i], maxP[i]);
                if (t1 > t2) {
                    final double tmp = t1;
                    t1 = t2;
                    t2 = tmp;
                }
                if (Double.isNaN(t1) || Double.isNaN(t2)) {
                    return null;
                }
                if (t1 > tNear) {
                    tNear = t1;
                }
                if (t2 < tFar) {
                    tFar = t2;
                }
                if (tNear > tFar || tFar < 1.0E-5) {
                    return null;
                }
            }
        }
        double minT = tNear;
        boolean isWithin = false;
        if (minT < 1.0E-5) {
            isWithin = true;
            minT = tFar;
        }
        Vec norm = this.normal(ray.add(minT));
        if (isWithin) {
            norm = norm.neg();
        }
        return new Hit(minT, norm).setIsWithin(isWithin);
    }
    
    private static double findIntersectionParameter(final double a, final double b, final double c) {
        if (Math.abs(a) < 1.0E-5 && Math.abs(b - c) > 1.0E-5) {
            return 1.0E8;
        }
        if (Math.abs(a) < 1.0E-5 && Math.abs(b - c) < 1.0E-5) {
            return 0.0;
        }
        final double t = (c - b) / a;
        return t;
    }
    
    private Vec normal(final Point p) {
        if (Math.abs(p.z - this.minPoint.z) <= 1.0E-5) {
            return new Vec(0.0, 0.0, -1.0);
        }
        if (Math.abs(p.z - this.maxPoint.z) <= 1.0E-5) {
            return new Vec(0.0, 0.0, 1.0);
        }
        if (Math.abs(p.y - this.minPoint.y) <= 1.0E-5) {
            return new Vec(0.0, -1.0, 0.0);
        }
        if (Math.abs(p.y - this.maxPoint.y) <= 1.0E-5) {
            return new Vec(0.0, 1.0, 0.0);
        }
        if (Math.abs(p.x - this.minPoint.x) <= 1.0E-5) {
            return new Vec(-1.0, 0.0, 0.0);
        }
        if (Math.abs(p.x - this.maxPoint.x) <= 1.0E-5) {
            return new Vec(1.0, 0.0, 0.0);
        }
        return null;
    }
    
    private void fixBoundryPoints() {
        final double min_x = Math.min(this.minPoint.x, this.maxPoint.x);
        final double max_x = Math.max(this.minPoint.x, this.maxPoint.x);
        final double min_y = Math.min(this.minPoint.y, this.maxPoint.y);
        final double max_y = Math.max(this.minPoint.y, this.maxPoint.y);
        final double min_z = Math.min(this.minPoint.z, this.maxPoint.z);
        final double max_z = Math.max(this.minPoint.z, this.maxPoint.z);
        this.minPoint = new Point(min_x, min_y, min_z);
        this.maxPoint = new Point(max_x, max_y, max_z);
    }
}
