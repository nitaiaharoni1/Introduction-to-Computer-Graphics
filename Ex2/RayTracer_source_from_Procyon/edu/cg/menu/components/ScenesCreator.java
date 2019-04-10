// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.menu.components;

import java.awt.event.ActionEvent;
import java.awt.ComponentOrientation;
import com.google.gson.Gson;
import java.lang.reflect.Method;
import javax.swing.JOptionPane;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.io.FileWriter;
import java.lang.reflect.Type;
import edu.cg.scene.Scene;
import edu.cg.menu.GsonMaker;
import edu.cg.Scenes;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.JLabel;
import java.awt.LayoutManager;
import java.awt.FlowLayout;
import javax.swing.border.Border;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import java.util.function.Consumer;
import edu.cg.menu.MenuWindow;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;

public class ScenesCreator extends JPanel
{
    private JFormattedTextField sceneNumTextField;
    
    public ScenesCreator(final MenuWindow menuWindow, final Consumer<String> sceneSelector) {
        this.setBorder(new EtchedBorder(1, null, null));
        this.setLayout(new FlowLayout(1, 5, 5));
        this.add(new JLabel("Scenes Creator:     "));
        this.sceneNumTextField = this.addAndGetTextField("      scene");
        this.add(new JLabel("            "));
        final String btnName = "Create scene";
        final JButton btnCreate = new JButton(btnName);
        int sceneNum;
        String sceneName;
        Class<Scenes> scenes;
        Method sceneMakerMethod;
        Gson gson;
        Object scene;
        String jsonStr;
        String sceneFileName;
        FileWriter writer;
        final Exception ex2;
        Exception e1;
        final RuntimeException ex3;
        final Exception ex4;
        Exception e2;
        final RuntimeException ex5;
        final RuntimeException ex6;
        final String s;
        String msg;
        btnCreate.addActionListener(e -> {
            try {
                if (!this.checkSceneNum()) {
                    throw new IllegalArgumentException("The scnene number must be a positive integer.");
                }
                else {
                    sceneNum = (int)this.sceneNumTextField.getValue();
                    sceneName = "scene" + sceneNum;
                    scenes = Scenes.class;
                    try {
                        sceneMakerMethod = scenes.getMethod(sceneName, (Class<?>[])new Class[0]);
                        gson = GsonMaker.getInstance();
                        scene = sceneMakerMethod.invoke(null, new Object[0]);
                        jsonStr = gson.toJson(scene, Scene.class);
                        sceneFileName = "scenes/" + sceneName + ".json";
                        writer = new FileWriter(sceneFileName);
                        writer.write(jsonStr);
                        writer.flush();
                        writer.close();
                        sceneSelector.accept(sceneFileName);
                    }
                    catch (NoSuchMethodException | SecurityException ex7) {
                        e1 = ex2;
                        new RuntimeException("The function: Scenes." + sceneName + "() doesn't exist");
                        throw ex3;
                    }
                    catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex8) {
                        e2 = ex4;
                        new RuntimeException("Unknon error while inviking:  Scenes." + sceneName + "()");
                        throw ex5;
                    }
                    catch (IOException e3) {
                        new RuntimeException("Couldn't create file: " + sceneName + ".json");
                        throw ex6;
                    }
                }
            }
            catch (Exception ex) {
                msg = "Error in " + s + "!" + System.lineSeparator() + ex.getMessage();
                menuWindow.log(msg);
                JOptionPane.showMessageDialog(menuWindow, msg, "Error", 0);
            }
            return;
        });
        this.add(btnCreate);
    }
    
    private JFormattedTextField addAndGetTextField(final String label) {
        final JLabel jLabel = new JLabel(label);
        this.add(jLabel);
        final JFormattedTextField tf = new JFormattedTextField((Object)1);
        tf.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        tf.setColumns(3);
        this.add(tf);
        return tf;
    }
    
    private boolean checkSceneNum() {
        try {
            return (int)this.sceneNumTextField.getValue() > 0;
        }
        catch (Exception e) {
            return false;
        }
    }
}
