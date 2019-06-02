// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow;

import com.jogamp.common.jvm.JNILibLoaderBase;
import com.jogamp.common.os.Platform;
import com.jogamp.common.util.cache.TempJarCache;

import java.security.AccessController;
import java.security.PrivilegedAction;

public class NWJNILibLoader extends JNILibLoaderBase
{
    public static boolean loadNativeWindow(final String s) {
        return AccessController.doPrivileged((PrivilegedAction<Boolean>)new PrivilegedAction<Boolean>() {
            @Override
            public Boolean run() {
                Platform.initSingleton();
                final String string = "nativewindow_" + s;
                if (TempJarCache.isInitialized() && null == TempJarCache.findLibrary(string)) {
                    JNILibLoaderBase.addNativeJarLibsJoglCfg(new Class[] { Debug.class });
                }
                return JNILibLoaderBase.loadLibrary(string, false, NWJNILibLoader.class.getClassLoader());
            }
        });
    }
}
