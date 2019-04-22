package edu.cg.scene.camera;

import edu.cg.algebra.Ray;
import edu.cg.algebra.Vec;
import edu.cg.algebra.Point;

public class PinholeCamera {
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

    /**
     * Initializes a pinhole camera model with default resolution 200X200 (RxXRy) and image width 2.
     *
     * @param cameraPosition  - The position of the camera.
     * @param towardsVec      - The towards vector of the camera (not necessarily normalized).
     * @param upVec           - The up vector of the camera.
     * @param distanceToPlain - The distance of the camera (position) to the center point of the image-plain.
     */
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

    /**
     * Initializes the resolution and width of the image.
     *
     * @param height         - the number of pixels in the y direction.
     * @param width          - the number of pixels in the x direction.
     * @param viewPlainWidth - the width of the image plain in world coordinates.
     */
    public void initResolution(int height, int width, double viewPlainWidth) {
        //Todo: Change, notice "this."
        this.viewPlainWidth = viewPlainWidth;
        this.resX = width;
        this.resY = height;
    }

    /**
     * Transforms from pixel coordinates to the center point of the corresponding pixel in model coordinates.
     *
     * @param x - the index of the x direction of the pixel.
     * @param y - the index of the y direction of the pixel.
     * @return the middle point of the pixel (x,y) in the model coordinates.
     */
    public Point transform(int x, int y) {
        //Todo: Change, notice "this."
        double pixelHeight;
        double pixelWidth = pixelHeight = this.viewPlainWidth / this.resX;
        double upDistance = (y - (int) (this.resY / 2.0)) * pixelHeight * -1.0;
        double rightDistance = (x - (int) (this.resX / 2.0)) * pixelWidth;
        Vec upMovement = this.upVec.mult(upDistance);
        Vec rightMovement = this.rightVec.mult(rightDistance);
        Point fovPoint = this.centerPoint.add(upMovement).add(rightMovement);
        return fovPoint;
    }

    /**
     * Returns a copy of the camera position
     *
     * @return a "new" point representing the camera position.
     */
    public Point getCameraPosition() {
        //Todo: Change, notice "this."
        return new Point(this.cameraPosition.x, this.cameraPosition.y, this.cameraPosition.z);
    }
}