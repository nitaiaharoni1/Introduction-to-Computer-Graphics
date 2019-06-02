// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.newt.driver.opengl;

import com.jogamp.common.nio.Buffers;
import com.jogamp.common.os.Platform;
import com.jogamp.common.util.IOUtil;
import com.jogamp.nativewindow.util.PixelFormat;
import com.jogamp.opengl.util.PNGPixelRect;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.nio.ByteBuffer;

public class JoglUtilPNGIcon
{
    public static ByteBuffer arrayToX11BGRAImages(final IOUtil.ClassResources classResources, final int[] array, final int[] array2) throws UnsupportedOperationException, InterruptedException, IOException, MalformedURLException {
        final PNGPixelRect[] array3 = new PNGPixelRect[classResources.resourceCount()];
        array[0] = 0;
        for (int i = 0; i < classResources.resourceCount(); ++i) {
            final URLConnection resolve = classResources.resolve(i);
            if (null != resolve) {
                final PNGPixelRect read = PNGPixelRect.read(resolve.getInputStream(), PixelFormat.BGRA8888, false, 0, false);
                final int n = 0;
                array[n] += 2 + read.getSize().getWidth() * read.getSize().getHeight();
                array3[i] = read;
            }
            else {
                array3[i] = null;
            }
        }
        if (0 == array[0]) {
            return null;
        }
        final boolean is64Bit = Platform.is64Bit();
        array2[0] = (is64Bit ? 8 : 4);
        final ByteBuffer directByteBuffer = Buffers.newDirectByteBuffer(array[0] * array2[0]);
        for (int j = 0; j < array3.length; ++j) {
            final PNGPixelRect pngPixelRect = array3[j];
            if (null != pngPixelRect) {
                final int width = pngPixelRect.getSize().getWidth();
                final int height = pngPixelRect.getSize().getHeight();
                if (is64Bit) {
                    directByteBuffer.putLong(width);
                    directByteBuffer.putLong(height);
                }
                else {
                    directByteBuffer.putInt(width);
                    directByteBuffer.putInt(height);
                }
                final ByteBuffer pixels = pngPixelRect.getPixels();
                final int stride = pngPixelRect.getStride();
                for (int k = 0; k < height; ++k) {
                    int n2 = k * stride;
                    for (int l = 0; l < width; ++l) {
                        final long n3 = (0xFFL & pixels.get(n2++)) | (0xFFL & pixels.get(n2++)) << 8 | (0xFFL & pixels.get(n2++)) << 16 | (0xFFL & pixels.get(n2++)) << 24;
                        if (is64Bit) {
                            directByteBuffer.putLong(n3);
                        }
                        else {
                            directByteBuffer.putInt((int)n3);
                        }
                    }
                }
            }
        }
        directByteBuffer.rewind();
        return directByteBuffer;
    }
}
