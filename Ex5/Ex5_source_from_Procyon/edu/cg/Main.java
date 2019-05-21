// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import com.jogamp.opengl.GLEventListener;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Component;
import com.jogamp.opengl.GLCapabilitiesImmutable;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import javax.swing.JFrame;
import edu.cg.models.Empty;
import edu.cg.models.SkewedBox;
import edu.cg.models.Car.Front;
import edu.cg.models.Car.Center;
import edu.cg.models.Car.Back;
import edu.cg.models.Car.Spolier;
import edu.cg.models.Car.PairOfWheels;
import edu.cg.models.Car.F1Car;
import java.awt.Frame;
import java.awt.Point;
import edu.cg.models.IRenderable;

public class Main
{
    static IRenderable[] models;
    static Point prevMouse;
    static int currentModel;
    static Frame frame;
    
    static {
        Main.models = new IRenderable[] { new F1Car(), new PairOfWheels(), new Spolier(), new Back(), new Center(), new Front(), new SkewedBox(), new Empty() };
    }
    
    public static void main(final String[] args) {
        Main.frame = new JFrame();
        GLProfile.initSingleton();
        final GLProfile glp = GLProfile.get("GL2");
        final GLCapabilities caps = new GLCapabilities(glp);
        caps.setSampleBuffers(true);
        caps.setNumSamples(9);
        final GLJPanel canvas = new GLJPanel((GLCapabilitiesImmutable)caps);
        final Viewer viewer = new Viewer((Component)canvas);
        viewer.setModel(nextModel());
        Main.frame.setSize(500, 500);
        Main.frame.setLayout(new BorderLayout());
        Main.frame.add((Component)canvas, "Center");
        canvas.addGLEventListener((GLEventListener)viewer);
        Main.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                System.exit(0);
            }
        });
        canvas.addKeyListener((KeyListener)new KeyAdapter() {
            @Override
            public void keyTyped(final KeyEvent e) {
                switch (e.getKeyChar()) {
                    case 'P':
                    case 'p': {
                        viewer.toggleRenderMode();
                        break;
                    }
                    case 'A':
                    case 'a': {
                        viewer.toggleAxes();
                        break;
                    }
                    case 'L':
                    case 'l': {
                        viewer.toggleLightSpheres();
                        break;
                    }
                    case 'M':
                    case 'm': {
                        viewer.setModel(Main.nextModel());
                        break;
                    }
                    case '\u001b': {
                        System.exit(0);
                        break;
                    }
                }
                canvas.repaint();
                super.keyTyped(e);
            }
        });
        canvas.addMouseMotionListener((MouseMotionListener)new MouseMotionAdapter() {
            @Override
            public void mouseDragged(final MouseEvent e) {
                viewer.trackball(Main.prevMouse, e.getPoint());
                Main.prevMouse = e.getPoint();
            }
        });
        canvas.addMouseListener((MouseListener)new MouseAdapter() {
            @Override
            public void mousePressed(final MouseEvent e) {
                Main.prevMouse = e.getPoint();
                viewer.startAnimation();
                super.mousePressed(e);
            }
            
            @Override
            public void mouseReleased(final MouseEvent e) {
                viewer.stopAnimation();
                super.mouseReleased(e);
            }
        });
        canvas.addMouseWheelListener((MouseWheelListener)new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(final MouseWheelEvent e) {
                final double rot = e.getWheelRotation();
                viewer.zoom(rot);
                canvas.repaint();
            }
        });
        canvas.setFocusable(true);
        canvas.requestFocus();
        Main.frame.setVisible(true);
        canvas.repaint();
    }
    
    private static IRenderable nextModel() {
        final IRenderable model = Main.models[Main.currentModel++];
        Main.frame.setTitle("Exercise 5 - " + model.toString());
        Main.currentModel %= Main.models.length;
        return model;
    }
}
