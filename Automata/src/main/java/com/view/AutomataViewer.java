package com.view;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Window.Type;
import java.util.HashMap;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.graphstream.graph.Graph;
import org.graphstream.ui.view.Viewer;

import com.graphicManagers.ContainViewer;
import com.graphicManagers.DefViewPanel;
import com.graphicManagers.ViewDialog;

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
		if (language != null && language.length > 0) {
			main.getAutomataManager().setLanguage(language);
		}
	}

	public void showGraphicOnDialog(String typeView) {
		Graph graph = null;
		if (typeView.equals(AutomatonView.MACHINE)) {
			graph = main.getAutomataManager().getMachineGraphicGraph();
			showGraphicGraph(graph, machine);

		} else {
			graph = main.getAutomataManager().getEquivalentGraphicGraph();
			showGraphicGraph(graph, equivalent);
		}

	}

	private void showGraphicGraph(Graph viewGraph, AutomatonView view) {
		ViewDialog auxDialog = null;

		ContainViewer containViewer = new ContainViewer(viewGraph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		containViewer.enableAutoLayout();

		DefViewPanel containView = containViewer.addDefaultView(true);
		containView.setMain(this);
		containViewer.setMain(this);
		auxDialog = containView.getFrame();

		auxDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		view.showGraphOnDialog(auxDialog);

	}

	public void setAutomatonName(String text) {
		if (text != null && !text.isEmpty()) {
			main.getAutomataManager().setName(text);
		}

	}

	public void generateEquivalent() {
		main.getAutomataManager().generateEquivalent();
		loadEquivalent();
	}

	public void closeGraphDialog() {

	}

	public void loadMachine() {
		load(AutomatonView.MACHINE);
	}

	private void load(String type) {
		HashMap<String, String> info = null;
		if (type.equals(AutomatonView.MACHINE)) {
			info = main.getAutomataManager().getDataMachine();
			machine.setDataAutomata(info);
		}else {
			info=main.getAutomataManager().getDataEquivalent();
			equivalent.setDataAutomata(info);
		}
		

	}

	public void loadEquivalent() {
		load(AutomatonView.AUTOMATON);
	}
}
