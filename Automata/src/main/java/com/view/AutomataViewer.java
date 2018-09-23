package com.view;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

@SuppressWarnings("serial")
public class AutomataViewer extends JPanel {

	private AutomatonView machine;
	private AutomatonView equivalent;
	private JSplitPane split;
	private MainView main;

	public AutomataViewer(MainView main) {
		this.main = main;
		setSize(new Dimension(860, 819));
		split = new JSplitPane();
		ViewFactory.createDefaultComponentPane(split);
		ViewFactory.createDefaultComponentPane(this);
		machine = new AutomatonView(AutomatonView.MACHINE, this);
		equivalent = new AutomatonView(AutomatonView.AUTOMATON, this);
		split.setLeftComponent(machine);
		// DEFAULT SIZE IS 10
		split.setDividerSize(0);
		split.setRightComponent(null);

		add(split);
		createMachine(AutomatonView.MEALY);
	}

	public void clear() {
		machine = new AutomatonView(AutomatonView.MACHINE, this);
		split.setLeftComponent(machine);
		equivalent = new AutomatonView(AutomatonView.AUTOMATON, this);
		createMachine(AutomatonView.MEALY);
	}

	public boolean validateLanguage(String data) {
		return main.getMachine().getLanguage().contains(data);
	}

	public boolean validateState(String data) {
		return main.getMachine().getState(data) != null;
	}

	public void createMachine(String type) {
		main.createMachine(type);
	}

	public void addState(boolean isMoore, String id) {
		main.getMachine().addState(id);
		if(isMoore) {
			main.getMachine().getState(id);
		}
	}
	

	public void addTransition(String... data) {

	}
}
