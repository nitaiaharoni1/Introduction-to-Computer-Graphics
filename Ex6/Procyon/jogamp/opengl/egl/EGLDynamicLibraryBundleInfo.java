// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.egl;

import com.jogamp.common.os.AndroidVersion;
import com.jogamp.common.os.Platform;
import jogamp.common.os.PlatformPropsImpl;
import jogamp.opengl.GLDynamicLibraryBundleInfo;

import java.util.ArrayList;
import java.util.List;

public abstract class EGLDynamicLibraryBundleInfo extends GLDynamicLibraryBundleInfo
{
    private static final List<String> glueLibNames;
    
    @Override
    public final boolean shallLookupGlobal() {
        return Platform.OSType.ANDROID == PlatformPropsImpl.OS_TYPE;
    }
    
    @Override
    public final List<String> getToolGetProcAddressFuncNameList() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("eglGetProcAddress");
        return list;
    }
    
    @Override
    public final long toolGetProcAddress(final long n, final String s) {
        return EGLContext.eglGetProcAddress(n, s);
    }
    
    @Override
    public final boolean useToolGetProcAdressFirst(final String s) {
        return !AndroidVersion.isAvailable;
    }
    
    protected final List<String> getEGLLibNamesList() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("libEGL.so.1");
        list.add("libEGL.so");
        list.add("EGL");
        list.add("libEGL");
        return list;
    }
    
    @Override
    public final List<String> getGlueLibNames() {
        return EGLDynamicLibraryBundleInfo.glueLibNames;
    }
    
    static {
        (glueLibNames = new ArrayList<String>()).add("jogl_mobile");
    }
}
