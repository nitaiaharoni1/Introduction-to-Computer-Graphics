// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.nativewindow.macosx;

import com.jogamp.nativewindow.DefaultGraphicsDevice;
import com.jogamp.nativewindow.NativeWindowFactory;

public class MacOSXGraphicsDevice extends DefaultGraphicsDevice implements Cloneable
{
    public MacOSXGraphicsDevice(final int n) {
        super(NativeWindowFactory.TYPE_MACOSX, "decon", n);
    }
    
    @Override
    public Object clone() {
        return super.clone();
    }
}
