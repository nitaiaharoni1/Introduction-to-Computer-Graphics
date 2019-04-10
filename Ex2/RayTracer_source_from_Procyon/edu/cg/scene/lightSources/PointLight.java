// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.scene.lightSources;

import edu.cg.algebra.Hit;
import edu.cg.scene.objects.Surface;
import edu.cg.algebra.Ray;
import edu.cg.algebra.Vec;
import edu.cg.algebra.Point;

public class PointLight extends Light
{
    protected Point position;
    protected double kq;
    protected double kl;
    protected double kc;
    
    public PointLight() {
        this.kq = 0.01;
        this.kl = 0.1;
        this.kc = 1.0;
    }
    
    protected String description() {
        final String endl = System.lineSeparator();
        return "Intensity: " + this.intensity + endl + "Position: " + this.position + endl + "Decay factors: kq = " + this.kq + ", kl = " + this.kl + ", kc = " + this.kc + endl;
    }
    
    @Override
    public String toString() {
        final String endl = System.lineSeparator();
        return "Point Light:" + endl + this.description();
    }
    
    @Override
    public PointLight initIntensity(final Vec intensity) {
        return (PointLight)super.initIntensity(intensity);
    }
    
    public PointLight initPosition(final Point position) {
        this.position = position;
        return this;
    }
    
    public PointLight initDecayFactors(final double kq, final double kl, final double kc) {
        this.kq = kq;
        this.kl = kl;
        this.kc = kc;
        return this;
    }
    
    @Override
    public Ray rayToLight(final Point fromPoint) {
        return new Ray(fromPoint, this.position);
    }
    
    @Override
    public boolean isOccludedBy(final Surface surface, final Ray rayToLight) {
        final Hit hit = surface.intersect(rayToLight);
        if (hit == null) {
            return false;
        }
        final Point source = rayToLight.source();
        final Point hittingPoint = rayToLight.getHittingPoint(hit);
        return source.distSqr(this.position) > source.distSqr(hittingPoint);
    }
    
    @Override
    public Vec intensity(final Point hittingPoint, final Ray rayToLight) {
        final double dist = hittingPoint.dist(this.position);
        final double decay = this.kc + (this.kl + this.kq * dist) * dist;
        return this.intensity.mult(1.0 / decay);
    }
}
