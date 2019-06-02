// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.texture.spi.awt;

import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.awt.AWTTextureData;
import com.jogamp.opengl.util.texture.spi.TextureProvider;
import jogamp.opengl.Debug;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class IIOTextureProvider implements TextureProvider
{
    private static final boolean DEBUG;
    
    @Override
    public TextureData newTextureData(final GLProfile glProfile, final File file, final int n, final int n2, final boolean b, final String s) throws IOException {
        final BufferedImage read = ImageIO.read(file);
        if (read == null) {
            return null;
        }
        if (IIOTextureProvider.DEBUG) {
            System.out.println("TextureIO.newTextureData(): BufferedImage type for " + file + " = " + read.getType());
        }
        return new AWTTextureData(glProfile, n, n2, b, read);
    }
    
    @Override
    public TextureData newTextureData(final GLProfile glProfile, final InputStream inputStream, final int n, final int n2, final boolean b, final String s) throws IOException {
        final BufferedImage read = ImageIO.read(inputStream);
        if (read == null) {
            return null;
        }
        if (IIOTextureProvider.DEBUG) {
            System.out.println("TextureIO.newTextureData(): BufferedImage type for stream = " + read.getType());
        }
        return new AWTTextureData(glProfile, n, n2, b, read);
    }
    
    @Override
    public TextureData newTextureData(final GLProfile glProfile, final URL url, final int n, final int n2, final boolean b, final String s) throws IOException {
        final InputStream openStream = url.openStream();
        try {
            return this.newTextureData(glProfile, openStream, n, n2, b, s);
        }
        finally {
            openStream.close();
        }
    }
    
    static {
        DEBUG = Debug.debug("TextureIO");
    }
}
