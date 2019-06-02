// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import edu.cg.algebra.Vec;
import edu.cg.models.Car.F1Car;
import edu.cg.models.Track;

import java.awt.*;

public class NeedForSpeed implements GLEventListener
{
    private GameState gameState;
    private F1Car car;
    private Vec carCameraTranslation;
    private Track gameTrack;
    private FPSAnimator ani;
    private Component glPanel;
    private boolean isModelInitialized;
    private boolean isDayMode;
    
    public NeedForSpeed(final Component glPanel) {
        this.gameState = null;
        this.car = null;
        this.carCameraTranslation = null;
        this.gameTrack = null;
        this.isModelInitialized = false;
        this.isDayMode = true;
        this.glPanel = glPanel;
        this.gameState = new GameState();
        this.gameTrack = new Track();
        this.carCameraTranslation = new Vec(0.0);
        this.car = new F1Car();
    }
    
    @Override
    public void display(final GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        if (!this.isModelInitialized) {
            this.initModel(gl);
        }
        if (this.isDayMode) {
            gl.glClearColor(0.52f, 0.824f, 1.0f, 1.0f);
        }
        else {
            gl.glClearColor(0.0f, 0.0f, 0.32f, 1.0f);
        }
        gl.glClear(16640);
        gl.glMatrixMode(5888);
        gl.glLoadIdentity();
        this.updateCarCameraTranslation(gl);
        this.setupCamera(gl);
        this.setupLights(gl);
        this.renderCar(gl);
        this.renderTrack(gl);
    }
    
    private void updateCarCameraTranslation(final GL2 gl) {
        final Vec ret = this.gameState.getNextTranslation();
        this.carCameraTranslation = this.carCameraTranslation.add(ret);
        final double dx = Math.max(this.carCameraTranslation.x, -7.0);
        this.carCameraTranslation.x = (float)Math.min(dx, 7.0);
        if (Math.abs(this.carCameraTranslation.z) >= 510.0) {
            this.carCameraTranslation.z = -(float)(Math.abs(this.carCameraTranslation.z) % 500.0);
            this.gameTrack.changeTrack(gl);
        }
    }
    
    private void setupCamera(final GL2 gl) {
        final GLU glu = new GLU();
        glu.gluLookAt(0.0 + this.carCameraTranslation.x, 1.8 + this.carCameraTranslation.y, 2.0 + this.carCameraTranslation.z, 0.0 + this.carCameraTranslation.x, 1.5 + this.carCameraTranslation.y, -5.0 + this.carCameraTranslation.z, 0.0, 0.7, -0.3);
    }
    
    private void setupSun(final GL2 gl, final int light) {
        final float[] sunColor = { 1.0f, 1.0f, 1.0f, 1.0f };
        final Vec dir = new Vec(0.0, 1.0, 1.0).normalize();
        final float[] pos = { dir.x, dir.y, dir.z, 0.0f };
        gl.glLightfv(light, 4610, sunColor, 0);
        gl.glLightfv(light, 4609, sunColor, 0);
        gl.glLightfv(light, 4611, pos, 0);
        gl.glLightfv(light, 4608, new float[] { 0.1f, 0.1f, 0.1f, 1.0f }, 0);
        gl.glEnable(light);
    }
    
    private void setupMoon(final GL2 gl) {
        gl.glLightModelfv(2899, new float[] { 0.15f, 0.15f, 0.18f, 1.0f }, 0);
    }
    
    private void setupSpotlight(final GL2 gl, final int light, final float[] pos) {
        final float[] sunColor = { 0.85f, 0.85f, 0.85f, 1.0f };
        gl.glLightfv(light, 4611, pos, 0);
        gl.glLightf(light, 4614, 75.0f);
        gl.glLightfv(light, 4612, new float[] { 0.0f, -1.0f, 0.0f }, 0);
        gl.glLightfv(light, 4610, sunColor, 0);
        gl.glLightfv(light, 4609, sunColor, 0);
        gl.glEnable(light);
    }
    
    private void setupLights(final GL2 gl) {
        if (this.isDayMode) {
            gl.glDisable(16385);
            this.setupSun(gl, 16384);
        }
        else {
            this.setupMoon(gl);
            final float[] pos1 = { 0.0f + this.carCameraTranslation.x, 8.0f + this.carCameraTranslation.y, -0.0f + this.carCameraTranslation.z, 1.0f };
            this.setupSpotlight(gl, 16384, pos1);
            final float[] pos2 = { 0.0f + this.carCameraTranslation.x, 8.0f + this.carCameraTranslation.y, -15.0f + this.carCameraTranslation.z, 1.0f };
            this.setupSpotlight(gl, 16385, pos2);
        }
    }
    
    private void renderTrack(final GL2 gl) {
        gl.glPushMatrix();
        this.gameTrack.render(gl);
        gl.glPopMatrix();
    }
    
    private void renderCar(final GL2 gl) {
        final double carRotation = this.gameState.getCarRotation();
        gl.glPushMatrix();
        gl.glTranslated(0.0 + this.carCameraTranslation.x, 0.15 + this.carCameraTranslation.y, -6.6 + this.carCameraTranslation.z);
        gl.glRotated(-carRotation, 0.0, 1.0, 0.0);
        gl.glRotated(90.0, 0.0, 0.1, 0.0);
        gl.glScaled(4.0, 4.0, 4.0);
        this.car.render(gl);
        gl.glPopMatrix();
    }
    
    public GameState getGameState() {
        return this.gameState;
    }
    
    @Override
    public void dispose(final GLAutoDrawable drawable) {
    }
    
    @Override
    public void init(final GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        (this.ani = new FPSAnimator(30, true)).add(drawable);
        this.glPanel.repaint();
        this.initModel(gl);
        this.ani.start();
    }
    
    public void initModel(final GL2 gl) {
        gl.glCullFace(1029);
        gl.glEnable(2884);
        gl.glEnable(2977);
        gl.glEnable(2929);
        gl.glEnable(2896);
        gl.glEnable(7425);
        this.car.init(gl);
        this.gameTrack.init(gl);
        this.isModelInitialized = true;
    }
    
    @Override
    public void reshape(final GLAutoDrawable drawable, final int x, final int y, final int width, final int height) {
        final GL2 gl = drawable.getGL().getGL2();
        final GLU glu = new GLU();
        final double aspect = width / height;
        gl.glMatrixMode(5889);
        gl.glLoadIdentity();
        glu.gluPerspective(57.0, aspect, 2.0, 500.0);
    }
    
    public void startAnimation() {
        if (!this.ani.isAnimating()) {
            this.ani.start();
        }
    }
    
    public void stopAnimation() {
        if (this.ani.isAnimating()) {
            this.ani.stop();
        }
    }
    
    public void toggleNightMode() {
        this.isDayMode = !this.isDayMode;
    }
}
