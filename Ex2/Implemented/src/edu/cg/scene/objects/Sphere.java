package edu.cg.scene.objects;

import edu.cg.algebra.*;

public class Sphere extends Shape
{
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
    //Todo: Change
    public Hit intersect(Ray ray) {
        double b = ray.direction().mult(2.0).dot(ray.source().sub(this.center));
        double c = this.substitute(ray.source());
        double discriminant = Math.sqrt(b * b - 4.0 * c);
        if (Double.isNaN(discriminant)) {
            return null;
        }
        double t1 = (-b - discriminant) / 2.0;
        double t2 = (-b + discriminant) / 2.0;
        if (t2 < Ops.epsilon) {
            return null;
        }
        double minT = t1;
        Vec normal = this.normal(ray.add(t1));
        boolean isWithin = false;
        if (t1 < Ops.epsilon) {
            minT = t2;
            normal = this.normal(ray.add(t2)).neg();
            isWithin = true;
        }
        if (minT > 1.0E8) {
            return null;
        }
        return new Hit(minT, normal).setIsWithin(isWithin);
    }

    //Todo: I've already changed... still need to change the name of the function. i don't know what the function do
    private Vec normal(Point p) {
        Vec normal = p.sub(center).normalize();
        return normal;
    }

    //Todo: I've already changed... still need to change the name of the function. i don't know what the function do
    private double substitute(Point p) {
        double substitute = p.distSqr(center) - Math.pow(radius,2);
        return substitute;
    }
}