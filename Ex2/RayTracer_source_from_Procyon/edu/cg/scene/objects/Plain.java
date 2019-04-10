// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.scene.objects;

import edu.cg.algebra.Hit;
import edu.cg.algebra.Ray;
import edu.cg.algebra.Point;
import edu.cg.algebra.Vec;

public class Plain extends Shape
{
    private double a;
    private double b;
    private double c;
    private double d;
    private transient Vec normal;
    
    public Plain(final double a, final double b, final double c, final double d) {
        this.normal = null;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }
    
    public Plain(final Vec normal, final Point p0) {
        this(normal.x, normal.y, normal.z, -normal.dot(p0.toVec()));
    }
    
    public Plain() {
        this(new Vec(0.0, 1.0, 0.0), new Point(0.0, -1.0, 0.0));
    }
    
    @Override
    public String toString() {
        final String endl = System.lineSeparator();
        return "Plain: a = " + this.a + ", b = " + this.b + ", c = " + this.c + ", d = " + this.d + endl;
    }
    
    public synchronized Vec normal() {
        if (this.normal == null) {
            this.normal = new Vec(this.a, this.b, this.c).normalize();
        }
        return this.normal;
    }
    
    public Vec normal(final Ray ray) {
        return (ray.direction().dot(this.normal()) < 0.0) ? this.normal() : this.normal().neg();
    }
    
    public double substitute(final Point p) {
        final Vec abc = new Vec(this.a, this.b, this.c);
        return abc.dot(p.toVec()) + this.d;
    }
    
    @Override
    public Hit intersect(final Ray ray) {
        final Vec abc = new Vec(this.a, this.b, this.c);
        final double t = -this.substitute(ray.source()) / ray.direction().dot(abc);
        return (t > 1.0E-5 & t < 1.0E8) ? new Hit(t, this.normal(ray)) : null;
    }
}
