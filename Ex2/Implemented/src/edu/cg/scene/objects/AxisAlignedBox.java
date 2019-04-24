package edu.cg.scene.objects;

import edu.cg.algebra.*;

public class AxisAlignedBox extends Shape {
    private Point minPoint;
    private Point maxPoint;
    private String name = "";
    static private int CURR_IDX;

    /**
     * Creates an axis aligned box with a specified minPoint and maxPoint.
     */
    public AxisAlignedBox(Point minPoint, Point maxPoint) {
        this.minPoint = minPoint;
        this.maxPoint = maxPoint;
        name = new String("Box " + CURR_IDX);
        CURR_IDX += 1;
        fixBoundryPoints();
    }

    /**
     * Creates a default axis aligned box with a specified minPoint and maxPoint.
     */
    public AxisAlignedBox() {
        minPoint = new Point(-1.0, -1.0, -1.0);
        maxPoint = new Point(1.0, 1.0, 1.0);
    }

    /**
     * This methods fixes the boundary points minPoint and maxPoint so that the values are consistent.
     */
    private void fixBoundryPoints() {
        double min_x = Math.min(minPoint.x, maxPoint.x), max_x = Math.max(minPoint.x, maxPoint.x),
                min_y = Math.min(minPoint.y, maxPoint.y), max_y = Math.max(minPoint.y, maxPoint.y),
                min_z = Math.min(minPoint.z, maxPoint.z), max_z = Math.max(minPoint.z, maxPoint.z);
        minPoint = new Point(min_x, min_y, min_z);
        maxPoint = new Point(max_x, max_y, max_z);
    }

    @Override
    public String toString() {
        String endl = System.lineSeparator();
        return name + endl + "Min Point: " + minPoint + endl + "Max Point: " + maxPoint + endl;
    }

    //Initializers
    public AxisAlignedBox initMinPoint(Point minPoint) {
        this.minPoint = minPoint;
        fixBoundryPoints();
        return this;
    }

    public AxisAlignedBox initMaxPoint(Point maxPoint) {
        this.maxPoint = maxPoint;
        fixBoundryPoints();
        return this;
    }

    @Override
    public Hit intersect(Ray ray) {

        double tNear = -1.0E8;
        double tFar = 1.0E8;

        final double[] rayPoint = ray.source().asArray();
        final double[] rayDirection = ray.direction().asArray();

        final double[] minPoint = this.minPoint.asArray();
        final double[] maxPoint = this.maxPoint.asArray();

        for (int i = 0; i < 3; ++i) {
            if (Math.abs(rayDirection[i]) <= Ops.epsilon) {
                if (rayPoint[i] > maxPoint[i] || rayPoint[i] < minPoint[i]) {
                    return null;
                }
            } else {
                double t1 = getParameters(rayDirection[i], rayPoint[i], minPoint[i]);
                double t2 = getParameters(rayDirection[i], rayPoint[i], maxPoint[i]);

                if (t1 > t2) {
                    final double tmp = t1;
                    t1 = t2;
                    t2 = tmp;
                }
                if (t1 > tNear) {
                    tNear = t1;
                }
                if (t2 < tFar) {
                    tFar = t2;
                }
                if (Double.isNaN(t1) || Double.isNaN(t2)) {
                    return null;
                }
                if (tNear > tFar || tFar < Ops.epsilon) {
                    return null;
                }
            }
        }

        double minTNear = tNear;
        boolean isInside = false;
        if (minTNear < Ops.epsilon) {
            isInside = true;
            minTNear = tFar;
        }

        Vec norm = this.normalize(ray.add(minTNear));
        if (isInside) {
            norm = norm.neg();
        }
        return new Hit(minTNear, norm).setIsWithin(isInside);
    }


    private Vec normalize(final Point p) {
        if (Math.abs(p.z - this.minPoint.z) <= Ops.epsilon) {
            return new Vec(0.0, 0.0, -1.0);
        }
        if (Math.abs(p.x - this.minPoint.x) <= Ops.epsilon) {
            return new Vec(-1.0, 0.0, 0.0);
        }
        if (Math.abs(p.y - this.minPoint.y) <= Ops.epsilon) {
            return new Vec(0.0, -1.0, 0.0);
        }
        if (Math.abs(p.y - this.maxPoint.y) <= Ops.epsilon) {
            return new Vec(0.0, 1.0, 0.0);
        }
        if (Math.abs(p.z - this.maxPoint.z) <= Ops.epsilon) {
            return new Vec(0.0, 0.0, 1.0);
        }
        if (Math.abs(p.x - this.maxPoint.x) <= Ops.epsilon) {
            return new Vec(1.0, 0.0, 0.0);
        }

        // if all failed
        return null;
    }

    private static double getParameters(final double a, final double b, final double c) {
        final double t = (c - b) / a;

        if (Math.abs(a) < Ops.epsilon && Math.abs(b - c) < Ops.epsilon) {
            return 0.0;
        }
        if (Math.abs(a) < Ops.epsilon && Math.abs(b - c) > Ops.epsilon) {
            return 1.0E8;
        }

        return t;
    }
}