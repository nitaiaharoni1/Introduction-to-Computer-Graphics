// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.models;

import com.jogamp.opengl.GL2;

public class Empty implements IRenderable
{
    @Override
    public void render(final GL2 gl) {
    }
    
    @Override
    public String toString() {
        return "Empty";
    }
    
    @Override
    public void init(final GL2 gl) {
    }
}
