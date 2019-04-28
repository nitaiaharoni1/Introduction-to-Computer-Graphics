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
        minPoint = new Point(-1, -1, -1);
        maxPoint = new Point(1, 1, 1);
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
        double minTClose;
        Point raySource = ray.source();
        Vec rayDirection = ray.direction();
        double[] minPointArray = minPoint.asArray();
        double[] maxPointArray = maxPoint.asArray();
        double[] rayDirectionArray = rayDirection.asArray();
        double[] raySourceArray = raySource.asArray();
        double tClose = Integer.MIN_VALUE;
        double tFar = Integer.MAX_VALUE;
        boolean isInside = false;

        for (int i = 0; i <= 2; i++) {
            if (Math.abs(rayDirectionArray[i]) > Ops.epsilon) {
                double tMin = getT(raySourceArray[i], rayDirectionArray[i], minPointArray[i]);
                double tMax = getT(raySourceArray[i], rayDirectionArray[i], maxPointArray[i]);
                double tempParam = tMin;
                if (tMin > tMax) {
                    tMin = tMax;
                    tMax = tempParam;
                }
                if (tMin > tClose) tClose = tMin;
                if (tMax < tFar) tFar = tMax;
                if (tClose > tFar || tFar < Ops.epsilon) return null;
            } else if (raySourceArray[i] > maxPointArray[i] || raySourceArray[i] < minPointArray[i]) return null;
        }
        minTClose = tClose;
        if (minTClose < Ops.epsilon) {
            isInside = true;
            minTClose = tFar;
        }
        Vec norm = normal(ray.add(minTClose));
        if (isInside) if (norm != null) norm = norm.neg();
        return new Hit(minTClose, norm).setIsWithin(isInside);
    }


    private static double getT(double raySource, double rayDirection, double minPoint) {
        if (Math.abs(rayDirection) < Ops.epsilon && Math.abs(raySource - minPoint) > Ops.epsilon) return Integer.MAX_VALUE;
        if (Math.abs(rayDirection) < Ops.epsilon && Math.abs(raySource - minPoint) < Ops.epsilon) return 0;
        return (minPoint - raySource) / rayDirection;
    }

    private Vec normal(Point hitPoint) {
        double xMinVal = Math.abs(hitPoint.x - minPoint.x);
        double yMinVal = Math.abs(hitPoint.y - minPoint.y);
        double zMinVal = Math.abs(hitPoint.z - minPoint.z);

        double xMaxVal = Math.abs(hitPoint.x - maxPoint.x);
        double yMaxVal = Math.abs(hitPoint.y - maxPoint.y);
        double zMaxVal = Math.abs(hitPoint.z - maxPoint.z);

        if (xMinVal < Ops.epsilon) return new Vec(-1, 0, 0);
        else if (yMinVal < Ops.epsilon) return new Vec(0, -1, 0);
        else if (zMinVal < Ops.epsilon) return new Vec(0, 0, -1);
        else if (xMaxVal < Ops.epsilon) return new Vec(1, 0, 0);
        else if (yMaxVal < Ops.epsilon) return new Vec(0, 1, 0);
        else if (zMaxVal < Ops.epsilon) return new Vec(0, 0, 1);
        else return null;
    }

}