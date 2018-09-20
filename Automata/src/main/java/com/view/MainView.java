package com.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JDesktopPane;
import javax.swing.BoxLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;


@SuppressWarnings("serial")
public class MainView extends JFrame implements ActionListener {
	public final String LOAD = "Cargar";
	public final String SAVE = "Guardar";
	public final String CREATE_SM = "Crear maquina de estados";
	public final String CREATE_AT = "Crear automata";
	public final String ABOUT = "Acerca";

	private AutomataViewer viewer;
	private static MainView mainView;
	
	private MainView() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setMinimumSize(new Dimension(1080, 640));
		setPreferredSize(new Dimension(640, 480));
		ViewFactory.createDefaultFrame(this);
		createMenuBar();
	}

	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("Archivo");
		JMenuItem load = new JMenuItem(LOAD);
		load.setActionCommand(LOAD);
		load.addActionListener(this);
		JMenuItem save = new JMenuItem(SAVE);
		save.setActionCommand(SAVE);
		save.addActionListener(this);
		file.add(load);
		file.add(save);
		JMenu create = new JMenu("Crear");
		JMenuItem statesM = new JMenuItem(CREATE_SM);
		statesM.setActionCommand(CREATE_SM);
		statesM.addActionListener(this);
		JMenuItem autom = new JMenuItem(CREATE_AT);
		autom.setActionCommand(CREATE_AT);
		autom.addActionListener(this);
		create.add(statesM);
		JMenu about = new JMenu("Información");
		JMenuItem aboutUs = new JMenuItem("Acerca");
		aboutUs.setActionCommand(ABOUT);
		aboutUs.addActionListener(this);
		about.add(aboutUs);
		menuBar.add(file);
		menuBar.add(create);
		menuBar.add(about);
		this.setJMenuBar(menuBar);
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		getContentPane().add(splitPane);

	}
	
	public static MainView getInstance() {
		if(mainView ==null) {
			mainView= new MainView();
		}
		return mainView;
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals(CREATE_SM)) {
			if (viewer == null) {
				viewer = new AutomataViewer(this);
				getContentPane().add(viewer, BorderLayout.CENTER);
				revalidate();
			}
			else {
				int option = JOptionPane.showConfirmDialog(this, "¿Desea crear uno nuevo?");
				if(option == JOptionPane.YES_OPTION) {
					this.remove(viewer);
					viewer = new AutomataViewer(this);
					getContentPane().add(viewer, BorderLayout.CENTER);
					revalidate();
				}
			}
		}
		else if(e.getActionCommand().equals(ABOUT)) {
			if(viewer!=null) {
				viewer.test();
			}
		}

	}
	
	
	@Override
	public void dispose() {	
		super.dispose();
		PerformerView.getInstance().setExtendedState(NORMAL);
	}

	public static void main(String[] args) {
		MainView main = new MainView();
		main.setVisible(true);
	}

}
