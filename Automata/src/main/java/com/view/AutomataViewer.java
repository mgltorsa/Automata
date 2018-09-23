package com.view;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import manager.AutomataManager;

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
		createMachine(AutomataManager.MEALY);
	}

	public void clear() {
		machine = new AutomatonView(AutomatonView.MACHINE, this);
		split.setLeftComponent(machine);
		equivalent = new AutomatonView(AutomatonView.AUTOMATON, this);
		createMachine(AutomataManager.MEALY);
	}

	public boolean validateLanguage(String data) {
		return main.getAutomataManager().validateLanguage(data);
	}

	public boolean validateState(String id) {
		return main.getAutomataManager().existState(id);
	}

	public void createMachine(String type) {
		main.getAutomataManager().createMachine(type);
	}

	public void removeState(String id) {
		main.getAutomataManager().removeState(id);
	}

	public void addToAutomata(String... data) {
		main.getAutomataManager().addToAutomata(data);

	}

	public void setLanguageToAutomata(String... language) {
		if (language!=null &&language.length > 0) {
			main.getAutomataManager().setLanguage(language);
		}
	}
}
