// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.menu;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.GridLayout;
import javax.swing.JOptionPane;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JPanel;
import javax.swing.JButton;
import edu.cg.menu.components.LogField;
import edu.cg.menu.components.RenderingParametersSelector;
import edu.cg.menu.components.ScenesCreator;
import edu.cg.menu.components.ScenesPicker;
import edu.cg.scene.Scene;
import edu.cg.Logger;
import javax.swing.JFrame;

public class MenuWindow extends JFrame implements Logger
{
    private Scene scene;
    private ScenesPicker scenesPicker;
    private ScenesCreator scenesCreator;
    private RenderingParametersSelector renderingParametersSelector;
    private LogField logField;
    private JButton btnRender;
    
    public MenuWindow() {
        this.scene = null;
        this.setTitle("Ex2: Ray Tracing Application");
        this.setDefaultCloseOperation(3);
        final JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        this.scenesPicker = new ScenesPicker(this);
        this.scenesCreator = new ScenesCreator(this, this.scenesPicker::open);
        (this.renderingParametersSelector = new RenderingParametersSelector()).add(this.scenesPicker);
        this.renderingParametersSelector.add(this.scenesCreator);
        this.renderingParametersSelector.initFields();
        (this.btnRender = new JButton("Render scene")).setEnabled(false);
        final int width;
        final int height;
        final double viewPlainWidth;
        BufferedImage img;
        String msg;
        this.btnRender.addActionListener(e -> {
            width = this.renderingParametersSelector.width();
            height = this.renderingParametersSelector.height();
            viewPlainWidth = this.renderingParametersSelector.viewPlainWidth();
            if (!this.renderingParametersSelector.readParameters(this.scene, this)) {
                return;
            }
            else {
                try {
                    img = this.scene.render(width, height, viewPlainWidth, this);
                    this.present(img);
                }
                catch (Exception ex) {
                    msg = "Couldn't render " + this.scene.getName() + System.lineSeparator() + "Exception message: " + ex.getMessage();
                    this.log(msg);
                    JOptionPane.showMessageDialog(this, msg, "Error", 0);
                }
                return;
            }
        });
        this.renderingParametersSelector.add(this.btnRender);
        this.logField = new LogField();
        final JPanel panel1 = new JPanel();
        panel1.add(this.renderingParametersSelector);
        contentPane.add(panel1, "Center");
        panel1.setLayout(new GridLayout(0, 1, 0, 0));
        panel1.add(this.logField);
        this.pack();
    }
    
    @Override
    public void setVisible(final boolean b) {
        super.setVisible(b);
        this.log("Application started.");
    }
    
    public void setScene(final Scene scene) {
        this.scene = scene;
        this.btnRender.setEnabled(true);
        this.renderingParametersSelector.writeParameters(scene);
        this.log(String.valueOf(scene.getName()) + " has been selected.");
    }
    
    public void present(final BufferedImage img) {
        new ImageWindow(img, this.scene.getName(), this).setVisible(true);
    }
    
    @Override
    public void log(final String s) {
        this.logField.log(s);
    }
}
