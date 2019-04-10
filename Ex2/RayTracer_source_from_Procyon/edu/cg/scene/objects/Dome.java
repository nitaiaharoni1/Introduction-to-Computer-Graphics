// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.scene.objects;

import edu.cg.algebra.Hit;
import edu.cg.algebra.Ray;
import edu.cg.algebra.Vec;
import edu.cg.algebra.Point;

public class Dome extends Shape
{
    private Sphere sphere;
    private Plain plain;
    
    public Dome() {
        this.sphere = new Sphere().initCenter(new Point(0.0, -0.5, -6.0));
        this.plain = new Plain(new Vec(-1.0, 0.0, -1.0), new Point(0.0, -0.5, -6.0));
    }
    
    @Override
    public String toString() {
        final String endl = System.lineSeparator();
        return "Dome:" + endl + this.sphere + this.plain + endl;
    }
    
    @Override
    public Hit intersect(final Ray ray) {
        final Hit hit = this.sphere.intersect(ray);
        if (hit == null) {
            return null;
        }
        return hit.isWithinTheSurface() ? this.hittingFromInside(ray, hit) : this.hittingFromOutside(ray, hit);
    }
    
    private Hit hittingFromOutside(final Ray ray, Hit hit) {
        Point hittingPoint = ray.getHittingPoint(hit);
        if (this.plain.substitute(hittingPoint) > 0.0) {
            return hit;
        }
        hit = this.plain.intersect(ray);
        if (hit == null) {
            return null;
        }
        hittingPoint = ray.getHittingPoint(hit);
        if (this.sphere.substitute(hittingPoint) < 0.0) {
            return hit;
        }
        return null;
    }
    
    private Hit hittingFromInside(final Ray ray, Hit hit) {
        final Point hittingPoint = ray.getHittingPoint(hit);
        if (this.plain.substitute(ray.source()) > 1.0E-5) {
            if (this.plain.substitute(hittingPoint) > 0.0) {
                return hit;
            }
            hit = this.plain.intersect(ray);
            if (hit == null) {
                return null;
            }
            return hit.setWithin();
        }
        else {
            if (this.plain.substitute(hittingPoint) > 0.0) {
                return this.plain.intersect(ray);
            }
            return null;
        }
    }
}
