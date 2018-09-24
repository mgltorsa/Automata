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

import com.manager.AutomataManager;

@SuppressWarnings("serial")
public class MainView extends JFrame implements ActionListener {
	/**
	 * A command that indicate save action.
	 */
	public final String SAVE = "Guardar";
	/**
	 * A command that indicate create states machine action.
	 */
	public final String CREATE_SM = "Crear maquina de estados";
	/**
	 * A command that indicate create equivalent automaton action.
	 */
	public final String CREATE_AT = "Crear automata";
	/**
	 * The viewer where are the two automata views (view for states machine and equivalent automaton).
	 */
	private AutomataViewer viewer;
	/**
	 * An automata manager that provides functions to manage the states machine and automaton.
	 */
	private AutomataManager automataManager;
	/**
	 * An instance of this class (this class use Singleton pattern)
	 */
	private static MainView mainView;

	/**
	 * Main view with the automata viewer.
	 */
	private MainView() {
		setResizable(true);
		automataManager = new AutomataManager();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setMinimumSize(new Dimension(960, 700));
		setPreferredSize(new Dimension(960, 700));
		ViewFactory.createDefaultFrame(this);
		createMenuBar();
		pack();
	}

	/**
	 * Create a menu options for this view.
	 */
	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("Archivo");
		JMenuItem load = new JMenuItem("Cargar");
		load.setActionCommand(PerformerView.LOAD);
		load.addActionListener(this);
		JMenuItem save = new JMenuItem("Guardar");
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
		JMenuItem aboutUs = new JMenuItem("Acerca del programa");
		aboutUs.setActionCommand(PerformerView.ABOUT);
		aboutUs.addActionListener(this);
		about.add(aboutUs);
		menuBar.add(file);
		menuBar.add(create);
		menuBar.add(about);
		this.setJMenuBar(menuBar);
		getContentPane().setLayout(new BorderLayout());

		viewer = new AutomataViewer(this);
		getContentPane().add(viewer, BorderLayout.CENTER);

	}
	
	/**
	 * Get instance of this class.
	 * @return mainView, mainView with automata viewer.
	 */

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
			if (viewer != null) {
				int option = JOptionPane.showConfirmDialog(null,
						"Borrara todo lo ingresado en la maquina, ¿desea hacerlo?");
				if (option == JOptionPane.YES_OPTION) {
					viewer.clear();
				}
			}
		} else if (e.getActionCommand().equals(PerformerView.ABOUT)) {
			PerformerView.getInstance().showAboutPane();
		} else if (e.getActionCommand().equals(PerformerView.LOAD)) {
			load();
		} else if (e.getActionCommand().equals(SAVE)) {
			save();
		}

	}

	/**
	 * Load a file and show in automata viewer the correspond states machine view.
	 */
	public void load() {
		String path = PerformerView.getInstance().showLoadDialog();
		if(path!=null) {
			try {
				automataManager.load(path);
				viewer.loadMachine();
				PerformerView.getInstance().showCreateMachine();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Error al cargar el archivo", "Fail load", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	/**
	 * Save the current states machine in a file.
	 */
	private void save() {
		if (automataManager.canSerializeMachine()) {
			String path = PerformerView.getInstance().showSaveDialog();
			if(path!=null) {
				try {
					automataManager.serializeMachine(path);
					JOptionPane.showMessageDialog(this, "Se ha guardado el archivo", "Correcto!", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this, "No se logró gurdar el archivo", "Fail load", JOptionPane.INFORMATION_MESSAGE);
					e.printStackTrace();
				}
			}

		}

	}

	@Override
	public void dispose() {
		super.dispose();
		PerformerView.getInstance().setExtendedState(NORMAL);
	}

	/**
	 * Return the current automata manager.
	 * @return automataManager, manager that provides function to manipulate current autamata.
	 */
	public AutomataManager getAutomataManager() {
		return automataManager;
	}

}
