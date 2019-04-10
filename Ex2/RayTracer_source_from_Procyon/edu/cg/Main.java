// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg;

import edu.cg.menu.MenuWindow;
import java.io.File;

public class Main
{
    public static void main(final String[] args) {
        final File scenesDir = new File("scenes");
        if (!scenesDir.exists() && !scenesDir.mkdir()) {
            throw new RuntimeException("Couldn't create the folder scenes...");
        }
        final File imagesOutputDir = new File("outputs");
        if (!imagesOutputDir.exists() && !imagesOutputDir.mkdir()) {
            throw new RuntimeException("Couldn't create the folder outputs...");
        }
        final MenuWindow mw = new MenuWindow();
        mw.setVisible(true);
    }
}
