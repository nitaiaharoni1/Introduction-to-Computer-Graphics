// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.models.Car;

import com.jogamp.opengl.GL2;
import edu.cg.algebra.Point;
import edu.cg.models.IRenderable;

import java.util.LinkedList;
import java.util.List;

public class F1Car implements IRenderable
{
    @Override
    public void render(final GL2 gl) {
        new Center().render(gl);
        gl.glPushMatrix();
        gl.glTranslated(-0.3875, 0.0, 0.0);
        new Back().render(gl);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslated(0.425, 0.0, 0.0);
        new Front().render(gl);
        gl.glPopMatrix();
    }
    
    @Override
    public String toString() {
        return "F1Car";
    }
    
    @Override
    public void init(final GL2 gl) {
    }
    
    @Override
    public void destroy(final GL2 gl) {
    }
    
    private Point pointMatrixMult(final double[] mat, final Point p) {
        float temp = 0.0f;
        final float[] arr = p.toArray();
        final float[] res = new float[3];
        for (int r = 0; r < 3; ++r) {
            for (int c = 0; c < 3; ++c) {
                temp += (float)(mat[4 * r + c] * arr[c]);
            }
            temp += (float)mat[4 * r + 3];
            res[r] = temp;
            temp = 0.0f;
        }
        final Point resP = new Point(0.0);
        resP.x = res[0];
        resP.y = res[1];
        resP.z = res[2];
        return resP;
    }
    
    public List<Point> getPointsOnSurface(final GL2 gl) {
        final LinkedList<Point> retList = new LinkedList<Point>();
        final double[] temp = new double[16];
        gl.glGetDoublev(2982, temp, 0);
        retList.add(this.pointMatrixMult(temp, new Point(0.0)));
        return retList;
    }
}
