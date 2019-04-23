package edu.cg.scene.lightSources;

import edu.cg.algebra.*;
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
        return (Spotlight) super.initPosition(position);
    }

    @Override
    public Spotlight initIntensity(Vec intensity) {
        return (Spotlight) super.initIntensity(intensity);
    }

    @Override
    public Spotlight initDecayFactors(double q, double l, double c) {
        return (Spotlight) super.initDecayFactors(q, l, c);
    }

    @Override
    public boolean isOccludedBy(Surface surface, Ray rayToLight) {
        return super.isOccludedBy(surface, rayToLight);
    }

    @Override
    public Vec intensity(Point hittingPoint, Ray rayToLight) {
        Vec V = rayToLight.direction();
        Vec Vd = direction.normalize().neg();
        double distance = hittingPoint.dist(position);
        double cosG = V.dot(Vd);
        double F_att = kq * Math.sqrt(distance) + kl * distance + kc;
        Vec Il = intensity.mult(cosG);
        Il = Il.mult(1 / F_att);
        return Il;
    }
}