package edu.cg.models.Car;

import com.jogamp.opengl.GL2;

import edu.cg.models.IRenderable;
import edu.cg.models.SkewedBox;

public class Back implements IRenderable {
    //This method is implemented.
    //       Look at the implementation and understand it.
    private SkewedBox baseBox = new SkewedBox(Specification.B_BASE_LENGTH, Specification.B_BASE_HEIGHT, Specification.B_BASE_HEIGHT, Specification.B_BASE_DEPTH, Specification.B_BASE_DEPTH);
    private SkewedBox backBox = new SkewedBox(Specification.B_LENGTH, Specification.B_HEIGHT_1, Specification.B_HEIGHT_2, Specification.B_DEPTH_1, Specification.B_DEPTH_2);
    private PairOfWheels wheels = new PairOfWheels();
    private Spolier spoiler = new Spolier();

    @Override
    public void render(GL2 gl) {
        gl.glPushMatrix();
        gl.glTranslated(-Specification.B_BASE_LENGTH, 0, 0);

        gl.glPushMatrix();
        gl.glTranslated(Specification.B_LENGTH / 2 - Specification.B_BASE_LENGTH / 2, 0, 0);
        Materials.SetBlackMetalMaterial(gl);
        baseBox.render(gl);

        Materials.SetRedMetalMaterial(gl);

        gl.glTranslated(-(Specification.B_LENGTH / 2 - Specification.B_BASE_LENGTH / 2), Specification.B_BASE_HEIGHT, 0);
        backBox.render(gl);
        gl.glPopMatrix();


        gl.glPushMatrix();
        gl.glTranslated(-Specification.B_LENGTH / 2 + Specification.TIRE_RADIUS, Specification.TIRE_RADIUS / 2, 0);
        wheels.render(gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslated(-Specification.B_LENGTH / 2 + Specification.S_LENGTH / 2, (Specification.B_HEIGHT_1 + Specification.B_HEIGHT_2) / 2, 0);
        spoiler.render(gl);
        gl.glPopMatrix();

        gl.glPopMatrix();

    }

    @Override
    public void init(GL2 gl) {
        // Auto-generated method stub

    }

    @Override
    public void destroy(GL2 gl) {

    }

}
