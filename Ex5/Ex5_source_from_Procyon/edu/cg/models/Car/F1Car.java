// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.models.Car;

import com.jogamp.opengl.GL2;
import edu.cg.models.IRenderable;

public class F1Car implements IRenderable
{
    @Override
    public void render(final GL2 gl) {
        new Center().render(gl);
        gl.glPushMatrix();
        gl.glTranslated(-0.3875, 0.0, 0.0);
        new Back().render(gl);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslated(0.425, 0.0, 0.0);
        new Front().render(gl);
        gl.glPopMatrix();
    }
    
    @Override
    public String toString() {
        return "F1Car";
    }
    
    @Override
    public void init(final GL2 gl) {
    }
}
