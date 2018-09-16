package com.View;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import org.graphstream.ui.j2dviewer.J2DGraphRenderer;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.swingViewer.DefaultView;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import com.Automata.IAutomata;


public class AutomataViewer {

	private IAutomata automata;
	private Viewer viewer;
	private GraphicGraph automatWa;
	private View view;
	private List<ActionListener> listeners;
	public AutomataViewer() {
		
		listeners = new ArrayList<ActionListener>();
	}
	
	public void addActionListener(ActionListener...listeners) {
		for (int i = 0; i < listeners.length; i++) {
			this.listeners.add(listeners[i]);
		}
	}
	
	public ViewPanel setAutomata(IAutomata automata) {
		MultiGraph a = new MultiGraph("Graph");
		a.addNode("1");
		a.addNode("2");
		a.addEdge("edge1", "1", "2");
//		a.display();
		if(viewer==null) {
			viewer = new Viewer(a,Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
			viewer.enableAutoLayout();
			
		}
		
		ViewPanel view =new DefaultView(viewer, viewer.DEFAULT_VIEW_ID, new J2DGraphRenderer());
//		view.display(viewer.getGraphicGraph(), true);
//		repaint();

		return view;
	}

	
}
