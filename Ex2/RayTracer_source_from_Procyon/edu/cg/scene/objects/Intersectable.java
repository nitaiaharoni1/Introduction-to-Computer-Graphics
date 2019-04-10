// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.scene.objects;

import edu.cg.algebra.Hit;
import edu.cg.algebra.Ray;

public interface Intersectable
{
    Hit intersect(final Ray p0);
}
