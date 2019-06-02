// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.models.Car;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import edu.cg.models.IRenderable;

public class Wheel implements IRenderable
{
    @Override
    public void render(final GL2 gl) {
        final GLU glu = new GLU();
        final GLUquadric quad = glu.gluNewQuadric();
        Materials.setMaterialTire(gl);
        gl.glPushMatrix();
        gl.glTranslated(0.0, 0.0, -0.05);
        glu.gluCylinder(quad, 0.075, 0.075, 0.1, 20, 1);
        gl.glRotated(180.0, 1.0, 0.0, 0.0);
        glu.gluDisk(quad, 0.06, 0.075, 20, 20);
        gl.glRotated(180.0, 1.0, 0.0, 0.0);
        gl.glTranslated(0.0, 0.0, 0.1);
        glu.gluDisk(quad, 0.06, 0.075, 20, 20);
        Materials.setMaterialRims(gl);
        glu.gluDisk(quad, 0.0, 0.06, 20, 20);
        gl.glTranslated(0.0, 0.0, -0.1);
        gl.glRotated(180.0, 1.0, 0.0, 0.0);
        glu.gluDisk(quad, 0.0, 0.06, 20, 20);
        gl.glPopMatrix();
        glu.gluDeleteQuadric(quad);
    }
    
    @Override
    public void init(final GL2 gl) {
    }
    
    @Override
    public String toString() {
        return "Wheel";
    }
    
    @Override
    public void destroy(final GL2 gl) {
    }
}
