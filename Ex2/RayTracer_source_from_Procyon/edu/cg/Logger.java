// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg;

public interface Logger
{
    void log(final String p0);
    
    default void log(final Object obj) {
        this.log((obj == null) ? "null" : obj.toString());
    }
}
