package com.view;


import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("serial")
public class PerformerView extends JFrame implements ActionListener{

	/**
	 * Command to create an states machine.
	 */
	public final static String CREATE="Crear maquina de estado";
	/**
	 * Command to load an file.
	 */
	public final static String LOAD="Cargar archivo";
	/**
	 * Command to show about pane.
	 */
	public final static String ABOUT="Sobre el programa (instrucciones y demas)";
	/**
	 * Main file chooser for save or load files.
	 */
	public final static JFileChooser fileChooser = new JFileChooser();
	/**
	 * Path of about file with instructions user.
	 */
	public final static String ABOUT_PATH = System.getProperty("user.dir")+"/doc/about.txt";
	/**
	 * Content pane of this Frame
	 */
	private JPanel contentPane;
	/**
	 * An instance of performer view (this class using Singleton pattern)
	 */
	private static PerformerView performerView;
	
	public static void main(String[] args) {
		try {
			PerformerView frame = PerformerView.getInstance();
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");			
			SwingUtilities.updateComponentTreeUI(frame);
			frame.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	/**
	 * A performer view where are users options.
	 */
	private PerformerView() {
		ViewFactory.createDefaultFrame(this);
		contentPane = (JPanel) this.getContentPane();
		this.setSize(520, 350);
		setResizable(false);
		createPanelButtons();
	}
	/**
	 * Return the instance of this performer view.
	 * @return performerView, the instance of this performer view.
	 */
	public static PerformerView getInstance() {
		if(performerView ==null) {
			performerView = new PerformerView();
		}
		return performerView;
	}

	/**
	 * Set default panel buttons to this view.
	 */
	private void createPanelButtons() {
		contentPane.setLayout(null);
		ButtonPanel createMachine = new ButtonPanel();
		createMachine.setBounds(38, 40, 151, 109);
		createMachine.setBackground(ViewFactory.ButtonPanelBackgorund);
		createMachine.setCommand(CREATE);
		createMachine.setMessage(CREATE);
		createMachine.setIcon(ViewFactory.getIcon(ViewFactory.CreateIcon));

		contentPane.add(createMachine);
		
		ButtonPanel instructions = new ButtonPanel();
		instructions.setBounds(175, 169, 151, 109);
		instructions.setBackground(ViewFactory.ButtonPanelBackgorund);
		instructions.setCommand(ABOUT);
		instructions.setMessage(ABOUT);
		instructions.setIcon(ViewFactory.getIcon(ViewFactory.AboutIcon));

		contentPane.add(instructions);
		
		ButtonPanel loadFile = new ButtonPanel();
		loadFile.setBounds(307, 40, 151, 109);
		loadFile.setBackground(ViewFactory.ButtonPanelBackgorund);
		loadFile.setCommand(LOAD);
		loadFile.setMessage(LOAD);
		loadFile.setIcon(ViewFactory.getIcon(ViewFactory.UploadIcon));

		contentPane.add(loadFile);
		addListeners(createMachine,loadFile,instructions);
		addFontToAll(ViewFactory.DEFAULT_FONT,createMachine,loadFile,instructions);
	}

	/**
	 * Add font to all panels in an array.
	 * @param font, font to will be apply in each panel.
	 * @param panels, panels where the font will be apply.
	 */
	private void addFontToAll(Font font, JPanel...panels) {
		for(JPanel panel : panels) {
			panel.setFont(font);
		}
		
	}

	/**
	 * Add this as listener for all panels.
	 * @param panels, panels where this as listener will be set.
	 */
	private void addListeners(ButtonPanel... panels) {
		for(ButtonPanel panel : panels) {
			panel.addContainerListener(this);
		}
		
	}

	@Override
	public void dispose() {
		super.dispose();
		System.exit(0);
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if(command.equals(CREATE)) {
			showCreateMachine();
		}else if(command.equals(LOAD)) {
			loadFile();
		}else if(command.equals(ABOUT)) {
			showAboutPane();
		}
	}

	/**
	 * Show about pane, where are the user instructions.
	 */
	public void showAboutPane() {
		try {
			Desktop.getDesktop().open(new File(ABOUT_PATH));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Ocurrio un error al abrir about.txt", "Error", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	/**
	 * Show load file dialog and show main view with the states machine loaded.
	 */
	private void loadFile() {		
		MainView.getInstance().load();
	}
	/**
	 * Show main view with the states machine loaded.
	 */
	public void showCreateMachine() {
		MainView.getInstance().setVisible(true);
		this.setExtendedState(ICONIFIED);
	}

	/**
	 * Show the save dialog and return the path selected.
	 * @return path, path selected by user, where file will be save.
	 */
	public String showSaveDialog() {
		String path=null;
		int option = fileChooser.showSaveDialog(null);
		if(option == JFileChooser.APPROVE_OPTION) {
			path = fileChooser.getSelectedFile().getPath();
		}
		
		return path;
		
	}

	/**
	 * Show load file dialog and return the path selected
	 * @return path, path selected by the user, where file will be load.
	 */
	public String showLoadDialog() {
		String path =null;
		int option = fileChooser.showOpenDialog(null);
		if(option==JFileChooser.APPROVE_OPTION) {
			path = fileChooser.getSelectedFile().getPath();
		}
		return path;
	}

	
}
