package edu.cg.models.Car;

import edu.cg.models.IRenderable;

import javax.media.opengl.GL2;

/**
 * A F1 Racing Car.
 */
public class F1Car implements IRenderable {

    @Override
    public void render(GL2 gl) {
        // TODO: Exactly the same
        // TODO: Render the whole car.
        //       Here You should only render the three parts: back, center and front.
        new Center().render(gl);
        gl.glPushMatrix();
        gl.glTranslated(-0.3875, 0, 0);
        new Back().render(gl);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslated(0.425, 0, 0);
        new Front().render(gl);
        gl.glPopMatrix();
    }

    @Override
    public String toString() {
        return "F1Car";
    }

    @Override
    public void init(GL2 gl) {

    }
}
