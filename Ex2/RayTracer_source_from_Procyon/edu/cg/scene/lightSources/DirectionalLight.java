// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.scene.lightSources;

import edu.cg.scene.objects.Surface;
import edu.cg.algebra.Ray;
import edu.cg.algebra.Point;
import edu.cg.algebra.Vec;

public class DirectionalLight extends Light
{
    private Vec direction;
    
    public DirectionalLight() {
        this.direction = new Vec(0.0, -1.0, -1.0);
    }
    
    public DirectionalLight initDirection(final Vec direction) {
        this.direction = direction.normalize();
        return this;
    }
    
    @Override
    public String toString() {
        final String endl = System.lineSeparator();
        return "Directional Light:" + endl + super.toString() + "Direction: " + this.direction + endl;
    }
    
    @Override
    public DirectionalLight initIntensity(final Vec intensity) {
        return (DirectionalLight)super.initIntensity(intensity);
    }
    
    @Override
    public Ray rayToLight(final Point fromPoint) {
        return new Ray(fromPoint, this.direction.neg());
    }
    
    @Override
    public boolean isOccludedBy(final Surface surface, final Ray rayToLight) {
        return surface.intersect(rayToLight) != null;
    }
    
    @Override
    public Vec intensity(final Point hittingPoint, final Ray rayToLight) {
        return this.intensity;
    }
}
