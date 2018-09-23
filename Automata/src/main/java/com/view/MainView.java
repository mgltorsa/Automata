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


import manager.AutomataManager;

@SuppressWarnings("serial")
public class MainView extends JFrame implements ActionListener {
	public final String LOAD = "Cargar";
	public final String SAVE = "Guardar";
	public final String CREATE_SM = "Crear maquina de estados";
	public final String CREATE_AT = "Crear automata";
	public final String ABOUT = "Acerca";

	private AutomataViewer viewer;
	private AutomataManager automataManager;
	private static MainView mainView;

	private MainView() {
		setResizable(true);
		automataManager= new AutomataManager();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setMinimumSize(new Dimension(960, 650));
		setPreferredSize(new Dimension(960, 650));
		ViewFactory.createDefaultFrame(this);
		createMenuBar();
		pack();
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
		getContentPane().setLayout(new BorderLayout());
		
		viewer = new AutomataViewer(this);
//		viewer.setBounds(0, 0, 574, 458);
		getContentPane().add(viewer,BorderLayout.CENTER);

	}

	public static MainView getInstance() {
		if (mainView == null) {
			mainView = new MainView();
		}
		return mainView;
	}

	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		this.setExtendedState(NORMAL);
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals(CREATE_SM)) {
			if(viewer!=null) {
				int option = JOptionPane.showConfirmDialog(null, "Borrara todo lo ingresado en la maquina, ¿desea hacerlo?");
				if(option == JOptionPane.YES_OPTION) {
					viewer.clear();
				}
			}
		} else if (e.getActionCommand().equals(ABOUT)) {
			
		}

	}

	@Override
	public void dispose() {
		super.dispose();
		PerformerView.getInstance().setExtendedState(NORMAL);
	}
	
	public AutomataManager getAutomataManager() {
		return automataManager;
	}
	



	
}
