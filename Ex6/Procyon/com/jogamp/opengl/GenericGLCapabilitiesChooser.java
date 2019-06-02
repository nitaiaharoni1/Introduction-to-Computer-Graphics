// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl;

import com.jogamp.nativewindow.CapabilitiesImmutable;

import java.util.List;

public class GenericGLCapabilitiesChooser extends DefaultGLCapabilitiesChooser
{
    @Override
    public int chooseCapabilities(final CapabilitiesImmutable capabilitiesImmutable, final List<? extends CapabilitiesImmutable> list, final int n) {
        return super.chooseCapabilities(capabilitiesImmutable, list, -1);
    }
}
