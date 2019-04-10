// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.scene.objects;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import edu.cg.algebra.Hit;
import edu.cg.algebra.Ray;
import java.util.function.BiConsumer;
import edu.cg.algebra.Vec;
import edu.cg.algebra.Point;

public class Triangle extends Shape
{
    private Point p1;
    private Point p2;
    private Point p3;
    private transient Plain plain;
    
    public Triangle() {
        this.plain = null;
        final Point p1 = null;
        this.p3 = p1;
        this.p2 = p1;
        this.p1 = p1;
    }
    
    public Triangle(final Point p1, final Point p2, final Point p3) {
        this.plain = null;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }
    
    @Override
    public String toString() {
        final String endl = System.lineSeparator();
        return "Triangle:" + endl + "p1: " + this.p1 + endl + "p2: " + this.p2 + endl + "p3: " + this.p3 + endl;
    }
    
    private synchronized Plain plain() {
        if (this.plain == null) {
            final Vec v2 = this.p2.sub(this.p1);
            final Vec v3 = this.p3.sub(this.p1);
            final Vec nomal = v2.cross(v3).normalize();
            this.plain = new Plain(nomal, this.p1);
        }
        return this.plain;
    }
    
    private Vec normal() {
        return this.plain().normal();
    }
    
    private void forEach(final BiConsumer<Point, Point> foo) {
        foo.accept(this.p1, this.p2);
        foo.accept(this.p2, this.p3);
        foo.accept(this.p3, this.p1);
    }
    
    private boolean isInside(final Ray ray, final Hit hit) {
        final List<Triangle> triangles = new LinkedList<Triangle>();
        this.forEach((pi, pj) -> triangles.add(new Triangle(ray.source(), pi, pj)));
        final Point hittingPoint = ray.getHittingPoint(hit);
        final Ray rayToHittingPoint = new Ray(ray.source(), hittingPoint);
        final boolean[] signs = new boolean[3];
        int i = 0;
        for (final Triangle triangle : triangles) {
            signs[i++] = (triangle.normal().dot(rayToHittingPoint.direction()) > 0.0);
        }
        boolean allTrue = true;
        boolean allFalse = true;
        boolean[] array;
        for (int length = (array = signs).length, j = 0; j < length; ++j) {
            final boolean sign = array[j];
            allTrue &= sign;
            allFalse &= !sign;
        }
        return allTrue | allFalse;
    }
    
    @Override
    public Hit intersect(final Ray ray) {
        final Hit hit = this.plain().intersect(ray);
        if (hit != null && this.isInside(ray, hit)) {
            return hit;
        }
        return null;
    }
}
