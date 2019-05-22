package edu.cg.models.Car;

import edu.cg.models.IRenderable;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

public class PairOfWheels implements IRenderable {
    // TODO: Use the wheel field to render the two wheels.
    private Wheel wheel = new Wheel();

    @Override
    public void render(GL2 gl) {
        // TODO: Exactly the same
        // TODO: Render the pair of wheels.
        gl.glPushMatrix();
        gl.glTranslated(0, 0, -0.1);

        GLU glu = new GLU();
        GLUquadric quad = glu.gluNewQuadric();
        glu.gluCylinder(quad, 0.01, 0.01, 0.2, 20, 1);

        Materials.SetDarkGreyMetalMaterial(gl);
        gl.glTranslated(0, 0, 0.23750000000000002);
        wheel.render(gl);

        gl.glTranslated(0, 0, -0.275);
        gl.glRotated(180, 0, 1, 0.0);
        wheel.render(gl);

        gl.glPopMatrix();
        glu.gluDeleteQuadric(quad);
    }

    @Override
    public void init(GL2 gl) {
        // TODO Auto-generated method stub

    }

    @Override
    public String toString() {
        return "PairOfWheels";
    }

}
