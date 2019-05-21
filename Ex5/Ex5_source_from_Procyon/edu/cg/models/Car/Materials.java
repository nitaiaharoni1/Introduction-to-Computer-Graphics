// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.models.Car;

import com.jogamp.opengl.GL2;

public class Materials
{
    private static final float[] dark_gray;
    private static final float[] dark_red;
    private static final float[] red;
    private static final float[] black;
    
    static {
        dark_gray = new float[] { 0.2f, 0.2f, 0.2f };
        dark_red = new float[] { 0.25f, 0.01f, 0.01f };
        red = new float[] { 0.5f, 0.0f, 0.0f };
        black = new float[] { 0.05f, 0.05f, 0.05f };
    }
    
    public static void SetMetalMaterial(final GL2 gl, final float[] color) {
        final float[] specular = { 0.8f, 0.8f, 0.8f };
        final float[] emissionColor = new float[3];
        final float[] diffColor = new float[3];
        float minIntensity = 1.0f;
        float maxIntensity = 0.0f;
        for (int i = 0; i < 3; ++i) {
            emissionColor[i] = 0.5f * color[i];
            minIntensity = Math.min(color[i], minIntensity);
            maxIntensity = Math.min(color[i], maxIntensity);
        }
        for (int i = 0; i < 3; ++i) {
            diffColor[i] = color[i] + 0.5f * (maxIntensity - minIntensity);
        }
        gl.glColor3fv(color, 0);
        gl.glMaterialf(1028, 5633, 10.0f);
        gl.glMaterialfv(1028, 4609, diffColor, 0);
        gl.glMaterialfv(1028, 4610, specular, 0);
        gl.glMaterialfv(1028, 5632, emissionColor, 0);
    }
    
    public static void SetBlackMetalMaterial(final GL2 gl) {
        SetMetalMaterial(gl, Materials.black);
    }
    
    public static void SetRedMetalMaterial(final GL2 gl) {
        SetMetalMaterial(gl, Materials.red);
    }
    
    public static void SetDarkRedMetalMaterial(final GL2 gl) {
        SetMetalMaterial(gl, Materials.dark_red);
    }
    
    public static void SetDarkGreyMetalMaterial(final GL2 gl) {
        gl.glMaterialf(1028, 5633, 25.0f);
        SetMetalMaterial(gl, Materials.dark_gray);
    }
    
    public static void setMaterialTire(final GL2 gl) {
        final float[] col = { 0.05f, 0.05f, 0.05f };
        gl.glColor3fv(col, 0);
        gl.glMaterialf(1028, 5633, 100.0f);
        gl.glMaterialfv(1028, 4609, col, 0);
        gl.glMaterialfv(1028, 4610, col, 0);
        gl.glMaterialfv(1028, 5632, col, 0);
    }
    
    public static void setMaterialRims(final GL2 gl) {
        final float[] col = { 0.8f, 0.8f, 0.8f };
        gl.glColor3fv(Materials.dark_gray, 0);
        gl.glMaterialf(1028, 5633, 20.0f);
        gl.glMaterialfv(1028, 4609, Materials.dark_gray, 0);
        gl.glMaterialfv(1028, 4610, col, 0);
        gl.glMaterialfv(1028, 5632, Materials.dark_gray, 0);
    }
}
