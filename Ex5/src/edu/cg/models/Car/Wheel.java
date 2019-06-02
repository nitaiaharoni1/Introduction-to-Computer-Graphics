package edu.cg.models.Car;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

import edu.cg.models.IRenderable;

public class Wheel implements IRenderable {

    @Override
    public void render(GL2 gl) {
        // Render the wheel.
        // The wheel should be in the center relative to its local coordinate system.
        gl.glPushMatrix();

        GLU glu = new GLU();
        GLUquadric quad = glu.gluNewQuadric();

        Materials.setMaterialTire(gl);
        gl.glTranslated(0, 0, -Specification.TIRE_DEPTH/2);
        glu.gluCylinder(quad, Specification.TIRE_RADIUS, Specification.TIRE_RADIUS, Specification.TIRE_DEPTH, 30, 1);

        gl.glPushMatrix();
        gl.glRotated(180, 1, 0, 0);
        glu.gluDisk(quad, 0, Specification.TIRE_RADIUS, 30, 1);
        Materials.setMaterialRims(gl);
        gl.glTranslated(0, 0, Specification.PAIR_OF_WHEELS_ROD_RADIUS / 2);
        glu.gluDisk(quad, 0, Specification.S_ROD_HIEGHT, 30, 1);
        gl.glPopMatrix();

        gl.glPushMatrix();
        Materials.setMaterialTire(gl);
        gl.glTranslated(0, 0, Specification.TIRE_DEPTH);
        glu.gluDisk(quad, 0, Specification.TIRE_RADIUS, 30, 1);
        Materials.setMaterialRims(gl);
        gl.glTranslated(0, 0, Specification.PAIR_OF_WHEELS_ROD_RADIUS / 2);
        glu.gluDisk(quad, 0, Specification.S_ROD_HIEGHT, 30, 1);
        gl.glPopMatrix();

        gl.glPopMatrix();
    }

    @Override
    public void init(GL2 gl) {
        //Auto-generated method stub

    }

    @Override
    public String toString() {
        return "Wheel";
    }

}
