// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.scene.objects;

import edu.cg.algebra.Vec;
import edu.cg.algebra.Hit;
import edu.cg.algebra.Ray;

public class Surface implements Intersectable
{
    private Shape shape;
    private Material material;
    
    public Surface(final Shape shape, final Material material) {
        this.shape = shape;
        this.material = material;
    }
    
    public Surface() {
        this(null, null);
    }
    
    @Override
    public String toString() {
        final String endl = System.lineSeparator();
        return "Surface:" + endl + "Shape:" + endl + this.shape + endl + "Material: " + endl + this.material + endl;
    }
    
    @Override
    public Hit intersect(final Ray ray) {
        final Hit hit = this.shape.intersect(ray);
        if (hit != null) {
            hit.setSurface(this);
        }
        return hit;
    }
    
    public Vec Ka() {
        return this.material.Ka;
    }
    
    public Vec Kd() {
        return this.material.Kd;
    }
    
    public Vec Ks() {
        return this.material.Ks;
    }
    
    public double reflectionIntensity() {
        return this.material.reflectionIntensity;
    }
    
    public int shininess() {
        return this.material.shininess;
    }
    
    public double refractionIndex() {
        return this.material.refractionIndex;
    }
    
    public double refractionIntensity() {
        return this.material.refractionIntensity;
    }
    
    public boolean isTransparent() {
        return this.material.isTransparent;
    }
    
    public double n1(final Hit hit) {
        return hit.isWithinTheSurface() ? this.material.refractionIndex : 1.0;
    }
    
    public double n2(final Hit hit) {
        return hit.isWithinTheSurface() ? 1.0 : this.material.refractionIndex;
    }
}
