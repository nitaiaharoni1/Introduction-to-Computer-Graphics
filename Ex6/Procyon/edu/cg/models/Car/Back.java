// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.models.Car;

import com.jogamp.opengl.GL2;
import edu.cg.models.IRenderable;
import edu.cg.models.SkewedBox;

public class Back implements IRenderable
{
    private SkewedBox baseBox;
    private SkewedBox backBox;
    private PairOfWheels wheels;
    private Spolier spoiler;
    
    public Back() {
        this.baseBox = new SkewedBox(0.375, 0.013125000000000001, 0.013125000000000001, 0.4, 0.4);
        this.backBox = new SkewedBox(0.525, 0.09937499999999999, 0.11812500000000001, 0.1, 0.4);
        this.wheels = new PairOfWheels();
        this.spoiler = new Spolier();
    }
    
    @Override
    public void render(final GL2 gl) {
        gl.glPushMatrix();
        Materials.SetBlackMetalMaterial(gl);
        gl.glTranslated(0.07500000000000001, 0.0, 0.0);
        this.baseBox.render(gl);
        Materials.SetRedMetalMaterial(gl);
        gl.glTranslated(-0.07500000000000001, 0.013125000000000001, 0.0);
        this.backBox.render(gl);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslated(-0.1875, 0.0375, 0.0);
        this.wheels.render(gl);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslated(-0.1875, 0.10875, 0.0);
        this.spoiler.render(gl);
        gl.glPopMatrix();
    }
    
    @Override
    public void init(final GL2 gl) {
    }
    
    @Override
    public void destroy(final GL2 gl) {
    }
}
