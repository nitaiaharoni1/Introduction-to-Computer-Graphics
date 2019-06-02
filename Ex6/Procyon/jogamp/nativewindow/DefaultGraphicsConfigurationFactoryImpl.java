// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow;

import com.jogamp.nativewindow.*;

public class DefaultGraphicsConfigurationFactoryImpl extends GraphicsConfigurationFactory
{
    @Override
    protected AbstractGraphicsConfiguration chooseGraphicsConfigurationImpl(final CapabilitiesImmutable capabilitiesImmutable, final CapabilitiesImmutable capabilitiesImmutable2, final CapabilitiesChooser capabilitiesChooser, final AbstractGraphicsScreen abstractGraphicsScreen, final int n) {
        return new DefaultGraphicsConfiguration(abstractGraphicsScreen, capabilitiesImmutable, capabilitiesImmutable2);
    }
}
