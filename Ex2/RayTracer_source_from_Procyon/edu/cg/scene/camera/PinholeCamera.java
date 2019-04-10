// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.scene.camera;

import edu.cg.algebra.Ray;
import edu.cg.algebra.Vec;
import edu.cg.algebra.Point;

public class PinholeCamera
{
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
    
    public PinholeCamera(final Point cameraPosition, final Vec towardsVec, final Vec upVec, final double distanceToPlain) {
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
    
    public void initResolution(final int height, final int width, final double viewPlainWidth) {
        this.viewPlainWidth = viewPlainWidth;
        this.resX = width;
        this.resY = height;
    }
    
    public Point transform(final int x, final int y) {
        final double pixelHeight;
        final double pixelWidth = pixelHeight = this.viewPlainWidth / this.resX;
        final double upDistance = (y - (int)(this.resY / 2.0)) * pixelHeight * -1.0;
        final double rightDistance = (x - (int)(this.resX / 2.0)) * pixelWidth;
        final Vec upMovement = this.upVec.mult(upDistance);
        final Vec rightMovement = this.rightVec.mult(rightDistance);
        final Point fovPoint = this.centerPoint.add(upMovement).add(rightMovement);
        return fovPoint;
    }
    
    public Point getCameraPosition() {
        return new Point(this.cameraPosition.x, this.cameraPosition.y, this.cameraPosition.z);
    }
}
