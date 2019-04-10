// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.scene.objects;

import edu.cg.algebra.Vec;

public class Material
{
    public Vec Ka;
    public Vec Kd;
    public Vec Ks;
    public double reflectionIntensity;
    public int shininess;
    public boolean isTransparent;
    public double refractionIntensity;
    public double refractionIndex;
    
    public Material() {
        this.Ka = new Vec(0.1, 0.1, 0.1);
        this.Kd = new Vec(1.0, 1.0, 1.0);
        this.Ks = new Vec(0.7, 0.7, 0.7);
        this.reflectionIntensity = 0.3;
        this.shininess = 10;
        this.isTransparent = false;
        this.refractionIntensity = 0.3;
        this.refractionIndex = 1.5;
    }
    
    @Override
    public String toString() {
        final String endl = System.lineSeparator();
        return "Ka: " + this.Ka + endl + "Kd1: " + this.Kd + endl + "Ks: " + this.Ks + endl + "Reflection Intensity: " + this.reflectionIntensity + endl + "Shininess: " + this.shininess + endl + "isTransparent: " + this.isTransparent + endl + "Refraction Intensity: " + this.refractionIntensity + endl + "Refraction Index: " + this.refractionIndex + endl;
    }
    
    public Material initKa(final Vec Ka) {
        this.Ka = Ka;
        return this;
    }
    
    public Material initKd(final Vec Kd) {
        this.Kd = Kd;
        return this;
    }
    
    public Material initKs(final Vec Ks) {
        this.Ks = Ks;
        return this;
    }
    
    public Material initReflectionIntensity(final double reflectionIntensity) {
        this.reflectionIntensity = reflectionIntensity;
        return this;
    }
    
    public Material initShininess(final int shininess) {
        this.shininess = shininess;
        return this;
    }
    
    public Material initRefractionIntensity(final double refractionIntensity) {
        this.refractionIntensity = refractionIntensity;
        return this;
    }
    
    public Material initRefractionIndex(final double refractionIndex) {
        this.refractionIndex = refractionIndex;
        return this;
    }
    
    public Material initIsTransparent(final boolean isTransparent) {
        this.isTransparent = isTransparent;
        return this;
    }
    
    public static Material getGlassMaterial(final boolean transparent) {
        final Material mat = new Material();
        final int shininess = 1 + (int)(Math.random() * 15.0);
        mat.initKa(new Vec(0.1)).initKs(new Vec(0.1)).initShininess(shininess);
        mat.initKd(new Vec(0.1));
        mat.initReflectionIntensity(0.95);
        if (transparent) {
            mat.initIsTransparent(true).initRefractionIntensity(0.2).initReflectionIntensity(0.8);
        }
        return mat;
    }
    
    public static Material getMetalMaterial() {
        final Material mat = new Material().initKa(new Vec(0.2)).initKd(new Vec(0.4)).initKs(new Vec(0.4)).initReflectionIntensity(0.2).initIsTransparent(false);
        return mat;
    }
    
    public static Material getRandomMaterial() {
        final boolean isTransparent = Math.random() < 0.5;
        final boolean isReflect = Math.random() < 0.5;
        if (Math.random() < 0.1) {
            return getGlassMaterial(isTransparent);
        }
        final double rComponent = Math.random();
        final double gComponent = Math.random();
        final double bComponent = Math.random();
        final double refractionIn = isTransparent ? (0.5 * Math.random()) : 0.0;
        final double reflectionIn = isReflect ? (0.5 * Math.random()) : 0.0;
        final double specularIn = 0.5 * Math.random();
        final double randomRefractionIndex = 1.0 + 0.8 * Math.random();
        final Material mat = new Material().initKa(new Vec(rComponent, gComponent, bComponent)).initKd(new Vec(rComponent, gComponent, bComponent)).initKs(new Vec(specularIn)).initReflectionIntensity(reflectionIn);
        if (isTransparent) {
            mat.initIsTransparent(true).initRefractionIndex(randomRefractionIndex).initRefractionIntensity(refractionIn);
        }
        return mat;
    }
}
