package edu.cg.models.Car;

import edu.cg.models.IRenderable;
import edu.cg.models.SkewedBox;

import javax.media.opengl.GL2;

public class Center implements IRenderable {
    // The following elements are used to build the center of the body.
    // bodyBase is the black base of the center.
    private SkewedBox bodyBase = new SkewedBox(Specification.C_BASE_LENGTH, Specification.C_BASE_HEIGHT, Specification.C_BASE_HEIGHT, Specification.C_BASE_DEPTH, Specification.C_BASE_DEPTH);
    // backSeatBox is the back seat of the center.
    private SkewedBox backSeatBox = new SkewedBox(Specification.C_BACK_LENGTH, Specification.C_BACK_HEIGHT_1, Specification.C_BACK_HEIGHT_2, Specification.C_BACK_DEPTH, Specification.C_BACK_DEPTH);
    // frontBox is used to render both the 'front' and 'back' parts of the centers
    // Look at the provided example with wireframe mode to better visualize where these elements should be placed.
    private SkewedBox frontBox = new SkewedBox(Specification.C_FRONT_LENGTH, Specification.C_FRONT_HEIGHT_1, Specification.C_FRONT_HEIGHT_2, Specification.C_FRONT_DEPTH_1, Specification.C_FRONT_DEPTH_2);
    // sideBox is used to render both the right-side and left-side of the center of the body.
    private SkewedBox sideBox = new SkewedBox(Specification.C_SIDE_LENGTH, Specification.C_SIDE_HEIGHT_1, Specification.C_SIDE_HEIGHT_2, Specification.C_SIDE_DEPTH_1, Specification.C_SIDE_DEPTH_2);

    @Override
    public void render(GL2 gl) {
        //Render the center of the car.
        Materials.SetBlackMetalMaterial(gl);
        gl.glPushMatrix();
        bodyBase.render(gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslated(0, Specification.C_BASE_HEIGHT, 0);

        gl.glPushMatrix();
        gl.glRotated(180, 0, 1, 0);
        backSeatBox.render(gl);
        gl.glPopMatrix();

        Materials.SetRedMetalMaterial(gl);
        gl.glPushMatrix();
        gl.glTranslated(Specification.C_SIDE_LENGTH - Specification.C_FRONT_LENGTH, 0, 0);
        frontBox.render(gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslated(-Specification.C_SIDE_LENGTH + Specification.C_FRONT_LENGTH, 0, 0);
        gl.glRotated(180, 0, 1, 0);
        frontBox.render(gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslated(0, 0, -Specification.S_RODS_SIZE);
        gl.glRotated(-90, 0, 1, 0);
        sideBox.render(gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslated(0, 0, Specification.S_RODS_SIZE);
        gl.glRotated(90, 0, 1, 0);
        sideBox.render(gl);
        gl.glPopMatrix();

        gl.glPopMatrix();
    }

    @Override
    public void init(GL2 gl) {
    }
}
