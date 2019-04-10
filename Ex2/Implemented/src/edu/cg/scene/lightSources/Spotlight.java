package edu.cg.scene.lightSources;

import edu.cg.algebra.Point;
import edu.cg.algebra.Ray;
import edu.cg.algebra.Vec;
import edu.cg.scene.objects.Surface;

public class Spotlight extends PointLight {
    private Vec direction;

    public Spotlight initDirection(Vec direction) {
        this.direction = direction;
        return this;
    }

    @Override
    public String toString() {
        String endl = System.lineSeparator();
        return "Spotlight: " + endl +
                description() +
                "Direction: " + direction + endl;
    }

    @Override
    public Spotlight initPosition(Point position) {
        return (Spotlight)super.initPosition(position);
    }

    @Override
    public Spotlight initIntensity(Vec intensity) {
        return (Spotlight)super.initIntensity(intensity);
    }

    @Override
    public Spotlight initDecayFactors(double q, double l, double c) {
        return (Spotlight)super.initDecayFactors(q, l, c);
    }

    //Todo: Change
    @Override
    public boolean isOccludedBy(Surface surface, Ray rayToLight) {
        return rayToLight.direction().neg().dot(this.direction.normalize()) < 1.0E-5 || super.isOccludedBy(surface, rayToLight);
    }

    //Todo: Change
    @Override
    public Vec intensity(Point hittingPoint, Ray rayToLight) {
        Vec D = this.direction.normalize().neg();
        Vec L = rayToLight.direction();
        double cosGamma = D.dot(L);
        if (cosGamma < 1.0E-5) {
            return new Vec(0.0);
        }
        return super.intensity(hittingPoint, rayToLight).mult(cosGamma);
    }
}