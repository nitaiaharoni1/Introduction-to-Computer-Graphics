// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.algebra;

public class Point
{
    public double x;
    public double y;
    public double z;
    
    public Point(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Point(final double val) {
        this(val, val, val);
    }
    
    public Point() {
        this(0.0);
    }
    
    public Point add(final double t, final Vec v) {
        return Ops.add(this, t, v);
    }
    
    public Point add(final Vec v) {
        return Ops.add(this, v);
    }
    
    public Point mult(final double a) {
        return Ops.mult(a, this);
    }
    
    public Point mult(final Point p) {
        return Ops.mult(this, p);
    }
    
    public Point add(final Point p) {
        return Ops.add(this, p);
    }
    
    public double dist(final Point other) {
        return Ops.dist(this, other);
    }
    
    public double distSqr(final Point other) {
        return Ops.distSqr(this, other);
    }
    
    public Vec sub(final Point other) {
        return Ops.sub(this, other);
    }
    
    public Vec toVec() {
        return this.sub(new Point());
    }
    
    public boolean isFinite() {
        return Ops.isFinite(this);
    }
    
    public double[] asArray() {
        final double[] ret = { this.x, this.y, this.z };
        return ret;
    }
    
    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }
}
