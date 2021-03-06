package edu.cg.scene.objects;

import edu.cg.algebra.*;


/**
 * Sphere Class which extends Shape and and defines a sphere shape
 */
public class Sphere extends Shape {
    private Point center;
    private double radius;

    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Sphere() {
        this(new Point(0, -0.5, -6), 0.5);
    }

    @Override
    public String toString() {
        String endl = System.lineSeparator();
        return "Sphere:" + endl + "Center: " + center + endl + "Radius: " + radius + endl;
    }

    public Sphere initCenter(Point center) {
        this.center = center;
        return this;
    }

    public Sphere initRadius(double radius) {
        this.radius = radius;
        return this;
    }


    /**
     * @param ray the specified ray
     * @return Hit object of the hit with the sphere
     */
    @Override
    public Hit intersect(Ray ray) {
        boolean isInside = false;
        double b = ray.direction().mult(2).dot(ray.source().sub(center));
        double c = ray.source().distSqr(center) - Math.pow(radius, 2);
        double disc = Math.sqrt(Math.pow(b, 2) - 4 * c);
        if (!(disc >= 0)) return null;
        double t1 = -(b + disc) / 2;
        double t2 = (-b + disc) / 2;
        Vec normal = ray.add(t1).sub(center).normalize();
        double minT = t1;
        if (t2 < Ops.epsilon) return null;
        if (t1 < Ops.epsilon) {
            minT = t2;
            normal = ray.add(t2).sub(center).normalize().neg();
            isInside = true;
        }
        if (minT > Integer.MAX_VALUE) return null;
        return new Hit(minT, normal).setIsWithin(isInside);
    }

}