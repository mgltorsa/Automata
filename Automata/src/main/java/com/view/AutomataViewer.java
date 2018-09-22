package com.view;

import java.awt.Button;
import java.awt.Dimension;

import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.view.Viewer;

import com.graphicManagers.DefViewPanel;

@SuppressWarnings("serial")
public class AutomataViewer extends JTabbedPane {

	public final static int STATE_MACHINE_VIEWER = 0;
	public final static int AUTOMATON_VIEWER = 1;

	private ViewDialog viewDialog;
	private MainView main;
	private AutomatonView automaton;
	private JDesktopPane graphicAutomaton;

	public AutomataViewer(MainView main, int type) {
		super(JTabbedPane.TOP);
		this.main = main;
		String name = "Automata";
		automaton = new AutomatonView();
		if (type == STATE_MACHINE_VIEWER) {
			name="Maquina de estados";
			automaton.setStateMachineView();
		}else {
			automaton.setAutomatonView();
		}
		automaton.setTitle(name);
		JScrollPane scroll = new JScrollPane(automaton);
		scroll.setAutoscrolls(true);
		automaton.setRoot(this);
		graphicAutomaton= new JDesktopPane();
		graphicAutomaton.setSize(new Dimension(400,400));
		this.add(name, scroll);
		this.add(name+": grafico", graphicAutomaton);
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
		main.toFront();
	}

	public void closeGraphDialog() {
		if (viewDialog != null) {
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
