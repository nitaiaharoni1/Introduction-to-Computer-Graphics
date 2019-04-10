package edu.cg.menu;

import edu.cg.Logger;
import edu.cg.menu.components.LogField;
import edu.cg.menu.components.RenderingParametersSelector;
import edu.cg.menu.components.ScenesCreator;
import edu.cg.menu.components.ScenesPicker;
import edu.cg.scene.Scene;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class MenuWindow extends JFrame implements Logger {
	//MARK: fields
	private Scene scene = null;
	
	//MARK: GUI fields
	private ScenesPicker scenesPicker;
	private ScenesCreator scenesCreator;
	private RenderingParametersSelector renderingParametersSelector;
	private LogField logField;
	private JButton btnRender;
	
	public MenuWindow() {
		super();
		
		setTitle("Ex2: Ray Tracing Application");
		//The following line makes sure that all application threads are terminated when this window is closed.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		scenesPicker = new ScenesPicker(this);
		scenesCreator = new ScenesCreator(this, scenesPicker::open);
		renderingParametersSelector = new RenderingParametersSelector();
		renderingParametersSelector.add(scenesPicker);
		renderingParametersSelector.add(scenesCreator);
		renderingParametersSelector.initFields();
		
		btnRender = new JButton("Render scene");
		btnRender.setEnabled(false);
		btnRender.addActionListener((e) -> {
			int width = renderingParametersSelector.width();
			int height = renderingParametersSelector.height();
			double viewPlainWidth = renderingParametersSelector.viewPlainWidth();
			
			if(!renderingParametersSelector.readParameters(scene, MenuWindow.this))
				return;
			
			try {
				BufferedImage img = scene.render(width, height, viewPlainWidth,MenuWindow.this);
				present(img);
			} catch(Exception ex) {
				String msg = "Couldn't render " + scene.getName() + System.lineSeparator() + 
						"Exception message: " + ex.getMessage();
				log(msg);
				JOptionPane.showMessageDialog(MenuWindow.this, msg, "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		renderingParametersSelector.add(btnRender);
		logField = new LogField();
		
		JPanel panel1 = new JPanel();
		panel1.add(renderingParametersSelector);
		
		contentPane.add(panel1, BorderLayout.CENTER);
		panel1.setLayout(new GridLayout(0, 1, 0, 0));
		
		panel1.add(logField);
		
		pack();
	}
	
	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		log("Application started.");
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
		btnRender.setEnabled(true);
		renderingParametersSelector.writeParameters(scene);
		log(scene.getName() + " has been selected.");
	}
	
	public void present(BufferedImage img) {
		new ImageWindow(img, scene.getName(), this).setVisible(true);
	}
	
	//MARK: Logger
	@Override
	public void log(String s) {
		logField.log(s);
	}
}