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

    /**
     * Original Function JavaDoc
     * <p>
     * Checks if the given surface occludes the light-source. The surface occludes the light source
     * if the given ray first intersects the surface before reaching the light source.
     *
     * @param surface    -The given surface
     * @param rayToLight - the ray to the light source
     * @return true if the ray is occluded by the surface..
     */
    @Override
    public boolean isOccludedBy(Surface surface, Ray rayToLight) {
        Hit rayHitting = surface.intersect(rayToLight);

        if (rayHitting != null) {
            Point hittingP = rayToLight.getHittingPoint(rayHitting);
            Point point = rayToLight.source();

            double distanceToLight = point.distSqr(position);
            double distanceToHit = point.distSqr(hittingP);
            double differenceAbs = Math.abs(distanceToLight - distanceToHit);

            return (differenceAbs > Ops.epsilon);

        } else {
            return false;
        }
    }

    /**
     * Original Function JavaDoc
     * <p>
     * Returns the light intensity at the specified point.
     *
     * @param hittingPoint - The given point
     * @param rayToLight   - A ray to the light source (this is relevant for point-light and spotlight)
     * @return A vector representing the light intensity (the r,g and b channels).
     */
    @Override
    public Vec intensity(Point hittingPoint, Ray rayToLight) {
        double distance = hittingPoint.dist(position);

        double att = kq * Math.sqrt(distance) + kl * distance + kc;

        Vec lightIntensityV = intensity.mult(1 / att);
        return lightIntensityV;
    }


    /**
     * Set the decay factors as required
     *
     * @param kq
     * @param kl
     * @param kc
     * @return this - the decayFactors PointLight
     */
    public PointLight initDecayFactors(double kq, double kl, double kc) {
        this.kq = kq;
        this.kl = kl;
        this.kc = kc;
        return this;
    }

    /**
     * Init position point
     *
     * @param position
     * @return this - the position
     */
    public PointLight initPosition(Point position) {
        this.position = position;
        return this;
    }
}