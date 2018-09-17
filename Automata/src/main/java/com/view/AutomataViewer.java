package com.view;


import javax.swing.JDialog;
import javax.swing.JSplitPane;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.view.Viewer;

import com.graphicManagers.DefViewPanel;




@SuppressWarnings("serial")
public class AutomataViewer extends JSplitPane {
	

	private ViewDialog viewDialog;
	private MainView main;
	private AutomatonView stateMachine;
	private AutomatonView automaton;
	
	public AutomataViewer(MainView main) {
		super(JSplitPane.VERTICAL_SPLIT);
		this.main=main;
		stateMachine = new AutomatonView("Maquina de estado");
		automaton = new AutomatonView("Automata");
		
		this.setLeftComponent(stateMachine);
		this.setRightComponent(automaton);
	}
	
	public void showAutomatonDialog(ContainViewer containViewer) {
		ViewDialog auxDialog = null;		
		DefViewPanel containView = containViewer.addDefaultView(true);
		auxDialog = containView.getFrame();
		auxDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		if (viewDialog != null) {
			viewDialog.dispose();
		}
		if (auxDialog != null) {
			viewDialog = auxDialog;
			viewDialog.setAlwaysOnTop(false);			
			viewDialog.showOnCenter();			
		}
//		main.toFront();
	}

	public void closeGraphDialog() {
		if (viewDialog != null ) {
			viewDialog.dispose();
		}
		
	}

	public void test() {
		Graph a = new MultiGraph("uys");
		a.addNode("1");
		a.addNode("2");
		a.addEdge("a1", "1", "2");
		ContainViewer containViewer = new ContainViewer(a, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		containViewer.enableAutoLayout();
		showAutomatonDialog(containViewer);
		
	}
}
