// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.algebra;

public class Ops
{
    public static final double epsilon = 1.0E-5;
    public static final double infinity = 1.0E8;
    
    public static double dot(final Vec u, final Vec v) {
        return u.x * v.x + u.y * v.y + u.z * v.z;
    }
    
    public static Vec cross(final Vec u, final Vec v) {
        return new Vec(u.y * v.z - u.z * v.y, u.z * v.x - u.x * v.z, u.x * v.y - u.y * v.x);
    }
    
    public static Vec mult(final double a, final Vec v) {
        return mult(new Vec(a), v);
    }
    
    public static Vec mult(final Vec u, final Vec v) {
        return new Vec(u.x * v.x, u.y * v.y, u.z * v.z);
    }
    
    public static Point mult(final double a, final Point p) {
        return mult(new Point(a), p);
    }
    
    public static Point mult(final Point p1, final Point p2) {
        return new Point(p1.x * p2.x, p1.y * p2.y, p1.z * p2.z);
    }
    
    public static double normSqr(final Vec v) {
        return dot(v, v);
    }
    
    public static double norm(final Vec v) {
        return Math.sqrt(normSqr(v));
    }
    
    public static double lengthSqr(final Vec v) {
        return normSqr(v);
    }
    
    public static double length(final Vec v) {
        return norm(v);
    }
    
    public static double dist(final Point p1, final Point p2) {
        return length(sub(p1, p2));
    }
    
    public static double distSqr(final Point p1, final Point p2) {
        return lengthSqr(sub(p1, p2));
    }
    
    public static Vec normalize(final Vec v) {
        return mult(1.0 / norm(v), v);
    }
    
    public static Vec neg(final Vec v) {
        return mult(-1.0, v);
    }
    
    public static Vec add(final Vec u, final Vec v) {
        return new Vec(u.x + v.x, u.y + v.y, u.z + v.z);
    }
    
    public static Point add(final Point p, final Vec v) {
        return new Point(p.x + v.x, p.y + v.y, p.z + v.z);
    }
    
    public static Point add(final Point p1, final Point p2) {
        return new Point(p1.x + p2.x, p1.y + p2.y, p1.z + p2.z);
    }
    
    public static Point add(final Point p, final double t, final Vec v) {
        return add(p, mult(t, v));
    }
    
    public static Vec sub(final Point p1, final Point p2) {
        return new Vec(p1.x - p2.x, p1.y - p2.y, p1.z - p2.z);
    }
    
    public static boolean isFinite(final Vec v) {
        return Double.isFinite(v.x) & Double.isFinite(v.y) & Double.isFinite(v.z);
    }
    
    public static boolean isFinite(final Point p) {
        return Double.isFinite(p.x) & Double.isFinite(p.y) & Double.isFinite(p.z);
    }
    
    public static Vec reflect(final Vec u, final Vec normal) {
        return add(u, mult(-2.0 * dot(u, normal), normal));
    }
    
    public static Vec refract(final Vec u, final Vec normal, final double n1, final double n2) {
        if (n1 == n2) {
            return u;
        }
        double dot = dot(neg(u), normal);
        dot *= dot;
        if (n1 > n2) {
            double criticalAngle = n2 / n1;
            criticalAngle *= criticalAngle;
            if (1.0 - dot >= criticalAngle) {
                return reflect(u, normal);
            }
        }
        Vec b = add(u, mult(dot(neg(u), normal), normal));
        b = normalize(b);
        final double sin2Theta2 = n1 * n1 * (1.0 - dot) / (n2 * n2);
        final double cos2Theta2 = 1.0 - sin2Theta2;
        final double sinTheta2 = Math.sqrt(sin2Theta2);
        final double cosTheta2 = Math.sqrt(cos2Theta2);
        return add(mult(-cosTheta2, normal), mult(sinTheta2, b));
    }
}
