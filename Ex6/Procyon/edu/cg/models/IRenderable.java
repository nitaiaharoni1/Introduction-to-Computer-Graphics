// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.models;

import com.jogamp.opengl.GL2;

public interface IRenderable
{
    void render(final GL2 p0);
    
    void init(final GL2 p0);
    
    void destroy(final GL2 p0);
}
