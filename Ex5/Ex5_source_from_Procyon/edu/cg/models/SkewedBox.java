// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.models;

import com.jogamp.opengl.GL2;

public class SkewedBox implements IRenderable
{
    private double length;
    private double height1;
    private double height2;
    private double depth1;
    private double depth2;
    
    public SkewedBox() {
        this.length = 0.1;
        this.height1 = 0.2;
        this.height2 = 0.1;
        this.depth1 = 0.2;
        this.depth2 = 0.1;
    }
    
    public SkewedBox(final double length, final double h1, final double h2, final double d1, final double d2) {
        this.length = length;
        this.height1 = h1;
        this.height2 = h2;
        this.depth1 = d1;
        this.depth2 = d2;
    }
    
    @Override
    public void render(final GL2 gl) {
        gl.glNormal3d(1.0, 0.0, 0.0);
        gl.glBegin(7);
        gl.glVertex3d(this.length / 2.0, 0.0, this.depth2 / 2.0);
        gl.glVertex3d(this.length / 2.0, 0.0, -this.depth2 / 2.0);
        gl.glVertex3d(this.length / 2.0, this.height2, -this.depth2 / 2.0);
        gl.glVertex3d(this.length / 2.0, this.height2, this.depth2 / 2.0);
        gl.glEnd();
        gl.glNormal3d(-1.0, 0.0, 0.0);
        gl.glBegin(7);
        gl.glVertex3d(-this.length / 2.0, 0.0, -this.depth1 / 2.0);
        gl.glVertex3d(-this.length / 2.0, 0.0, this.depth1 / 2.0);
        gl.glVertex3d(-this.length / 2.0, this.height1, this.depth1 / 2.0);
        gl.glVertex3d(-this.length / 2.0, this.height1, -this.depth1 / 2.0);
        gl.glEnd();
        gl.glNormal3d(0.0, 1.0, 0.0);
        gl.glBegin(7);
        gl.glVertex3d(-this.length / 2.0, this.height1, this.depth1 / 2.0);
        gl.glVertex3d(this.length / 2.0, this.height2, this.depth2 / 2.0);
        gl.glVertex3d(this.length / 2.0, this.height2, -this.depth2 / 2.0);
        gl.glVertex3d(-this.length / 2.0, this.height1, -this.depth1 / 2.0);
        gl.glEnd();
        gl.glNormal3d(0.0, -1.0, 0.0);
        gl.glBegin(7);
        gl.glVertex3d(-this.length / 2.0, 0.0, this.depth1 / 2.0);
        gl.glVertex3d(-this.length / 2.0, 0.0, -this.depth1 / 2.0);
        gl.glVertex3d(this.length / 2.0, 0.0, -this.depth2 / 2.0);
        gl.glVertex3d(this.length / 2.0, 0.0, this.depth2 / 2.0);
        gl.glEnd();
        gl.glNormal3d(0.0, 0.0, 1.0);
        gl.glBegin(7);
        gl.glVertex3d(-this.length / 2.0, this.height1, this.depth1 / 2.0);
        gl.glVertex3d(-this.length / 2.0, 0.0, this.depth1 / 2.0);
        gl.glVertex3d(this.length / 2.0, 0.0, this.depth2 / 2.0);
        gl.glVertex3d(this.length / 2.0, this.height2, this.depth2 / 2.0);
        gl.glEnd();
        gl.glNormal3d(0.0, 0.0, -1.0);
        gl.glBegin(7);
        gl.glVertex3d(-this.length / 2.0, 0.0, -this.depth1 / 2.0);
        gl.glVertex3d(-this.length / 2.0, this.height1, -this.depth1 / 2.0);
        gl.glVertex3d(this.length / 2.0, this.height2, -this.depth2 / 2.0);
        gl.glVertex3d(this.length / 2.0, 0.0, -this.depth2 / 2.0);
        gl.glEnd();
    }
    
    @Override
    public void init(final GL2 gl) {
    }
    
    @Override
    public String toString() {
        return "SkewedBox";
    }
}
