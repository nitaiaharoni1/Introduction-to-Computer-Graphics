// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.mac;

import java.io.DataInput;
import java.io.IOException;

public class ResourceData
{
    private final byte[] data;
    
    public ResourceData(final DataInput dataInput) throws IOException {
        dataInput.readFully(this.data = new byte[dataInput.readInt()]);
    }
    
    public byte[] getData() {
        return this.data;
    }
}
