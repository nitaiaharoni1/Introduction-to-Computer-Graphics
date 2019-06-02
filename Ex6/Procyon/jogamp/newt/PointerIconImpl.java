// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt;

import com.jogamp.nativewindow.util.DimensionImmutable;
import com.jogamp.nativewindow.util.PixelFormat;
import com.jogamp.nativewindow.util.PixelRectangle;
import com.jogamp.nativewindow.util.PointImmutable;
import com.jogamp.newt.Display;

import java.nio.ByteBuffer;

public class PointerIconImpl implements Display.PointerIcon
{
    private final DisplayImpl display;
    private final PixelFormat pixelformat;
    private final DimensionImmutable size;
    private final ByteBuffer pixels;
    private final PointImmutable hotspot;
    private long handle;
    private int hashCode;
    private volatile boolean hashCodeComputed;
    
    public PointerIconImpl(final DisplayImpl display, final PixelFormat pixelformat, final DimensionImmutable size, final ByteBuffer pixels, final PointImmutable hotspot, final long handle) {
        this.hashCode = 0;
        this.hashCodeComputed = false;
        this.display = display;
        this.pixelformat = pixelformat;
        this.size = size;
        this.pixels = pixels;
        this.hotspot = hotspot;
        this.handle = handle;
    }
    
    public PointerIconImpl(final DisplayImpl display, final PixelRectangle pixelRectangle, final PointImmutable hotspot, final long handle) {
        this.hashCode = 0;
        this.hashCodeComputed = false;
        this.display = display;
        this.pixelformat = pixelRectangle.getPixelformat();
        this.size = pixelRectangle.getSize();
        this.pixels = pixelRectangle.getPixels();
        this.hotspot = hotspot;
        this.handle = handle;
    }
    
    @Override
    public int hashCode() {
        if (!this.hashCodeComputed) {
            synchronized (this) {
                if (!this.hashCodeComputed) {
                    final int n = 31 + this.display.getFQName().hashCode();
                    final int n2 = (n << 5) - n + this.pixelformat.hashCode();
                    final int n3 = (n2 << 5) - n2 + this.size.hashCode();
                    final int n4 = (n3 << 5) - n3 + this.getStride();
                    final int n5 = (n4 << 5) - n4 + (this.isGLOriented() ? 1 : 0);
                    final int n6 = (n5 << 5) - n5 + this.pixels.hashCode();
                    this.hashCode = (n6 << 5) - n6 + this.hotspot.hashCode();
                }
            }
        }
        return this.hashCode;
    }
    
    public final synchronized long getHandle() {
        return this.handle;
    }
    
    public final synchronized long validatedHandle() {
        synchronized (this.display.pointerIconList) {
            if (!this.display.pointerIconList.contains(this)) {
                this.display.pointerIconList.add(this);
            }
        }
        if (0L == this.handle) {
            try {
                return this.handle = this.display.createPointerIconImpl(this.pixelformat, this.size.getWidth(), this.size.getHeight(), this.pixels, this.hotspot.getX(), this.hotspot.getY());
            }
            catch (Exception ex) {
                ex.printStackTrace();
                return 0L;
            }
        }
        return this.handle;
    }
    
    @Override
    public final Display getDisplay() {
        return this.display;
    }
    
    @Override
    public final PixelFormat getPixelformat() {
        return this.pixelformat;
    }
    
    @Override
    public final ByteBuffer getPixels() {
        return this.pixels;
    }
    
    @Override
    public final synchronized boolean isValid() {
        return 0L != this.handle;
    }
    
    @Override
    public final synchronized boolean validate() {
        return 0L != this.handle || 0L != this.validatedHandle();
    }
    
    @Override
    public synchronized void destroy() {
        if (Display.DEBUG) {
            System.err.println("PointerIcon.destroy: " + this + ", " + this.display + ", " + Display.getThreadName());
        }
        if (0L != this.handle) {
            synchronized (this.display.pointerIconList) {
                this.display.pointerIconList.remove(this);
            }
            this.display.runOnEDTIfAvail(false, new Runnable() {
                @Override
                public void run() {
                    if (!PointerIconImpl.this.display.isNativeValidAsync()) {
                        PointerIconImpl.this.destroyOnEDT(PointerIconImpl.this.display.getHandle());
                    }
                }
            });
        }
    }
    
    synchronized void destroyOnEDT(final long n) {
        final long handle = this.handle;
        this.handle = 0L;
        try {
            this.display.destroyPointerIconImpl(n, handle);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public final DimensionImmutable getSize() {
        return this.size;
    }
    
    @Override
    public final int getStride() {
        return this.size.getWidth() * this.pixelformat.comp.bytesPerPixel();
    }
    
    @Override
    public final boolean isGLOriented() {
        return false;
    }
    
    @Override
    public final PointImmutable getHotspot() {
        return this.hotspot;
    }
    
    @Override
    public final String toString() {
        return "PointerIcon[obj 0x" + Integer.toHexString(super.hashCode()) + ", " + this.display.getFQName() + ", 0x" + Long.toHexString(this.handle) + ", " + this.pixelformat + ", " + this.size + ", " + this.hotspot + ", pixels " + this.pixels + "]";
    }
}
