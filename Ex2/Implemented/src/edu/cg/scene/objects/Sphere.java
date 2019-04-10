package edu.cg.scene.objects;

import edu.cg.algebra.Hit;
import edu.cg.algebra.Ray;
import edu.cg.algebra.Vec;
import edu.cg.algebra.Point;

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
        if (t2 < 1.0E-5) {
            return null;
        }
        double minT = t1;
        Vec normal = this.normal(ray.add(t1));
        boolean isWithin = false;
        if (t1 < 1.0E-5) {
            minT = t2;
            normal = this.normal(ray.add(t2)).neg();
            isWithin = true;
        }
        if (minT > 1.0E8) {
            return null;
        }
        return new Hit(minT, normal).setIsWithin(isWithin);
    }

    //Todo: Change
    private Vec normal(Point p) {
        return p.sub(this.center).normalize();
    }

    //Todo: Change
    public double substitute(Point p) {
        return p.distSqr(this.center) - this.radius * this.radius;
    }
}