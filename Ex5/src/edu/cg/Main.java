package edu.cg;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import edu.cg.models.Car.F1Car;
import edu.cg.models.IRenderable;
import edu.cg.models.SkewedBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Main {

    // Incrementally add new models to the models array.
    //       When the program is started, the first model in the array is rendered.
    //       Later, pressing 'm' on the keyboard will render the next model in the array.
    static IRenderable[] models = {new F1Car(), new SkewedBox()};
    static Point prevMouse;
    static int currentModel;
    static Frame frame;

    /**
     * Create frame, canvas and viewer, and load the first model.
     *
     * @param args
     *            No arguments
     */
    public static void main(String[] args) {
        frame = new JFrame();
        // General OpenGL initialization
        GLProfile.initSingleton();
        GLProfile glp = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(glp);

        caps.setSampleBuffers(true);
        caps.setNumSamples(9);

        // Create viewer and initialize with first model
        final GLJPanel canvas = new GLJPanel(caps);
        final Viewer viewer = new Viewer(canvas);
        viewer.setModel(nextModel());

        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());
        frame.add(canvas, BorderLayout.CENTER);

        // Add event listeners
        canvas.addGLEventListener(viewer);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        canvas.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                switch(e.getKeyChar()) {

                    // Toggle wireframe mode
                    case 'p':
                    case KeyEvent.VK_P:
                        viewer.toggleRenderMode();
                        break;

                    // Toggle axes
                    case 'a':
                    case KeyEvent.VK_A:
                        viewer.toggleAxes();
                        break;

                    // Toggle light spheres
                    case 'l':
                    case KeyEvent.VK_L:
                        viewer.toggleLightSpheres();
                        break;

                    // Show next model
                    case 'm':
                    case KeyEvent.VK_M:
                        viewer.setModel(nextModel());
                        break;

                    // exit
                    case KeyEvent.VK_ESCAPE:
                        System.exit(0);
                        break; //should never reach this line;

                    default:
                        break;
                }

                canvas.repaint();
                super.keyTyped(e);
            }
        });

        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Let mouse drag affect trackball view
                viewer.trackball(prevMouse, e.getPoint());
                prevMouse = e.getPoint();
            }
        });

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                prevMouse = e.getPoint();
                viewer.startAnimation();
                super.mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                viewer.stopAnimation();
                super.mouseReleased(e);
            }
        });

        canvas.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                // Let mouse wheel affect zoom
                double rot = e.getWheelRotation();
                viewer.zoom(rot); //zoom out for negative rot, zoom in for positive rot.
                canvas.repaint();
            }
        });

        // Show frame
        canvas.setFocusable(true);
        canvas.requestFocus();
        frame.setVisible(true);
        canvas.repaint();
    }

    /**
     * Return the next model in the array
     *
     * @return Renderable model
     */
    private static IRenderable nextModel() {
        IRenderable model = models[currentModel++];
        frame.setTitle("Exercise 5 - " + model.toString());
        currentModel = currentModel%models.length;

        return model;
    }
}
