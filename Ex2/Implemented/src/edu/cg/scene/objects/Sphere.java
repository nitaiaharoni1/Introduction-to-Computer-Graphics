package edu.cg.scene.objects;

import edu.cg.algebra.*;

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

    @Override
    public Hit intersect(Ray ray) {
        double b = ray.direction().mult(2.0).dot(ray.source().sub(this.center));
        double c = this.substitute(ray.source());
        double disc = Math.sqrt(b * b - 4.0 * c);
        boolean isInside = false;

        if (Double.isNaN(disc) == true) {
            return null;
        }

        double t1 = (-b - disc) / 2.0;
        double t2 = (-b + disc) / 2.0;

        if (t2 < Ops.epsilon) {
            return null;
        }

        Vec normal = this.getNormal(ray.add(t1));
        double minT = t1;

        if (minT > 1.0E8) {
            return null;
        }

        if (t1 < Ops.epsilon) {
            minT = t2;
            normal = this.getNormal(ray.add(t2)).neg();
            isInside = true;
        }

        return new Hit(minT, normal).setIsWithin(isInside);
    }

    private Vec getNormal(Point p) {
        Vec normal = p.sub(center).normalize();
        return normal;
    }

    private double substitute(Point p) {
        double substitute = p.distSqr(center) - Math.pow(radius, 2);
        return substitute;
    }
}