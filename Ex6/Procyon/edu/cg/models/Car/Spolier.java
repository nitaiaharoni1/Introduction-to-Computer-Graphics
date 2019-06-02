// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.models.Car;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import edu.cg.models.IRenderable;
import edu.cg.models.SkewedBox;

public class Spolier implements IRenderable
{
    private static final SkewedBox spoilerBase;
    private static final SkewedBox spoilerWings;
    
    static {
        spoilerBase = new SkewedBox(0.15, 0.015, 0.015, 0.45, 0.45);
        spoilerWings = new SkewedBox(0.15, 0.15, 0.06, 0.024999999999999994, 0.024999999999999994);
    }
    
    @Override
    public void render(final GL2 gl) {
        Materials.SetDarkGreyMetalMaterial(gl);
        final GLU glu = new GLU();
        final GLUquadric quad = glu.gluNewQuadric();
        gl.glPushMatrix();
        gl.glTranslated(0.0, 0.0, 0.04375);
        gl.glRotated(-90.0, 1.0, 0.0, 0.0);
        glu.gluCylinder(quad, 0.01875, 0.01875, 0.05, 20, 20);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslated(0.0, 0.0, -0.04375);
        gl.glRotated(-90.0, 1.0, 0.0, 0.0);
        glu.gluCylinder(quad, 0.01875, 0.01875, 0.05, 20, 20);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslated(0.0, 0.05, 0.0);
        Materials.SetDarkRedMetalMaterial(gl);
        Spolier.spoilerBase.render(gl);
        gl.glPushMatrix();
        gl.glTranslated(0.0, 0.045, 0.0);
        Spolier.spoilerBase.render(gl);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslated(0.0, 0.0, 0.2375);
        Materials.SetRedMetalMaterial(gl);
        Spolier.spoilerWings.render(gl);
        gl.glPopMatrix();
        gl.glTranslated(0.0, 0.0, -0.2375);
        Spolier.spoilerWings.render(gl);
        gl.glPopMatrix();
        glu.gluDeleteQuadric(quad);
    }
    
    @Override
    public void init(final GL2 gl) {
    }
    
    @Override
    public String toString() {
        return "Spoiler";
    }
    
    @Override
    public void destroy(final GL2 gl) {
    }
}
