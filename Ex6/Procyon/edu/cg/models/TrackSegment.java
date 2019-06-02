// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.models;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import edu.cg.algebra.Point;
import edu.cg.models.Car.Materials;

import java.io.File;
import java.util.LinkedList;

public class TrackSegment implements IRenderable
{
    public static final double ASPHALT_TEXTURE_WIDTH = 20.0;
    public static final double ASPHALT_TEXTURE_DEPTH = 10.0;
    public static final double GRASS_TEXTURE_WIDTH = 10.0;
    public static final double GRASS_TEXTURE_DEPTH = 10.0;
    public static final double TRACK_LENGTH = 500.0;
    public static final double BOX_LENGTH = 1.5;
    private LinkedList<Point> boxesLocations;
    private SkewedBox box;
    private Texture texRoad;
    private Texture texGrass;
    
    public void setDifficulty(double difficulty) {
        difficulty = Math.min(difficulty, 0.95);
        difficulty = Math.max(difficulty, 0.05);
        final double numberOfLanes = 4.0;
        double deltaZ = 0.0;
        if (difficulty < 0.25) {
            deltaZ = 100.0;
        }
        else if (difficulty < 0.5) {
            deltaZ = 75.0;
        }
        else {
            deltaZ = 50.0;
        }
        this.boxesLocations = new LinkedList<Point>();
        for (double dz = deltaZ; dz < 499.25; dz += deltaZ) {
            int cnt = 0;
            boolean flag = false;
            for (int i = 0; i < 12; ++i) {
                final double dx = -(numberOfLanes / 2.0) * (18.0 / numberOfLanes) + 0.75 + i * 1.5;
                if (Math.random() < difficulty) {
                    this.boxesLocations.add(new Point(dx, 0.75, -dz));
                    ++cnt;
                }
                else if (!flag) {
                    ++i;
                    flag = true;
                }
                if (cnt > difficulty * 10.0) {
                    break;
                }
            }
        }
    }
    
    public TrackSegment(final double difficulty) {
        this.box = null;
        this.box = new SkewedBox(1.5, true);
        this.setDifficulty(difficulty);
    }
    
    @Override
    public void render(final GL2 gl) {
        this.renderBoxes(gl);
        this.renderAsphalt(gl);
        this.renderGrass(gl);
    }
    
    private void renderBoxes(final GL2 gl) {
        Materials.setWoodenBoxMaterial(gl);
        for (final Point p : this.boxesLocations) {
            gl.glPushMatrix();
            gl.glTranslated(p.x, 0.0, p.z);
            this.box.render(gl);
            gl.glPopMatrix();
        }
    }
    
    private void renderAsphalt(final GL2 gl) {
        Materials.setAsphaltMaterial(gl);
        gl.glPushMatrix();
        this.renderQuadraticTexture(gl, this.texRoad, 20.0, 10.0, 6, 500.0);
        gl.glPopMatrix();
    }
    
    private void renderGrass(final GL2 gl) {
        Materials.setGreenMaterial(gl);
        final double dx = 15.0;
        gl.glTranslated(dx, 0.0, 0.0);
        this.renderQuadraticTexture(gl, this.texGrass, 10.0, 10.0, 2, 500.0);
        gl.glTranslated(-2.0 * dx, 0.0, 0.0);
        this.renderQuadraticTexture(gl, this.texGrass, 10.0, 10.0, 2, 500.0);
        gl.glPopMatrix();
    }
    
    private void renderQuadraticTexture(final GL2 gl, final Texture tex, final double quadWidth, final double quadDepth, final int split, final double totalDepth) {
        gl.glEnable(3553);
        tex.bind(gl);
        gl.glTexEnvi(8960, 8704, 8448);
        gl.glTexParameteri(3553, 10241, 9987);
        gl.glTexParameteri(3553, 10240, 9729);
        gl.glTexParameteri(3553, 33083, 1);
        gl.glColor3d(1.0, 0.0, 0.0);
        final GLU glu = new GLU();
        final GLUquadric quad = glu.gluNewQuadric();
        gl.glColor3d(1.0, 0.0, 0.0);
        gl.glNormal3d(0.0, 1.0, 0.0);
        final double d = 1.0 / split;
        final double dz = quadDepth / split;
        final double dx = quadWidth / split;
        for (double tz = 0.0; tz < totalDepth; tz += quadDepth) {
            for (double i = 0.0; i < split; ++i) {
                gl.glBegin(5);
                for (double j = 0.0; j <= split; ++j) {
                    gl.glTexCoord2d(j * d, (i + 1.0) * d);
                    gl.glVertex3d(-quadWidth / 2.0 + j * dx, 0.0, -tz - (i + 1.0) * dz);
                    gl.glTexCoord2d(j * d, i * d);
                    gl.glVertex3d(-quadWidth / 2.0 + j * dx, 0.0, -tz - i * dz);
                }
                gl.glEnd();
            }
        }
        glu.gluDeleteQuadric(quad);
        gl.glDisable(3553);
    }
    
    @Override
    public void init(final GL2 gl) {
        this.box.init(gl);
        try {
            this.texRoad = TextureIO.newTexture(new File("Textures/RoadTexture.jpg"), true);
            this.texGrass = TextureIO.newTexture(new File("Textures/GrassTexture.jpg"), true);
        }
        catch (Exception e) {
            System.err.print("Unable to read texture : " + e.getMessage());
        }
    }
    
    @Override
    public void destroy(final GL2 gl) {
        this.texRoad.destroy(gl);
        this.texGrass.destroy(gl);
        this.box.destroy(gl);
    }
}
