// 
// Decompiled by Procyon v0.5.30
// 

package com.jogamp.common.net.asset;

import com.jogamp.common.net.AssetURLConnection;
import com.jogamp.common.net.AssetURLContext;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class Handler extends URLStreamHandler
{
    static final AssetURLContext localCL;
    
    @Override
    protected URLConnection openConnection(final URL url) throws IOException {
        final AssetURLConnection assetURLConnection = new AssetURLConnection(url, Handler.localCL);
        assetURLConnection.connect();
        return assetURLConnection;
    }
    
    static {
        localCL = new AssetURLContext() {
            @Override
            public ClassLoader getClassLoader() {
                return Handler.class.getClassLoader();
            }
        };
    }
}
