// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.menu;

import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.awt.image.RenderedImage;
import javax.imageio.ImageIO;
import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class ImageWindow extends JFrame
{
    private MenuWindow menuWindow;
    private BufferedImage img;
    
    public ImageWindow(final BufferedImage img, final String title, final MenuWindow menuWindow) {
        this.img = img;
        this.menuWindow = menuWindow;
        this.setTitle(title);
        final JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        final JButton btnSaveAs = new JButton("Save as...");
        final JFileChooser fileChooser2;
        final JFileChooser fileChooser;
        final File selectedFile;
        final int ret;
        btnSaveAs.addActionListener(e -> {
            fileChooser = (fileChooser2 = new JFileChooser("outputs"));
            new File(String.valueOf(title) + ".png");
            fileChooser2.setSelectedFile(selectedFile);
            ret = fileChooser.showSaveDialog(this);
            if (ret == 0) {
                this.save(fileChooser.getSelectedFile());
            }
            return;
        });
        contentPane.add(btnSaveAs, "North");
        final JPanel panelImage = new ImagePanel();
        contentPane.add(panelImage, "Center");
        this.pack();
    }
    
    private void save(final File file) {
        try {
            ImageIO.write(this.img, "png", file);
            this.menuWindow.log("File: " + file.getName() + ".png has been saved.");
        }
        catch (IOException e) {
            this.menuWindow.log("Failed to save image: " + this.getTitle());
            JOptionPane.showMessageDialog(this, "Can't save file!", "Error", 0);
        }
    }
    
    @Override
    public void setVisible(final boolean b) {
        super.setVisible(b);
        this.menuWindow.log("Image: " + this.getTitle() + " has been " + (b ? "presented." : "vanished."));
    }
    
    private class ImagePanel extends JPanel
    {
        public ImagePanel() {
            this.setPreferredSize(new Dimension(ImageWindow.this.img.getWidth(), ImageWindow.this.img.getHeight()));
        }
        
        @Override
        protected void paintComponent(final Graphics g) {
            g.drawImage(ImageWindow.this.img, 0, 0, null);
        }
    }
}
