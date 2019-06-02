// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.models;

import com.jogamp.opengl.GL2;

public class Track implements IRenderable
{
    private TrackSegment currentTrackSegment;
    private TrackSegment nextTrackSegment;
    private double currentDifficulty;
    private final double DIFFICULTY_DELTA = 0.05;
    private final double MAXIMUM_DIFFICULTY = 0.95;
    
    public Track() {
        this.currentTrackSegment = null;
        this.nextTrackSegment = null;
        this.currentDifficulty = 0.5;
        this.currentTrackSegment = new TrackSegment(this.currentDifficulty);
        this.nextTrackSegment = new TrackSegment(this.currentDifficulty + 0.05);
    }
    
    @Override
    public void render(final GL2 gl) {
        gl.glPushMatrix();
        this.currentTrackSegment.render(gl);
        gl.glTranslated(0.0, 0.0, -500.0);
        this.nextTrackSegment.render(gl);
        gl.glPopMatrix();
    }
    
    @Override
    public void init(final GL2 gl) {
        this.currentTrackSegment.init(gl);
        this.nextTrackSegment.init(gl);
    }
    
    @Override
    public void destroy(final GL2 gl) {
        this.currentTrackSegment.destroy(gl);
        this.nextTrackSegment.destroy(gl);
        final TrackSegment trackSegment = null;
        this.nextTrackSegment = trackSegment;
        this.currentTrackSegment = trackSegment;
    }
    
    public void changeTrack(final GL2 gl) {
        final TrackSegment tmp = this.currentTrackSegment;
        this.currentTrackSegment = this.nextTrackSegment;
        this.currentDifficulty += 0.05;
        this.currentDifficulty = Math.min(this.currentDifficulty, 0.95);
        tmp.setDifficulty(this.currentDifficulty + 0.05);
        this.nextTrackSegment = tmp;
    }
}
