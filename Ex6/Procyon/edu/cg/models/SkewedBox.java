// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.models;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import edu.cg.algebra.Vec;

import java.io.File;

public class SkewedBox implements IRenderable
{
    private double length;
    private double height1;
    private double height2;
    private double depth1;
    private double depth2;
    private Texture texBox;
    private boolean useTexture;
    
    public SkewedBox() {
        this.texBox = null;
        this.useTexture = false;
        this.length = 0.8;
        this.height1 = 0.7;
        this.height2 = 0.5;
        this.depth1 = 0.7;
        this.depth2 = 0.3;
    }
    
    public SkewedBox(final double length, final double h1, final double h2, final double d1, final double d2) {
        this.texBox = null;
        this.useTexture = false;
        this.length = length;
        this.height1 = h1;
        this.height2 = h2;
        this.depth1 = d1;
        this.depth2 = d2;
    }
    
    public SkewedBox(final double length, final boolean useTexture) {
        this.texBox = null;
        this.useTexture = false;
        this.length = length;
        this.depth1 = length;
        this.depth2 = length;
        this.height1 = length;
        this.height2 = length;
        this.useTexture = useTexture;
    }
    
    @Override
    public void render(final GL2 gl) {
        Vec normal = null;
        if (this.useTexture) {
            assert this.texBox != null && gl != null;
            this.initTextureProperties(gl);
        }
        gl.glNormal3d(1.0, 0.0, 0.0);
        gl.glBegin(7);
        gl.glTexCoord2d(0.0, 0.0);
        gl.glVertex3d(this.length / 2.0, 0.0, this.depth2 / 2.0);
        gl.glTexCoord2d(0.0, 1.0);
        gl.glVertex3d(this.length / 2.0, 0.0, -this.depth2 / 2.0);
        gl.glTexCoord2d(1.0, 1.0);
        gl.glVertex3d(this.length / 2.0, this.height2, -this.depth2 / 2.0);
        gl.glTexCoord2d(1.0, 0.0);
        gl.glVertex3d(this.length / 2.0, this.height2, this.depth2 / 2.0);
        gl.glNormal3d(-1.0, 0.0, 0.0);
        gl.glTexCoord2d(0.0, 0.0);
        gl.glVertex3d(-this.length / 2.0, 0.0, -this.depth1 / 2.0);
        gl.glTexCoord2d(0.0, 1.0);
        gl.glVertex3d(-this.length / 2.0, 0.0, this.depth1 / 2.0);
        gl.glTexCoord2d(1.0, 1.0);
        gl.glVertex3d(-this.length / 2.0, this.height1, this.depth1 / 2.0);
        gl.glTexCoord2d(1.0, 0.0);
        gl.glVertex3d(-this.length / 2.0, this.height1, -this.depth1 / 2.0);
        normal = new Vec(this.height1 - this.height2, 1.0, 0.0).normalize();
        gl.glNormal3d(normal.x, normal.y, normal.z);
        gl.glTexCoord2d(0.0, 0.0);
        gl.glVertex3d(-this.length / 2.0, this.height1, this.depth1 / 2.0);
        gl.glTexCoord2d(0.0, 1.0);
        gl.glVertex3d(this.length / 2.0, this.height2, this.depth2 / 2.0);
        gl.glTexCoord2d(1.0, 1.0);
        gl.glVertex3d(this.length / 2.0, this.height2, -this.depth2 / 2.0);
        gl.glTexCoord2d(1.0, 0.0);
        gl.glVertex3d(-this.length / 2.0, this.height1, -this.depth1 / 2.0);
        gl.glNormal3d(0.0, -1.0, 0.0);
        gl.glTexCoord2d(0.0, 0.0);
        gl.glVertex3d(-this.length / 2.0, 0.0, this.depth1 / 2.0);
        gl.glTexCoord2d(0.0, 1.0);
        gl.glVertex3d(-this.length / 2.0, 0.0, -this.depth1 / 2.0);
        gl.glTexCoord2d(1.0, 1.0);
        gl.glVertex3d(this.length / 2.0, 0.0, -this.depth2 / 2.0);
        gl.glTexCoord2d(1.0, 0.0);
        gl.glVertex3d(this.length / 2.0, 0.0, this.depth2 / 2.0);
        normal = new Vec(this.depth1 - this.depth2, 0.0, 1.0).normalize();
        gl.glNormal3d(normal.x, 0.0, normal.z);
        gl.glTexCoord2d(0.0, 0.0);
        gl.glVertex3d(-this.length / 2.0, this.height1, this.depth1 / 2.0);
        gl.glTexCoord2d(0.0, 1.0);
        gl.glVertex3d(-this.length / 2.0, 0.0, this.depth1 / 2.0);
        gl.glTexCoord2d(1.0, 1.0);
        gl.glVertex3d(this.length / 2.0, 0.0, this.depth2 / 2.0);
        gl.glTexCoord2d(1.0, 0.0);
        gl.glVertex3d(this.length / 2.0, this.height2, this.depth2 / 2.0);
        normal = new Vec(this.depth1 - this.depth2, 0.0, -1.0).normalize();
        gl.glNormal3d(normal.x, 0.0, normal.z);
        gl.glTexCoord2d(0.0, 0.0);
        gl.glVertex3d(-this.length / 2.0, 0.0, -this.depth1 / 2.0);
        gl.glTexCoord2d(0.0, 1.0);
        gl.glVertex3d(-this.length / 2.0, this.height1, -this.depth1 / 2.0);
        gl.glTexCoord2d(1.0, 1.0);
        gl.glVertex3d(this.length / 2.0, this.height2, -this.depth2 / 2.0);
        gl.glTexCoord2d(1.0, 0.0);
        gl.glVertex3d(this.length / 2.0, 0.0, -this.depth2 / 2.0);
        gl.glEnd();
        gl.glDisable(3553);
    }
    
    private void initTextureProperties(final GL2 gl) {
        gl.glEnable(3553);
        this.texBox.bind(gl);
        gl.glTexEnvi(8960, 8704, 8448);
        gl.glTexParameteri(3553, 10241, 9987);
        gl.glTexParameteri(3553, 10240, 9729);
        gl.glTexParameteri(3553, 33083, 1);
    }
    
    @Override
    public void init(final GL2 gl) {
        if (this.useTexture) {
            try {
                this.texBox = TextureIO.newTexture(new File("Textures/WoodBoxTexture.jpg"), true);
            }
            catch (Exception e) {
                System.err.print("Unable to read texture : " + e.getMessage());
            }
        }
    }
    
    @Override
    public void destroy(final GL2 gl) {
        if (this.useTexture) {
            this.texBox.destroy(gl);
            this.texBox = null;
        }
    }
    
    @Override
    public String toString() {
        return "SkewedBox";
    }
}
