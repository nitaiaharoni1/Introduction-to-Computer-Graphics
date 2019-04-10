package edu.cg.scene.camera;

import edu.cg.algebra.Ray;
import edu.cg.algebra.Vec;
import edu.cg.algebra.Point;

public class PinholeCamera{
    //Todo: Change
    Point cameraPosition;
    Point centerPoint;
    Vec towardsVec;
    Vec upVec;
    Vec rightVec;
    double viewPlainWidth;
    double distanceToPlain;
    double resX;
    double resY;
    double pixelRatio;

    public PinholeCamera(Point cameraPosition, Vec towardsVec, Vec upVec, double distanceToPlain) {
        //Todo: Change, notice "this."
        this.cameraPosition = cameraPosition;
        this.towardsVec = towardsVec.normalize();
        this.rightVec = this.towardsVec.cross(upVec).normalize();
        this.upVec = this.rightVec.cross(this.towardsVec).normalize();
        this.distanceToPlain = distanceToPlain;
        this.centerPoint = new Ray(cameraPosition, towardsVec).add(distanceToPlain);
        this.resX = 200.0;
        this.resY = 200.0;
        this.viewPlainWidth = 2.0;
    }

    public void initResolution(int height, int width, double viewPlainWidth) {
        //Todo: Change, notice "this."
        this.viewPlainWidth = viewPlainWidth;
        this.resX = width;
        this.resY = height;
    }

    public Point transform(int x, int y) {
        //Todo: Change, notice "this."
        double pixelHeight;
        double pixelWidth = pixelHeight = this.viewPlainWidth / this.resX;
        double upDistance = (y - (int)(this.resY / 2.0)) * pixelHeight * -1.0;
        double rightDistance = (x - (int)(this.resX / 2.0)) * pixelWidth;
        Vec upMovement = this.upVec.mult(upDistance);
        Vec rightMovement = this.rightVec.mult(rightDistance);
        Point fovPoint = this.centerPoint.add(upMovement).add(rightMovement);
        return fovPoint;
    }

    public Point getCameraPosition() {
        //Todo: Change, notice "this."
        return new Point(this.cameraPosition.x, this.cameraPosition.y, this.cameraPosition.z);
    }
}