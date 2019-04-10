// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg;

public class UnimplementedMethodException extends RuntimeException
{
    public UnimplementedMethodException(final String methodName) {
        super("Method " + methodName + " is not implemented.");
    }
}
