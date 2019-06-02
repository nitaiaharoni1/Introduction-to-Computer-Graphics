// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main
{
    static Point prevMouse;
    static int currentModel;
    static Frame frame;
    
    public static void main(final String[] args) {
        Main.frame = new JFrame();
        GLProfile.initSingleton();
        final GLProfile glp = GLProfile.get("GL2");
        final GLCapabilities caps = new GLCapabilities(glp);
        final GLJPanel canvas = new GLJPanel(caps);
        Main.frame.setSize(500, 500);
        Main.frame.setLayout(new BorderLayout());
        Main.frame.add(canvas, "Center");
        final NeedForSpeed game = new NeedForSpeed(canvas);
        canvas.addGLEventListener(game);
        Main.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                super.windowClosing(e);
                System.exit(1);
            }
        });
        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e) {
                final GameState gameState = game.getGameState();
                switch (e.getKeyCode()) {
                    case 38: {
                        gameState.updateAccelaration(GameState.AccelarationState.GAS);
                        break;
                    }
                    case 40: {
                        gameState.updateAccelaration(GameState.AccelarationState.BREAKS);
                        break;
                    }
                    case 37: {
                        gameState.updateSteering(GameState.SteeringState.LEFT);
                        break;
                    }
                    case 39: {
                        gameState.updateSteering(GameState.SteeringState.RIGHT);
                        break;
                    }
                    case 76: {
                        game.toggleNightMode();
                        break;
                    }
                }
                super.keyPressed(e);
            }
            
            @Override
            public void keyReleased(final KeyEvent e) {
                final GameState gameState = game.getGameState();
                switch (e.getKeyCode()) {
                    case 38:
                    case 40: {
                        gameState.updateAccelaration(GameState.AccelarationState.CRUISE);
                        break;
                    }
                    case 37:
                    case 39: {
                        gameState.updateSteering(GameState.SteeringState.STRAIGHT);
                        break;
                    }
                }
                super.keyPressed(e);
            }
            
            @Override
            public void keyTyped(final KeyEvent e) {
                super.keyTyped(e);
            }
        });
        canvas.setFocusable(true);
        canvas.requestFocus();
        Main.frame.setVisible(true);
        canvas.repaint();
    }
}
