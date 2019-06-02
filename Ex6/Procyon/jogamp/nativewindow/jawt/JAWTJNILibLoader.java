// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.nativewindow.jawt;

import com.jogamp.common.jvm.JNILibLoaderBase;
import com.jogamp.nativewindow.NativeWindowFactory;
import jogamp.nativewindow.NWJNILibLoader;

import java.awt.*;
import java.security.AccessController;
import java.security.PrivilegedAction;

public class JAWTJNILibLoader extends NWJNILibLoader
{
    public static void initSingleton() {
    }
    
    static {
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                Toolkit.getDefaultToolkit();
                if (NativeWindowFactory.TYPE_MACOSX != NativeWindowFactory.getNativeWindowType(false)) {
                    try {
                        JNILibLoaderBase.loadLibrary("jawt", null, true, JAWTJNILibLoader.class.getClassLoader());
                    }
                    catch (Throwable t) {
                        if (JNILibLoaderBase.DEBUG) {
                            t.printStackTrace();
                        }
                    }
                }
                return null;
            }
        });
    }
}
