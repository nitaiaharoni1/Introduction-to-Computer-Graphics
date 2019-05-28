package edu.cg.models.Car;

import edu.cg.models.IRenderable;
import edu.cg.models.SkewedBox;

import javax.media.opengl.GL2;

public class Front implements IRenderable {
    //The front of the car is build from the following elements:
    private SkewedBox hoodBox1 = new SkewedBox(Specification.F_HOOD_LENGTH_1, Specification.F_HOOD_HEIGHT_1, Specification.F_HOOD_HEIGHT_2, Specification.F_HOOD_DEPTH_1, Specification.F_HOOD_DEPTH_2);
    private SkewedBox hoodBox2 = new SkewedBox(Specification.F_HOOD_LENGTH_2, Specification.F_HOOD_HEIGHT_2, Specification.F_BUMPER_HEIGHT_1, Specification.F_HOOD_DEPTH_2, Specification.F_HOOD_DEPTH_3);
    private SkewedBox bumperBox = new SkewedBox(Specification.F_BUMPER_LENGTH, Specification.F_BUMPER_HEIGHT_1, Specification.F_BUMPER_HEIGHT_2, Specification.F_BUMPER_DEPTH, Specification.F_BUMPER_DEPTH);
    private SkewedBox bumperWingsBox = new SkewedBox(Specification.F_BUMPER_LENGTH, Specification.F_BUMPER_WINGS_HEIGHT, Specification.F_BUMPER_HEIGHT_2, Specification.F_BUMPER_WINGS_DEPTH, Specification.F_BUMPER_WINGS_DEPTH);
    private PairOfWheels wheels = new PairOfWheels();


    @Override
    public void render(GL2 gl) {
        //Render the front of the car.
        Materials.SetRedMetalMaterial(gl);
        gl.glPushMatrix();
        gl.glTranslated((Specification.F_FRONT_LENGTH) / 2 - Specification.S_ROD_RADIUS, 0, 0);

        hoodBox1.render(gl);

        gl.glTranslated(Specification.C_BASE_LENGTH, 0, 0);
        hoodBox2.render(gl);

        gl.glTranslated(Specification.F_HOOD_LENGTH_1 / 2 - Specification.S_ROD_RADIUS, 0, 0);

        gl.glPushMatrix();
        gl.glTranslated(0, 0, Specification.F_BUMPER_DEPTH / 2);
        bumperWingsBox.render(gl);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslated(0, 0, -Specification.F_BUMPER_DEPTH / 2);
        bumperWingsBox.render(gl);
        gl.glPopMatrix();

        Materials.SetDarkRedMetalMaterial(gl);
        bumperBox.render(gl);

        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslated((Specification.F_FRONT_LENGTH) / 2 - Specification.S_ROD_RADIUS + Specification.C_BASE_LENGTH, Specification.F_HOOD_HEIGHT_2 / 2, 0);
        wheels.render(gl);
        gl.glPopMatrix();

        gl.glPopMatrix();

    }

    @Override
    public void init(GL2 gl) {
        //Auto-generated method stub

    }

}
