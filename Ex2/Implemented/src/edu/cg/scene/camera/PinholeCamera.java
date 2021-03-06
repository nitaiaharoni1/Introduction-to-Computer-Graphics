package edu.cg.scene.camera;

import edu.cg.algebra.Ray;
import edu.cg.algebra.Vec;
import edu.cg.algebra.Point;

public class PinholeCamera {
    /**
     * Parameters for Pinhole Camera Cons
     */
    Point cameraPosition;
    Vec towardsVec;
    Vec upVec;
    Vec rightVec;
    double distanceToPlain;
    Point centerPoint;

    double height;
    double width;
    double viewPlainWidth;

    /**
     * Parameters for initResolution
     */
    double pixelsHeight;
    double pixelsWidth;
    int midPixelY;
    int midPixelX;

    /**
     * Initializes a pinhole camera model with default resolution 200X200 (RxXRy) and image width 2.
     *
     * @param cameraPosition  - The position of the camera.
     * @param towardsVec      - The towards vector of the camera (not necessarily normalized).
     * @param upVec           - The up vector of the camera.
     * @param distanceToPlain - The distance of the camera (position) to the center point of the image-plain.
     */
    public PinholeCamera(Point cameraPosition, Vec towardsVec, Vec upVec, double distanceToPlain) {
        this.cameraPosition = cameraPosition;
        this.towardsVec = towardsVec.normalize();
        this.upVec = upVec.normalize();
        this.distanceToPlain = distanceToPlain;

        this.rightVec = towardsVec.cross(upVec).normalize();
        this.centerPoint = new Ray(cameraPosition, towardsVec).add(distanceToPlain);

        this.height = 200.0;
        this.width = 200.0;
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
        this.height = height;
        this.width = width;
        this.viewPlainWidth = viewPlainWidth;

        this.pixelsWidth = viewPlainWidth / width;
        this.pixelsHeight = viewPlainWidth / height;

        this.midPixelX = width / 2;
        this.midPixelY = height / 2;
    }

    /**
     * Transforms from pixel coordinates to the center point of the corresponding pixel in model coordinates.
     *
     * @param x - the index of the x direction of the pixel.
     * @param y - the index of the y direction of the pixel.
     * @return the middle point of the pixel (x,y) in the model coordinates.
     */
    public Point transform(int x, int y) {
        double dRight = (x - midPixelX) * this.pixelsWidth;
        double dUp = (y - midPixelY) * -this.pixelsHeight;

        Vec mUp = upVec.mult(dUp);
        Vec mRight = rightVec.mult(dRight);

        Point midPoint = centerPoint.add(mUp).add(mRight);
        return midPoint;
    }

    /**
     * Returns a copy of the camera position
     *
     * @return a "new" point representing the camera position.
     */
    public Point getCameraPosition() {
        Point point = new Point(cameraPosition.x, cameraPosition.y, cameraPosition.z);
        return point;
    }
}