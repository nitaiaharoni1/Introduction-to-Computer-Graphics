// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.scene.lightSources;

import edu.cg.algebra.Ray;
import edu.cg.scene.objects.Surface;
import edu.cg.algebra.Point;
import edu.cg.algebra.Vec;

public class Spotlight extends PointLight
{
    private Vec direction;
    
    public Spotlight initDirection(final Vec direction) {
        this.direction = direction;
        return this;
    }
    
    @Override
    public String toString() {
        final String endl = System.lineSeparator();
        return "Spotlight: " + endl + this.description() + "Direction: " + this.direction + endl;
    }
    
    @Override
    public Spotlight initPosition(final Point position) {
        return (Spotlight)super.initPosition(position);
    }
    
    @Override
    public Spotlight initIntensity(final Vec intensity) {
        return (Spotlight)super.initIntensity(intensity);
    }
    
    @Override
    public Spotlight initDecayFactors(final double q, final double l, final double c) {
        return (Spotlight)super.initDecayFactors(q, l, c);
    }
    
    @Override
    public boolean isOccludedBy(final Surface surface, final Ray rayToLight) {
        return rayToLight.direction().neg().dot(this.direction.normalize()) < 1.0E-5 || super.isOccludedBy(surface, rayToLight);
    }
    
    @Override
    public Vec intensity(final Point hittingPoint, final Ray rayToLight) {
        final Vec D = this.direction.normalize().neg();
        final Vec L = rayToLight.direction();
        final double cosGamma = D.dot(L);
        if (cosGamma < 1.0E-5) {
            return new Vec(0.0);
        }
        return super.intensity(hittingPoint, rayToLight).mult(cosGamma);
    }
}
