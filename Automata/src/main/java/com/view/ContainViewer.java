package com.view;

import java.util.Iterator;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.stream.ProxyPipe;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.j2dviewer.J2DGraphRenderer;
import org.graphstream.ui.swingViewer.GraphRenderer;
import org.graphstream.ui.view.Viewer;

import com.graphicManagers.DefViewPanel;
import com.view.ContainView;

public class ContainViewer extends Viewer {

	protected ContainView containView;
	protected DefViewPanel view;

	public ContainViewer(Graph graph, ThreadingModel threadingModel) {
		super(graph, threadingModel);

	}

	public ContainView addView(String id, GraphRenderer renderer, boolean openInAFrame) {
		synchronized (views) {

			containView = new ContainView(this, id, renderer);
			addView(containView);
			containView.openInAFrame(true);

			return containView;
		}
	}

	@Override
	public DefViewPanel addDefaultView(boolean openInAFrame) {
		synchronized (views) {
			view = new DefViewPanel(this, DEFAULT_VIEW_ID, new J2DGraphRenderer());
			addView(view);

			if (openInAFrame)
				view.openInAFrame(true);

			return view;
		}
	}

	public void load(Graph graph) {

		graphInAnotherThread = false;
		init(new GraphicGraph(newGGId()), (ProxyPipe) null, graph);
		enableXYZfeedback(true);

	}

	public DefViewPanel getView() {
		return view;
	}

	public void setSelectedMode(boolean selected) {
		if (view != null) {
			view.setSelectedMode(selected);
		}
	}

	public void setMain(AutomataViewer main) {
		if (view != null) {
			view.setMain(main);
		}
	}

	public GraphicGraph getViewGraph() {
		if (view != null) {
			return view.getGraphicGraph();
		} else {
			return null;
		}
	}

	public void refreshDefViewPane() {
		if (view != null) {
			view.display(graph, true);
		}
	}

	public void defaultGraphicGraph() {
		if (view != null) {
			System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

			GraphicGraph viewGraph = view.getGraphicGraph();
			viewGraph.addAttribute("ui.stylesheet",
					"graph { fill-color: white ; } node { size: 40px; fill-color: rgb(133,207,255) ; stroke-mode: plain; "
							+ "stroke-width: 2px; stroke-color: #CCF; shadow-mode: gradient-radial; shadow-width: "
							+ "10px; shadow-color: white; shadow-offset: 0px; text-color: black; "
							+ "text-style: italic ; } edge "
							+ "{ fill-color: rgb(250,124,97) ; text-color: black; text-style: italic ; text-size: 12px ; size: 3px  ; }");

			Iterator<Node> nodes = viewGraph.getNodeIterator();
			while (nodes.hasNext()) {
				Node node = nodes.next();
				node.setAttribute("ui.style",
						"size: 40px; fill-color: rgb(133,207,255) ; stroke-mode: plain; "
								+ "stroke-width: 2px; stroke-color: #CCF; shadow-mode: gradient-radial; shadow-width: "
								+ "10px; shadow-color: white; shadow-offset: 0px; text-color: black; "
								+ "text-style: italic ;");
			}

			Iterator<Edge> edges = viewGraph.getEdgeIterator();
			while (edges.hasNext()) {
				Edge edge = edges.next();
				edge.addAttribute("ui.style",
						"fill-color: rgb(250,124,97) ; text-color: black; text-style: italic ; text-size: 12px ; size: 3px ;");
			}
		}
	}

}
