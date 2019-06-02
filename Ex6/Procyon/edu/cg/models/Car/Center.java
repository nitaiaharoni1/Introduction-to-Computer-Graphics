// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.models.Car;

import com.jogamp.opengl.GL2;
import edu.cg.models.IRenderable;
import edu.cg.models.SkewedBox;

public class Center implements IRenderable
{
    private SkewedBox bodyBase;
    private SkewedBox backBox;
    private SkewedBox frontBox;
    private SkewedBox sideBox;
    
    public Center() {
        this.bodyBase = new SkewedBox(0.25, 0.013125000000000001, 0.013125000000000001, 0.4, 0.4);
        this.backBox = new SkewedBox(0.09375, 0.13125, 0.22968750000000002, 0.1, 0.1);
        this.frontBox = new SkewedBox(0.046875, 0.13125, 0.11812500000000001, 0.1, 0.4);
        this.sideBox = new SkewedBox(0.15000000000000002, 0.11812500000000001, 0.13125, 0.25, 0.15625);
    }
    
    @Override
    public void render(final GL2 gl) {
        gl.glPushMatrix();
        Materials.SetBlackMetalMaterial(gl);
        this.bodyBase.render(gl);
        Materials.SetRedMetalMaterial(gl);
        gl.glTranslated(0.1015625, 0.013125000000000001, 0.0);
        this.frontBox.render(gl);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslated(-0.1015625, 0.013125000000000001, 0.0);
        gl.glRotated(180.0, 0.0, 1.0, 0.0);
        this.frontBox.render(gl);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslated(0.0, 0.013125000000000001, 0.125);
        gl.glRotated(90.0, 0.0, 1.0, 0.0);
        this.sideBox.render(gl);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslated(0.0, 0.013125000000000001, -0.125);
        gl.glRotated(-90.0, 0.0, 1.0, 0.0);
        this.sideBox.render(gl);
        gl.glPopMatrix();
        Materials.SetBlackMetalMaterial(gl);
        gl.glPushMatrix();
        gl.glTranslated(-0.03125, 0.013125000000000001, 0.0);
        this.backBox.render(gl);
        gl.glPopMatrix();
    }
    
    @Override
    public void init(final GL2 gl) {
    }
    
    @Override
    public void destroy(final GL2 gl) {
    }
}
