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
    transient int height; //Todo: might need to be double
    transient int width; //Todo: might need to be double
    transient double viewPlainWidth; //Todo: might be int

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

        this.height = 200;
        this.width = 200;
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
    }

    /**
     * Transforms from pixel coordinates to the center point of the corresponding pixel in model coordinates.
     *
     * @param x - the index of the x direction of the pixel.
     * @param y - the index of the y direction of the pixel.
     * @return the middle point of the pixel (x,y) in the model coordinates.
     */
    public Point transform(int x, int y) {
        double pixelsHeight = viewPlainWidth / height; //Todo: might need to change back to pixelsAngleHeight=pixelsAngleWidth
        double pixelsWidth = viewPlainWidth / width;
        double dUp = -pixelsHeight * (y - height / 2);
        double dRight = pixelsWidth * (x - width / 2);
        Vec mUp = upVec.mult(dUp);
        Vec mRight = rightVec.mult(dRight);
        Point middlePoint = centerPoint.add(mRight).add(mUp);
        return middlePoint;
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