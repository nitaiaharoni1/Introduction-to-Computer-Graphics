package edu.cg.models;

import javax.media.opengl.GL2;

/**
 * A renderable model
 */
public interface IRenderable {

    /**
     * Render the model
     *
     * @param gl GL context
     */
    public void render(GL2 gl);

    /**
     * Initialize the model
     *
     * @param gl GL context
     */
    public void init(GL2 gl);

}
