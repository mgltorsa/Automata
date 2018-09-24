package com.graphicManagers;

import java.util.Iterator;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.stream.ProxyPipe;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.j2dviewer.J2DGraphRenderer;
import org.graphstream.ui.swingViewer.GraphRenderer;
import org.graphstream.ui.view.Viewer;

import com.graphicManagers.ContainView;
import com.view.AutomataViewer;

public class ContainViewer extends Viewer {

	/**
	 * A container with a dialog with a view of the graphic graph 
	 */
	protected ContainView containView;
	/**
	 * A view that contains view of graphic graph.
	 */
	protected DefViewPanel view;

	/**
	 * And instance of a main viewer of some graphic graphs
	 * @param graph, a graphic graph.
	 * @param threadingModel, indicates if the graphic graph is showing in another thread.
	 */
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
	
	/**
	 * Initialize this viewer's components and render the graphic graph.
	 * @param graph, the graphic graph.
	 */
	public void load(Graph graph) {

		graphInAnotherThread = false;
		init(new GraphicGraph(newGGId()), (ProxyPipe) null, graph);
		enableXYZfeedback(true);

	}

	/**
	 * Return a view of this viewer.
	 * @return view, a view that contains a graphic graph
	 */
	public DefViewPanel getView() {
		return view;
	}
	
	/**
	 * Set main listener for callbacks to main frame.
	 * @param main, main listener 
	 */
	public void setMain(AutomataViewer main) {
		if (view != null) {
			view.setMain(main);
		}
	}

	/**
	 * Return the graphic graph of this viewer.
	 * @return graphicGraph, if a graphic graph was rendered in this viewer retorn its instance.
	 */
	public GraphicGraph getViewGraph() {
		if (view != null) {
			return view.getGraphicGraph();
		} else {
			return null;
		}
	}

	/**
	 * Refresh the view of this viewer.
	 */
	public void refreshDefViewPane() {
		if (view != null) {
			view.display(graph, true);
		}
	}

	/**
	 * Set graphic graph with default attributes for this viewer.
	 */
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
