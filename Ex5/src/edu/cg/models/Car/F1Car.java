package edu.cg.models.Car;

import edu.cg.models.IRenderable;

import javax.media.opengl.GL2;

/**
 * A F1 Racing Car.
 */
public class F1Car implements IRenderable {

    @Override
    public void render(GL2 gl) {
        //Render the whole car.
        //       Here You should only render the three parts: back, center and front.
        new Center().render(gl);
        new Back().render(gl);
        new Front().render(gl);
    }

    @Override
    public String toString() {
        return "F1Car";
    }

    @Override
    public void init(GL2 gl) {

    }
}
