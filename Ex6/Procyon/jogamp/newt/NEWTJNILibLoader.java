// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt;

import com.jogamp.common.jvm.JNILibLoaderBase;
import com.jogamp.common.os.Platform;
import com.jogamp.common.util.cache.TempJarCache;
import jogamp.nativewindow.Debug;

import java.security.AccessController;
import java.security.PrivilegedAction;

public class NEWTJNILibLoader extends JNILibLoaderBase
{
    public static boolean loadNEWT() {
        return AccessController.doPrivileged((PrivilegedAction<Boolean>)new PrivilegedAction<Boolean>() {
            @Override
            public Boolean run() {
                Platform.initSingleton();
                if (TempJarCache.isInitialized() && null == TempJarCache.findLibrary("newt")) {
                    JNILibLoaderBase.addNativeJarLibsJoglCfg(new Class[] { Debug.class, jogamp.newt.Debug.class });
                }
                return JNILibLoaderBase.loadLibrary("newt", false, NEWTJNILibLoader.class.getClassLoader());
            }
        });
    }
}
