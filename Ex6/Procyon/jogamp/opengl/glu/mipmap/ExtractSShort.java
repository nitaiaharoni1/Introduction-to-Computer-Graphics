// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.opengl.glu.mipmap;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

public class ExtractSShort implements ExtractPrimitive
{
    @Override
    public double extract(final boolean b, final ByteBuffer byteBuffer) {
        short n;
        if (b) {
            n = Mipmap.GLU_SWAP_2_BYTES(byteBuffer.getShort());
        }
        else {
            n = byteBuffer.getShort();
        }
        assert n <= 32767;
        return n;
    }
    
    @Override
    public void shove(final double n, final int n2, final ByteBuffer byteBuffer) {
        assert 0.0 <= n && n < 32768.0;
        final ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
        shortBuffer.position(n2);
        shortBuffer.put((short)n);
    }
}