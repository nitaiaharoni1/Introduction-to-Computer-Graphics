// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt.driver;

import com.jogamp.common.util.IOUtil;
import com.jogamp.common.util.ReflectionUtil;
import jogamp.newt.Debug;
import jogamp.newt.DisplayImpl;
import jogamp.newt.driver.opengl.JoglUtilPNGIcon;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;

public class PNGIcon
{
    private static final String err0 = "PNG decoder not implemented.";
    private static final boolean avail;
    
    public static boolean isAvailable() {
        return PNGIcon.avail;
    }
    
    public static ByteBuffer arrayToX11BGRAImages(final IOUtil.ClassResources classResources, final int[] array, final int[] array2) throws UnsupportedOperationException, InterruptedException, IOException, MalformedURLException {
        if (PNGIcon.avail) {
            return JoglUtilPNGIcon.arrayToX11BGRAImages(classResources, array, array2);
        }
        throw new UnsupportedOperationException("PNG decoder not implemented.");
    }
    
    static {
        Debug.initSingleton();
        final ClassLoader classLoader = PNGIcon.class.getClassLoader();
        avail = (DisplayImpl.isPNGUtilAvailable() && ReflectionUtil.isClassAvailable("jogamp.newt.driver.opengl.JoglUtilPNGIcon", classLoader));
    }
}
