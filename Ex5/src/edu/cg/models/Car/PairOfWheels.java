package edu.cg.models.Car;

import edu.cg.models.IRenderable;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

public class PairOfWheels implements IRenderable {
    //Use the wheel field to render the two wheels.
    private Wheel wheel = new Wheel();

    @Override
    public void render(GL2 gl) {
        // Render the pair of wheels.
        Materials.SetDarkGreyMetalMaterial(gl);

        gl.glPushMatrix();
        gl.glTranslated(0, 0, -Specification.PAIR_OF_WHEELS_ROD_DEPTH);

        GLU glu = new GLU();
        GLUquadric quad = glu.gluNewQuadric();
        glu.gluCylinder(quad, Specification.PAIR_OF_WHEELS_ROD_RADIUS, Specification.PAIR_OF_WHEELS_ROD_RADIUS, 2*Specification.PAIR_OF_WHEELS_ROD_DEPTH, 30, 1);

        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslated(0, 0, -Specification.PAIR_OF_WHEELS_ROD_DEPTH + Specification.TIRE_RADIUS / 2);
        wheel.render(gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glRotated(180, 1, 1, 0);
        gl.glTranslated(0, 0, -Specification.PAIR_OF_WHEELS_ROD_DEPTH + Specification.TIRE_RADIUS / 2);
        wheel.render(gl);
        gl.glPopMatrix();

    }

    @Override
    public void init(GL2 gl) {
        //Auto-generated method stub

    }

    @Override
    public String toString() {
        return "PairOfWheels";
    }

}
