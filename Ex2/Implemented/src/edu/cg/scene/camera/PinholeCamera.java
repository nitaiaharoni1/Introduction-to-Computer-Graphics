package edu.cg.scene.camera;

import edu.cg.algebra.Ray;
import edu.cg.algebra.Vec;
import edu.cg.algebra.Point;

public class PinholeCamera {
    transient Point cameraPosition;
    transient Vec towardsVec;
    transient Vec upVec;
    transient double distanceToPlain;

    transient Point centerPoint;
    transient Vec rightVec;
    transient double height;
    transient double width;
    transient double viewPlainWidth;

    /**
     * Initializes a pinhole camera model with default resolution 200X200 (RxXRy) and image width 2.
     *
     * @param cameraPosition  - The position of the camera.
     * @param towardsVec      - The towards vector of the camera (not necessarily normalized).
     * @param upVec           - The up vector of the camera.
     * @param distanceToPlain - The distance of the camera (position) to the center point of the image-plain.
     */
    public PinholeCamera(Point cameraPosition, Vec towardsVec, Vec upVec, double distanceToPlain) {
        this.width = 200.0;
        this.height = 200.0;
        this.viewPlainWidth = 2.0;
        this.cameraPosition = cameraPosition;
        this.towardsVec = towardsVec.normalize();
        this.upVec = upVec.normalize();
        this.distanceToPlain = distanceToPlain;
        this.rightVec = towardsVec.cross(upVec).normalize();
        this.centerPoint = new Ray(cameraPosition, towardsVec).add(distanceToPlain);
    }

    /**
     * Initializes the resolution and width of the image.
     *
     * @param height         - the number of pixels in the y direction.
     * @param width          - the number of pixels in the x direction.
     * @param viewPlainWidth - the width of the image plain in world coordinates.
     */
    public void initResolution(int height, int width, double viewPlainWidth) {
        this.height = height;
        this.width = width;
        this.viewPlainWidth = viewPlainWidth;
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
        double pixelWidth = pixelHeight = viewPlainWidth / width;
        double upDistance = (y - (int) (height / 2.0)) * pixelHeight * -1.0;
        double rightDistance = (x - (int) (width / 2.0)) * pixelWidth;
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
        Point p = new Point(cameraPosition.x, cameraPosition.y, cameraPosition.z);
        return p;
    }
}