// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.menu.components;

import java.awt.event.ActionEvent;
import com.google.gson.Gson;
import javax.swing.JOptionPane;
import edu.cg.scene.Scene;
import java.nio.file.Files;
import java.nio.file.Paths;
import edu.cg.menu.GsonMaker;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Container;
import javax.swing.BoxLayout;
import javax.swing.border.Border;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextField;
import edu.cg.menu.MenuWindow;
import javax.swing.JPanel;

public class ScenesPicker extends JPanel
{
    private MenuWindow menuWindow;
    private JTextField txtFilename;
    
    public ScenesPicker(final MenuWindow menuWindow) {
        this.menuWindow = menuWindow;
        this.setBorder(new EtchedBorder(1, null, null));
        this.setLayout(new BoxLayout(this, 0));
        (this.txtFilename = new JTextField()).addActionListener(e -> this.open(this.txtFilename.getText()));
        this.add(this.txtFilename);
        this.txtFilename.setColumns(40);
        final JButton btnBrowse = new JButton("Browse scene...");
        final JFileChooser fileChooser;
        final int ret;
        btnBrowse.addActionListener(e -> {
            fileChooser = new JFileChooser("scenes");
            ret = fileChooser.showOpenDialog(menuWindow);
            if (ret == 0) {
                this.open(fileChooser.getSelectedFile().getPath());
            }
            return;
        });
        this.add(btnBrowse);
    }
    
    public void open(final String filename) {
        this.txtFilename.setText(filename);
        try {
            final Gson gson = GsonMaker.getInstance();
            final String scene1Json = new String(Files.readAllBytes(Paths.get(filename, new String[0])));
            final Scene scene = gson.fromJson(scene1Json, Scene.class);
            this.menuWindow.setScene(scene);
        }
        catch (Exception e) {
            final String msg = "Can't open scene!";
            this.menuWindow.log(msg);
            JOptionPane.showMessageDialog(this.menuWindow, msg, "Error", 0);
        }
    }
}
