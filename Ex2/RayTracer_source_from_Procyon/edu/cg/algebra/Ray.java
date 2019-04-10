// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.algebra;

public class Ray
{
    private final Point source;
    private final Vec direction;
    
    public Ray(final Point source, final Vec direction) {
        this.source = source;
        this.direction = direction.normalize();
    }
    
    public Ray(final Point p0, final Point p1) {
        this(p0, p1.sub(p0).normalize());
    }
    
    public Point source() {
        return this.source;
    }
    
    public Vec direction() {
        return this.direction;
    }
    
    public Point add(final double t) {
        return this.source.add(t, this.direction);
    }
    
    public Point getHittingPoint(final Hit hit) {
        return this.add(hit.t());
    }
    
    public Ray inverse() {
        return new Ray(this.source, this.direction.neg());
    }
}
