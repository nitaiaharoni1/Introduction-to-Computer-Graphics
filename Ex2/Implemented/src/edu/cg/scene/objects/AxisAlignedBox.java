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
        final Point rayPoint = ray.source();
        final Vec rayDirection = ray.direction();

        final double[] rayPArr = rayPoint.asArray();
        final double[] rayDArr = rayDirection.asArray();

        final double[] minPoint = this.minPoint.asArray();
        final double[] maxPoint = this.maxPoint.asArray();

        boolean isInside = false;
        double near = -1.0E8;
        double far = 1.0E8;

        for (int i = 0; i <= 2; ++i) {
            boolean absVal = Math.abs(rayDArr[i]) > Ops.epsilon;
            if (absVal == true) {
                double t1 = getParameters(rayDArr[i], rayPArr[i], minPoint[i]);
                double t2 = getParameters(rayDArr[i], rayPArr[i], maxPoint[i]);
                final double tempParam = t1;
                boolean areNan = (Double.isNaN(t1) == true || Double.isNaN(t2) == true);

                if (areNan) {
                    return null;
                }
                if (near > far || far < Ops.epsilon) {
                    return null;
                }
                if (t1 > t2) {
                    t1 = t2;
                    t2 = tempParam;
                }
                if (t1 > near) {
                    near = t1;
                }
                if (t2 < far) {
                    far = t2;
                }
            } else {
                boolean inRange = rayPArr[i] > maxPoint[i] || rayPArr[i] < minPoint[i];
                if (inRange) {
                    return null;
                }
            }
        }

        double minimalNear = near;

        if (minimalNear < Ops.epsilon) {
            isInside = true;
            minimalNear = far;
        }

        Vec norm = this.normalize(ray.add(minimalNear));
        if (isInside == true) {
            norm = norm.neg();
        }

        Hit hitToReturn = new Hit(minimalNear, norm).setIsWithin(isInside);
        return hitToReturn;
    }


    private Vec normalize(final Point point) {
        if (Math.abs(point.z - this.minPoint.z) <= Ops.epsilon) {
            Vec toReturn = new Vec(0.0, 0.0, -1.0);
            return toReturn;
        }
        if (Math.abs(point.x - this.minPoint.x) <= Ops.epsilon) {
            Vec toReturn = new Vec(-1.0, 0.0, 0.0);
            return toReturn;
        }
        if (Math.abs(point.y - this.minPoint.y) <= Ops.epsilon) {
            Vec toReturn = new Vec(0.0, -1.0, 0.0);
            return toReturn;
        }
        if (Math.abs(point.y - this.maxPoint.y) <= Ops.epsilon) {
            Vec toReturn = new Vec(0.0, 1.0, 0.0);
            return toReturn;
        }
        if (Math.abs(point.z - this.maxPoint.z) <= Ops.epsilon) {
            Vec toReturn = new Vec(0.0, 0.0, 1.0);
            return toReturn;
        }
        if (Math.abs(point.x - this.maxPoint.x) <= Ops.epsilon) {
            Vec toReturn = new Vec(1.0, 0.0, 0.0);
            return toReturn;
        }

        // if all failed
        return null;
    }

    private static double getParameters(final double a, final double b, final double c) {
        final double t = (c - b) / a;
        double zero = 0.0;
        double huge = 1.0E8;


        if (Math.abs(a) < Ops.epsilon && Math.abs(b - c) < Ops.epsilon) {
            return zero;
        }
        if (Math.abs(a) < Ops.epsilon && Math.abs(b - c) > Ops.epsilon) {
            return huge;
        }

        return t;
    }
}