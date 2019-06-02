// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.models.Car;

import com.jogamp.opengl.GL2;

public class Materials
{
    private static final float[] dark_gray;
    
    static {
        dark_gray = new float[] { 0.2f, 0.2f, 0.2f };
    }
    
    public static void SetBlackMetalMaterial(final GL2 gl) {
        final float[] mat_ambient = { 0.0f, 0.0f, 0.0f, 1.0f };
        final float[] mat_diffuse = { 0.01f, 0.01f, 0.01f, 1.0f };
        final float[] mat_specular = { 0.5f, 0.5f, 0.5f, 1.0f };
        final float shine = 32.0f;
        gl.glMaterialf(1028, 5633, shine);
        gl.glMaterialfv(1028, 4608, mat_ambient, 0);
        gl.glMaterialfv(1028, 4609, mat_diffuse, 0);
        gl.glMaterialfv(1028, 4610, mat_specular, 0);
    }
    
    public static void SetRedMetalMaterial(final GL2 gl) {
        final float[] mat_ambient = { 0.1745f, 0.01175f, 0.01175f, 1.0f };
        final float[] mat_diffuse = { 0.61424f, 0.04136f, 0.04136f, 1.0f };
        final float[] mat_specular = { 0.727811f, 0.626959f, 0.626959f, 1.0f };
        final float shine = 76.8f;
        gl.glMaterialf(1028, 5633, shine);
        gl.glMaterialfv(1028, 4608, mat_ambient, 0);
        gl.glMaterialfv(1028, 4609, mat_diffuse, 0);
        gl.glMaterialfv(1028, 4610, mat_specular, 0);
    }
    
    public static void SetDarkRedMetalMaterial(final GL2 gl) {
        final float[] mat_ambient = { 0.0f, 0.0f, 0.0f, 1.0f };
        final float[] mat_diffuse = { 0.4f, 0.0f, 0.0f, 1.0f };
        final float[] mat_specular = { 0.4f, 0.3f, 0.3f, 1.0f };
        final float shine = 32.0f;
        gl.glMaterialf(1028, 5633, shine);
        gl.glMaterialfv(1028, 4608, mat_ambient, 0);
        gl.glMaterialfv(1028, 4609, mat_diffuse, 0);
        gl.glMaterialfv(1028, 4610, mat_specular, 0);
    }
    
    public static void SetDarkGreyMetalMaterial(final GL2 gl) {
        final float[] mat_ambient = { 0.25f, 0.25f, 0.25f, 1.0f };
        final float[] mat_diffuse = { 0.4f, 0.4f, 0.4f, 1.0f };
        final float[] mat_specular = { 0.774597f, 0.774597f, 0.774597f, 1.0f };
        final float shine = 76.8f;
        gl.glMaterialf(1028, 5633, shine);
        gl.glMaterialfv(1028, 4608, mat_ambient, 0);
        gl.glMaterialfv(1028, 4609, mat_diffuse, 0);
        gl.glMaterialfv(1028, 4610, mat_specular, 0);
    }
    
    public static void setMaterialTire(final GL2 gl) {
        final float[] col = { 0.05f, 0.05f, 0.05f };
        gl.glColor3fv(col, 0);
        gl.glMaterialf(1028, 5633, 100.0f);
        gl.glMaterialfv(1028, 4609, col, 0);
        gl.glMaterialfv(1028, 4610, col, 0);
    }
    
    public static void setMaterialRims(final GL2 gl) {
        final float[] col = { 0.8f, 0.8f, 0.8f };
        gl.glColor3fv(Materials.dark_gray, 0);
        gl.glMaterialf(1028, 5633, 20.0f);
        gl.glMaterialfv(1028, 4609, Materials.dark_gray, 0);
        gl.glMaterialfv(1028, 4610, col, 0);
    }
    
    public static void setGreenMaterial(final GL2 gl) {
        final float[] mat_ambient = { 0.0215f, 0.1745f, 0.0215f, 1.0f };
        final float[] mat_diffuse = { 0.07568f, 0.61424f, 0.07568f, 1.0f };
        final float[] mat_specular = { 0.633f, 0.727811f, 0.633f, 1.0f };
        final float shine = 128.0f;
        gl.glMaterialf(1028, 5633, shine);
        gl.glMaterialfv(1028, 4608, mat_ambient, 0);
        gl.glMaterialfv(1028, 4609, mat_diffuse, 0);
        gl.glMaterialfv(1028, 4610, mat_specular, 0);
    }
    
    public static void setAsphaltMaterial(final GL2 gl) {
        final float[] mat_ambient = { 0.15375f, 0.15f, 0.16625f, 1.0f };
        final float[] mat_diffuse = { 0.68275f, 0.67f, 0.72525f, 1.0f };
        final float[] mat_specular = { 0.332741f, 0.328634f, 0.346435f, 1.0f };
        final float shine = 38.4f;
        gl.glMaterialf(1028, 5633, shine);
        gl.glMaterialfv(1028, 4608, mat_ambient, 0);
        gl.glMaterialfv(1028, 4609, mat_diffuse, 0);
        gl.glMaterialfv(1028, 4610, mat_specular, 0);
    }
    
    public static void setWoodenBoxMaterial(final GL2 gl) {
        final float[] mat_ambient = { 0.4f, 0.4f, 0.4f, 1.0f };
        final float[] mat_diffuse = { 0.714f, 0.4284f, 0.18144f, 1.0f };
        final float[] mat_specular = { 0.393548f, 0.271906f, 0.166721f, 1.0f };
        final float shine = 25.6f;
        gl.glMaterialf(1028, 5633, shine);
        gl.glMaterialfv(1028, 4608, mat_ambient, 0);
        gl.glMaterialfv(1028, 4609, mat_diffuse, 0);
        gl.glMaterialfv(1028, 4610, mat_specular, 0);
    }
}
