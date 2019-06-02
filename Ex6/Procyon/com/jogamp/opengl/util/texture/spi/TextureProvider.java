// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.opengl.util.texture.spi;

import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.texture.ImageType;
import com.jogamp.opengl.util.texture.TextureData;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public interface TextureProvider
{
    TextureData newTextureData(final GLProfile p0, final File p1, final int p2, final int p3, final boolean p4, final String p5) throws IOException;
    
    TextureData newTextureData(final GLProfile p0, final InputStream p1, final int p2, final int p3, final boolean p4, final String p5) throws IOException;
    
    TextureData newTextureData(final GLProfile p0, final URL p1, final int p2, final int p3, final boolean p4, final String p5) throws IOException;
    
    public interface SupportsImageTypes
    {
        ImageType[] getImageTypes();
    }
}
