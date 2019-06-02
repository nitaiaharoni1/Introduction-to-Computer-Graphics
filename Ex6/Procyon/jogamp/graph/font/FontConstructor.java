// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font;

import com.jogamp.graph.font.Font;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface FontConstructor
{
    Font create(final File p0) throws IOException;
    
    Font create(final InputStream p0, final int p1) throws IOException;
}
