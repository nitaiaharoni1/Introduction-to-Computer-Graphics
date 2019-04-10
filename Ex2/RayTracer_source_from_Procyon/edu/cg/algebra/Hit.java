// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.algebra;

import edu.cg.scene.objects.Surface;

public class Hit implements Comparable<Hit>
{
    private final double t;
    private final Vec normalToSurface;
    private boolean isWithin;
    private Surface surface;
    
    public Hit(final double t, final Vec normalToSurface) {
        this.isWithin = false;
        this.surface = null;
        this.t = t;
        this.normalToSurface = normalToSurface;
    }
    
    public Vec getNormalToSurface() {
        return this.normalToSurface;
    }
    
    public Surface getSurface() {
        return this.surface;
    }
    
    public void setSurface(final Surface surface) {
        this.surface = surface;
    }
    
    public boolean isWithinTheSurface() {
        return this.isWithin;
    }
    
    public Hit setIsWithin(final boolean isWithin) {
        this.isWithin = isWithin;
        return this;
    }
    
    public Hit setWithin() {
        return this.setIsWithin(true);
    }
    
    public Hit setOutside() {
        return this.setIsWithin(false);
    }
    
    public double t() {
        return this.t;
    }
    
    @Override
    public int compareTo(final Hit other) {
        return (this.t < other.t) ? -1 : ((this.t > other.t) ? 1 : 0);
    }
}
