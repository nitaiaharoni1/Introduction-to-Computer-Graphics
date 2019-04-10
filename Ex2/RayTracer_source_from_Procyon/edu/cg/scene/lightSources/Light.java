// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.scene.lightSources;

import edu.cg.scene.objects.Surface;
import edu.cg.algebra.Ray;
import edu.cg.algebra.Point;
import edu.cg.algebra.Vec;

public abstract class Light
{
    protected Vec intensity;
    
    public Light() {
        this.intensity = new Vec(1.0, 1.0, 1.0);
    }
    
    @Override
    public String toString() {
        final String endl = System.lineSeparator();
        return "Intensity: " + this.intensity + endl;
    }
    
    public Light initIntensity(final Vec intensity) {
        this.intensity = intensity;
        return this;
    }
    
    public abstract Ray rayToLight(final Point p0);
    
    public abstract boolean isOccludedBy(final Surface p0, final Ray p1);
    
    public abstract Vec intensity(final Point p0, final Ray p1);
}
