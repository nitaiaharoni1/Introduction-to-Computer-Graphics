// 
// Decompiled by Procyon v0.5.30
// 

package jogamp.graph.font.typecast.ot.table;

import java.io.DataInput;
import java.io.IOException;

public class CmapFormatUnknown extends CmapFormat
{
    protected CmapFormatUnknown(final int format, final DataInput dataInput) throws IOException {
        super(dataInput);
        this._format = format;
        dataInput.skipBytes(this._length - 4);
    }
    
    @Override
    public int getRangeCount() {
        return 0;
    }
    
    @Override
    public Range getRange(final int n) throws ArrayIndexOutOfBoundsException {
        throw new ArrayIndexOutOfBoundsException();
    }
    
    @Override
    public int mapCharCode(final int n) {
        return 0;
    }
}
