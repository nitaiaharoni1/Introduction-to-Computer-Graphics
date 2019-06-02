// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.models.Car;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import edu.cg.models.IRenderable;

public class PairOfWheels implements IRenderable
{
    private final Wheel wheel;
    
    public PairOfWheels() {
        this.wheel = new Wheel();
    }
    
    @Override
    public void render(final GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslated(0.0, 0.0, -0.1);
        final GLU glu = new GLU();
        Materials.SetDarkGreyMetalMaterial(gl);
        final GLUquadric quad = glu.gluNewQuadric();
        glu.gluCylinder(quad, 0.01, 0.01, 0.2, 20, 1);
        gl.glTranslated(0.0, 0.0, 0.23750000000000002);
        this.wheel.render(gl);
        gl.glTranslated(0.0, 0.0, -0.275);
        gl.glRotated(180.0, 0.0, 1.0, 0.0);
        this.wheel.render(gl);
        gl.glPopMatrix();
        glu.gluDeleteQuadric(quad);
    }
    
    @Override
    public void init(final GL2 gl) {
    }
    
    @Override
    public String toString() {
        return "PairOfWheels";
    }
    
    @Override
    public void destroy(final GL2 gl) {
    }
}
