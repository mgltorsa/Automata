package com.view;

import java.awt.Dimension;
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

/**
 * @author Miguel
 *
 */
@SuppressWarnings("serial")
public class AutomataViewer extends JPanel {

	/**
	 * An automaton view that contains an states machine.
	 */
	private AutomatonView machine;
	/**
	 * A view that contains the equivalent machine of the states machine.
	 */
	private AutomatonView equivalent;
	/**
	 * The content pain that contains machine and equivalent views.
	 */
	private JSplitPane split;
	/**
	 * Main listener or callback to a MainView frame.
	 */
	private MainView main;

	/**
	 * An automata viewer that contains two automata views, one for states
	 * machine views and other for its equivalent machine.
	 * 
	 * @param main.
	 *            Main listener or callback to Main view frame.
	 */
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

	/**
	 * Resets the views for states machine view and equivalent view.
	 */
	public void clear() {
		machine = new AutomatonView(AutomatonView.MACHINE, this);
		split.setLeftComponent(machine);
		equivalent = new AutomatonView(AutomatonView.AUTOMATON, this);
		createMachine(AutomataManager.MEALY);
	}

	/**
	 * Validate if in the actual states machine data (Stimulus) is a correct
	 * input for some state.
	 * 
	 * @param state,
	 *            state that can contains a transition with input equals to
	 *            data<br>
	 *            (if this occur validate alphabet returns false to indicates
	 *            data is invalid)<br>
	 * @param data,
	 *            the input that will be validate.
	 * @return isValid, if is true indicate that the input is valid, in other
	 *         case is invalid.
	 */
	public boolean validateAlphabet(String state, String data) {
		boolean belong = main.getAutomataManager().validateLanguage(data);
		boolean notRepeat = main.getAutomataManager().validateLanguage(state,
				data);
		boolean isValid = belong && notRepeat;
		return isValid;
	}

	/**
	 * Validate if exist an state.
	 * 
	 * @param id,
	 *            id of the state to will be validate.
	 * @return
	 */
	public boolean validateState(String id) {
		return main.getAutomataManager().existState(id);
	}

	/**
	 * Create a new states machine in the Automata manager.
	 * 
	 * @param type,
	 *            the type of automaton to create (Mealy or Moore machine)
	 */
	public void createMachine(String type) {
		main.getAutomataManager().createMachine(type);
	}

	/**
	 * Remove an state from current states machine.
	 * 
	 * @param id,
	 *            id of the states will be remove.
	 */
	public void removeState(String id) {
		main.getAutomataManager().removeState(id);
	}
	/**
	 * Add data row for current states machine.
	 * @param data, data format can be read in AutomataManager doc
	 */
	public void addToAutomata(String... data) {
		main.getAutomataManager().addToAutomata(data);

	}

	/**
	 * Set a language for current states machine.
	 * @param language, language that will be set in states machine.
	 */
	public void setLanguageToAutomata(String... language) {
		if (language != null && language.length > 0) {
			main.getAutomataManager().setLanguage(language);
		}
	}
	/**
	 * Show a graphic graph in a JDialog.
	 * @param typeView, indicate if the view is for an states machine or equivalent automaton.
	 */
	public void showGraphicOnDialog(String typeView) {
		System.out.println("test");
		Graph graph = null;
		if (typeView.equals(AutomatonView.MACHINE)) {
			graph = main.getAutomataManager().getMachineGraphicGraph();
			showGraphicGraph(graph, machine);

		} else {
			graph = main.getAutomataManager().getEquivalentGraphicGraph();
			showGraphicGraph(graph, equivalent);
		}

	}

	/**
	 * Show a graphic graph in JDialog with automaton view information.
	 * @param viewGraph, a graphic graph that shows the graphic automaton.
	 * @param view, a view that contains automaton information.
	 */
	private void showGraphicGraph(Graph viewGraph, AutomatonView view) {
		ViewDialog auxDialog = null;

		ContainViewer containViewer = new ContainViewer(viewGraph,
				Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		containViewer.enableAutoLayout();

		DefViewPanel containView = containViewer.addDefaultView(true);
		containView.setMain(this);
		containViewer.setMain(this);
		auxDialog = containView.getFrame();

		auxDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		view.showGraphOnDialog(auxDialog);

	}
	
	/**
	 * Set name for current states machine.
	 * @param text, the name that will be set to current states machine.
	 */

	public void setAutomatonName(String text) {
		if (text != null && !text.isEmpty()) {
			main.getAutomataManager().setName(text);
		}

	}
	/**
	 * Generate and load the equivalent automaton for the current states machine.
	 */

	public void generateEquivalent() {
		main.getAutomataManager().generateEquivalent();
		loadEquivalent();
	}
	
	/**
	 * Create a new states machine and show its AutomatonView.
	 */
	public void loadMachine() {
		load(AutomatonView.MACHINE);
	}
	
	/**
	 * Create a new states machine or automaton and shows its AutomatonViews.
	 * @param type, the type of automaton view that will be created.
	 */

	private void load(String type) {
		HashMap<String, String> info = null;
		if (type.equals(AutomatonView.MACHINE)) {
			info = main.getAutomataManager().getDataMachine();
			machine.setDataAutomata(info);
		} else {
			info = main.getAutomataManager().getDataEquivalent();
			equivalent.setDataAutomata(info);
			this.add(equivalent);
		}
	}

	/**
	 * Create an equivalent automaton for current states machine and show its AutomatonView.
	 */
	public void loadEquivalent() {
		load(AutomatonView.AUTOMATON);
	}
}
