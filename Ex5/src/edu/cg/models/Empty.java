package edu.cg.models;

import javax.media.opengl.GL2;

/**
 * A simple axes dummy
 */
public class Empty implements IRenderable {

    public void render(GL2 gl) {
    }

    @Override
    public String toString() {
        return "Empty";
    }

    @Override
    public void init(GL2 gl) {

    }
}
