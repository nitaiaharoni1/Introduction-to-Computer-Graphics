// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.algebra;

import java.awt.Color;

public class Vec
{
    public double x;
    public double y;
    public double z;
    
    public Vec(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vec(final double val) {
        this(val, val, val);
    }
    
    public Vec(final Vec other) {
        this(other.x, other.y, other.z);
    }
    
    public Vec() {
        this(0.0);
    }
    
    public double norm() {
        return Ops.norm(this);
    }
    
    public double normSqr() {
        return Ops.normSqr(this);
    }
    
    public double length() {
        return Ops.length(this);
    }
    
    public double lengthSqr() {
        return Ops.lengthSqr(this);
    }
    
    public Vec normalize() {
        return Ops.normalize(this);
    }
    
    public Vec neg() {
        return Ops.neg(this);
    }
    
    public double dot(final Vec other) {
        return Ops.dot(this, other);
    }
    
    public Vec cross(final Vec other) {
        return Ops.cross(this, other);
    }
    
    public Vec mult(final double a) {
        return Ops.mult(a, this);
    }
    
    public Vec mult(final Vec v) {
        return Ops.mult(this, v);
    }
    
    public Vec add(final Vec v) {
        return Ops.add(this, v);
    }
    
    public boolean isFinite() {
        return Ops.isFinite(this);
    }
    
    public Color toColor() {
        return new Color(clip(this.x), clip(this.y), clip(this.z));
    }
    
    private static float clip(final double val) {
        return (float)Math.min(1.0, Math.max(0.0, val));
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
