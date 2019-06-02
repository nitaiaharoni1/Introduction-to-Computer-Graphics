// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.models.Car;

import com.jogamp.opengl.GL2;
import edu.cg.models.IRenderable;
import edu.cg.models.SkewedBox;

public class Front implements IRenderable
{
    private SkewedBox hoodBox1;
    private SkewedBox hoodBox2;
    private SkewedBox bumperBox;
    private SkewedBox bumperWingsBox;
    private PairOfWheels wheels;
    
    public Front() {
        this.hoodBox1 = new SkewedBox(0.3125, 0.13125, 0.11249999999999999, 0.4, 0.1);
        this.hoodBox2 = new SkewedBox(0.1875, 0.11249999999999999, 0.028124999999999997, 0.1, 0.08000000000000002);
        this.bumperBox = new SkewedBox(0.1, 0.028124999999999997, 5.0E-4, 0.30000000000000004, 0.30000000000000004);
        this.bumperWingsBox = new SkewedBox(0.1, 0.065625, 5.0E-4, 0.039999999999999994, 0.039999999999999994);
        this.wheels = new PairOfWheels();
    }
    
    @Override
    public void render(final GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslated(-0.14375, 0.0, 0.0);
        gl.glPushMatrix();
        Materials.SetRedMetalMaterial(gl);
        this.hoodBox1.render(gl);
        gl.glTranslated(0.25, 0.0, 0.0);
        this.hoodBox2.render(gl);
        gl.glTranslated(0.14375, 0.0, 0.0);
        Materials.SetDarkRedMetalMaterial(gl);
        this.bumperBox.render(gl);
        Materials.SetRedMetalMaterial(gl);
        gl.glPushMatrix();
        gl.glTranslated(0.0, 0.0, 0.17);
        this.bumperWingsBox.render(gl);
        gl.glPopMatrix();
        gl.glTranslated(0.0, 0.0, -0.17);
        this.bumperWingsBox.render(gl);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslated(0.25, 0.0375, 0.0);
        this.wheels.render(gl);
        gl.glPopMatrix();
        gl.glPopMatrix();
    }
    
    @Override
    public void init(final GL2 gl) {
    }
    
    @Override
    public void destroy(final GL2 gl) {
    }
}
