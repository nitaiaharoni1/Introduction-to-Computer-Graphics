package edu.cg.scene.lightSources;

import edu.cg.algebra.Point;
import edu.cg.algebra.Ray;
import edu.cg.algebra.Vec;
import edu.cg.scene.objects.Surface;

public class DirectionalLight extends Light {
    private Vec direction = new Vec(0, -1, -1);

    public DirectionalLight initDirection(Vec direction) {
        this.direction = direction;
        return this;
    }

    @Override
    public String toString() {
        String endl = System.lineSeparator();
        return "Directional Light:" + endl + super.toString() +
                "Direction: " + direction + endl;
    }

    @Override
    public Ray rayToLight(Point p) {
        Ray ray = new Ray(p, direction.neg());
        return ray;
    }

    @Override
    public boolean isOccludedBy(Surface surface, Ray rayToLight) {
        Boolean isOcluded = (surface.intersect(rayToLight)!=null) ? true : false;
        return isOcluded;
    }

    @Override
    public Vec intensity(Point hittingPoint, Ray rayToLight) {
        return intensity;
    }
}