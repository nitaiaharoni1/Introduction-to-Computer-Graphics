package edu.cg.models.Car;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import edu.cg.models.IRenderable;

public class Wheel implements IRenderable {

    @Override
    public void render(GL2 gl) {
        // TODO: Render the wheel.
        // The wheel should be in the center relative to its local coordinate system.
        GLU glu = new GLU();
        GLUquadric quad = glu.gluNewQuadric();
        Materials.setMaterialTire(gl);
        gl.glPushMatrix();
        gl.glTranslated(0, 0, -0.05);
        glu.gluCylinder(quad, 0.075, 0.075, 0.1, 20, 1);
        gl.glRotated(180, 1, 0, 0.0);
        glu.gluDisk(quad, 0.06, 0.075, 20, 1);
        gl.glRotated(180, 1, 0, 0.0);
        gl.glTranslated(0, 0, 0.1);
        glu.gluDisk(quad, 0.06, 0.075, 20, 1);
        Materials.setMaterialRims(gl);
        glu.gluDisk(quad, 0, 0.06, 20, 1);
        gl.glTranslated(0, 0, -0.1);
        gl.glRotated(180, 1, 0, 0.0);
        glu.gluDisk(quad, 0, 0.06, 20, 1);
        gl.glPopMatrix();
        glu.gluDeleteQuadric(quad);
    }

    @Override
    public void init(GL2 gl) {
        // TODO Auto-generated method stub

    }

    @Override
    public String toString() {
        return "Wheel";
    }

}
