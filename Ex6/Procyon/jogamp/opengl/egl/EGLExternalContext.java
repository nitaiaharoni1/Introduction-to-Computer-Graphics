// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.egl;

import com.jogamp.nativewindow.AbstractGraphicsScreen;
import com.jogamp.opengl.GLException;
import jogamp.opengl.GLContextShareSet;

public class EGLExternalContext extends EGLContext
{
    public EGLExternalContext(final AbstractGraphicsScreen abstractGraphicsScreen) {
        super(null, null);
        GLContextShareSet.contextCreated(this);
        if (!this.setGLFunctionAvailability(false, 0, 0, 8, false, false)) {
            throw new InternalError("setGLFunctionAvailability !strictMatch failed");
        }
        this.getGLStateTracker().setEnabled(false);
    }
    
    @Override
    protected void releaseImpl() throws GLException {
    }
    
    @Override
    protected void destroyImpl() throws GLException {
    }
}
