package edu.cg.scene.lightSources;

import edu.cg.algebra.*;
import edu.cg.scene.objects.Surface;

public class PointLight extends Light {
    protected Point position;

    //Decay factors:
    protected double kq = 0.01;
    protected double kl = 0.1;
    protected double kc = 1;

    protected String description() {
        String endl = System.lineSeparator();
        return "Intensity: " + intensity + endl +
                "Position: " + position + endl +
                "Decay factors: kq = " + kq + ", kl = " + kl + ", kc = " + kc + endl;
    }

    @Override
    public String toString() {
        String endl = System.lineSeparator();
        return "Point Light:" + endl + description();
    }

    @Override
    public PointLight initIntensity(Vec intensity) {
        return (PointLight) super.initIntensity(intensity);
    }

    @Override
    public Ray rayToLight(Point fromPoint) {
        Ray ray = new Ray(fromPoint, position);
        return ray;
    }

    @Override
    public boolean isOccludedBy(Surface surface, Ray rayToLight) {
        Hit hit = surface.intersect(rayToLight);
        if (hit != null) {
            Point hitP = rayToLight.getHittingPoint(hit);
            Point p = rayToLight.source();
            double dToLight = p.distSqr(position);
            double dToHit = p.distSqr(hitP);
            double delta = Math.abs(dToLight-dToHit);
            if (delta > Ops.epsilon) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public Vec intensity(Point hittingPoint, Ray rayToLight) {
        double distance = hittingPoint.dist(position);
        double F_att = kq * Math.sqrt(distance) + kl * distance + kc;
        Vec Il = intensity.mult(1 / F_att);
        return Il;
    }

    public PointLight initPosition(Point position) {
        this.position = position;
        return this;
    }

    public PointLight initDecayFactors(double kq, double kl, double kc) {
        this.kq = kq;
        this.kl = kl;
        this.kc = kc;
        return this;
    }
}