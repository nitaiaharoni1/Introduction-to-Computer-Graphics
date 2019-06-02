// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast;

import com.jogamp.graph.font.Font;
import jogamp.graph.font.FontConstructor;
import jogamp.graph.font.typecast.ot.OTFontCollection;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class TypecastFontConstructor implements FontConstructor
{
    @Override
    public Font create(final File file) throws IOException {
        return new TypecastFont(OTFontCollection.create(file));
    }
    
    @Override
    public Font create(final InputStream inputStream, final int n) throws IOException {
        return new TypecastFont(OTFontCollection.create(inputStream, n));
    }
}
