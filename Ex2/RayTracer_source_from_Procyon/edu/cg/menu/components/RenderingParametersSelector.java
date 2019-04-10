// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.menu.components;

import javax.swing.JOptionPane;
import edu.cg.menu.MenuWindow;
import edu.cg.scene.Scene;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.CheckboxGroup;
import javax.swing.JLabel;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.GridLayout;
import javax.swing.border.Border;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import java.awt.Checkbox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;

public class RenderingParametersSelector extends JPanel
{
    private JFormattedTextField width;
    private JFormattedTextField height;
    private JFormattedTextField viewPlainWidth;
    private JFormattedTextField recursionLevel;
    private Checkbox x1;
    private Checkbox x2;
    private Checkbox x3;
    private Checkbox reflection;
    private Checkbox refraction;
    
    public RenderingParametersSelector() {
        this.setBorder(new EtchedBorder(1, null, null));
        this.setLayout(new GridLayout(0, 1, 0, 0));
    }
    
    public void initFields() {
        final JPanel panel1 = new JPanel();
        panel1.setBorder(new EtchedBorder(1, null, null));
        panel1.setLayout(new FlowLayout(1, 5, 5));
        this.add(panel1);
        this.width = this.addAndGetTextField(" Width:", 400, panel1);
        this.height = this.addAndGetTextField(" Height:", 400, panel1);
        this.viewPlainWidth = this.addAndGetTextField(" View Plain Width:", 4.0, panel1);
        final JLabel antiAliasingLabel = new JLabel("   Anti aliasing:");
        panel1.add(antiAliasingLabel);
        final CheckboxGroup group = new CheckboxGroup();
        final Font myFont = new Font("Monospaced", 1, 12);
        (this.x1 = new Checkbox("x1 ", group, true)).setFont(myFont);
        (this.x2 = new Checkbox("x2 ", group, false)).setFont(myFont);
        (this.x3 = new Checkbox("x3", group, false)).setFont(myFont);
        panel1.add(this.x1);
        panel1.add(this.x2);
        panel1.add(this.x3);
        final JPanel panel2 = new JPanel();
        panel2.setBorder(new EtchedBorder(1, null, null));
        panel2.setLayout(new FlowLayout(1, 5, 5));
        this.add(panel2);
        (this.recursionLevel = this.addAndGetTextField(" Max recursion level:", 1, panel2)).setColumns(2);
        panel2.add(new JLabel("   "));
        (this.reflection = new Checkbox("Render reflections  ")).setFont(myFont);
        (this.refraction = new Checkbox("Render refractions")).setFont(myFont);
        panel2.add(this.reflection);
        panel2.add(this.refraction);
    }
    
    private JFormattedTextField addAndGetTextField(final String label, final int value, final JPanel panel) {
        final JLabel jLabel = new JLabel(label);
        panel.add(jLabel);
        final JFormattedTextField tf = new JFormattedTextField((Object)value);
        tf.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        tf.setColumns(3);
        panel.add(tf);
        return tf;
    }
    
    private JFormattedTextField addAndGetTextField(final String label, final double value, final JPanel panel) {
        final JLabel jLabel = new JLabel(label);
        panel.add(jLabel);
        final JFormattedTextField tf = new JFormattedTextField(value);
        tf.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        tf.setColumns(3);
        panel.add(tf);
        return tf;
    }
    
    public int width() {
        final int ans = (int)this.width.getValue();
        if (ans <= 0) {
            throw new IllegalArgumentException("Width argument must be positive");
        }
        return ans;
    }
    
    public int height() {
        final int ans = (int)this.height.getValue();
        if (ans <= 0) {
            throw new IllegalArgumentException("Height argument must be positive");
        }
        return ans;
    }
    
    public double viewPlainWidth() {
        final double ans = (double)this.viewPlainWidth.getValue();
        if (ans <= 1.0E-5) {
            throw new IllegalArgumentException("View plain width argument must be positive");
        }
        return ans;
    }
    
    public void setWidth(final int width) {
        this.width.setValue(width);
    }
    
    public void setHeight(final int height) {
        this.height.setValue(height);
    }
    
    public void setViewPlainWidth(final double width) {
        this.viewPlainWidth.setValue(width);
    }
    
    public boolean readParameters(final Scene scene, final MenuWindow menuWindow) {
        try {
            final int recursionLevel = (int)this.recursionLevel.getValue();
            if (recursionLevel < 1 | recursionLevel > 10) {
                throw new RuntimeException();
            }
            scene.initMaxRecursionLevel(recursionLevel);
        }
        catch (Exception ex) {
            final String msg = "Recursion level must be an Integer between 1 to 10.";
            menuWindow.log(msg);
            JOptionPane.showMessageDialog(menuWindow, msg, "Error", 0);
            return false;
        }
        if (this.x1.getState()) {
            scene.initAntiAliasingFactor(1);
        }
        else if (this.x2.getState()) {
            scene.initAntiAliasingFactor(2);
        }
        else {
            scene.initAntiAliasingFactor(3);
        }
        scene.initRenderRefarctions(this.refraction.getState()).initRenderReflections(this.reflection.getState());
        return true;
    }
    
    public void writeParameters(final Scene scene) {
        switch (scene.getFactor()) {
            case 1: {
                this.x1.setState(true);
                break;
            }
            case 2: {
                this.x2.setState(true);
                break;
            }
            default: {
                this.x3.setState(true);
                break;
            }
        }
        this.recursionLevel.setValue(scene.getMaxRecursionLevel());
        this.refraction.setState(scene.getRenderRefarctions());
        this.reflection.setState(scene.getRenderReflections());
    }
}
