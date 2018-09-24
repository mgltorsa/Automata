package com.graphicManagers;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Collection;

import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.swingViewer.GraphRenderer;
import org.graphstream.ui.swingViewer.LayerRenderer;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Camera;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.util.MouseManager;
import org.graphstream.ui.view.util.ShortcutManager;

import com.view.AutomataViewer;

@SuppressWarnings("serial")
public class DefViewPanel extends ViewPanel implements WindowListener, ComponentListener {

	/**
	 * A view on a graphic graph.
	 * <p>
	 * <p>
	 * Basically a view is a Swing panel where a {@link org.graphstream.ui.swingViewer.GraphRenderer} renders the
	 * graphic graph. If you are in the Swing thread, you can change the view on the
	 * graphic graph using methods to translate, zoom and rotate the view.
	 * </p>
	 */
	public DefViewPanel(String identifier) {
		super(identifier);
	}
	/**
	 * Parent viewer.
	 */
	protected Viewer viewer;

	/**
	 * The graph to render, shortcut to the viewers reference.
	 */
	protected GraphicGraph graph;

	/**
	 * The (optional) frame.
	 */
	protected ViewDialog frame;

	/**
	 * Manager for events with the keyboard.
	 */
	protected ShortcutManager shortcuts;

	/**
	 * Manager for events with the mouse.
	 */
	protected MouseManager mouseClicks;

	/**
	 * The graph renderer.
	 */
	protected GraphRenderer renderer;

	/**
	 * The main listener for callback to main panels.
	 */
	private AutomataViewer main;

	/**
	 * Main class
	 */
	public DefViewPanel(Viewer viewer, String identifier, GraphRenderer renderer) {
		super(identifier);
		load(viewer, renderer);
	}
	
	/**
	 * Load a view on a graphic graph
	 * @param viewer. Viewer of the graphic graph
	 * @param renderer. Render of the graphic graph.
	 */

	public void load(Viewer viewer, GraphRenderer renderer) {
		this.viewer = viewer;
		this.graph = viewer.getGraphicGraph();
		this.renderer = renderer;
		setOpaque(false); 
		setDoubleBuffered(true);
		setMouseManager(null);
		setShortcutManager(null);
		this.renderer.open(graph, this);

		addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (e.getWheelRotation() == -1) {
					getCamera().setViewPercent(Math.max(0.0001f, getCamera().getViewPercent() * 0.9f));
				} else if (e.getWheelRotation() == 1) {
					getCamera().setViewPercent(getCamera().getViewPercent() * 1.1f);

				}
			}

		});

	}
	
	/**
	 * Get camera tath is watching the graphic graph.
	 */

	public Camera getCamera() {
		return renderer.getCamera();
	}
	
	public void display(GraphicGraph graph, boolean graphChanged) {
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		checkTitle();

		Graphics2D g2 = (Graphics2D) g;

		render(g2);
	}

	/**
	 * Check if frame!=null and set a default title
	 */
	protected void checkTitle() {
		if (frame != null) {
			String titleAttr = String.format("ui.%s.title", getId());
			String title = (String) graph.getLabel(titleAttr);

			if (title == null) {
				title = (String) graph.getLabel("ui.default.title");

				if (title == null)
					title = (String) graph.getLabel("ui.title");
			}

			if (title != null)
				frame.setTitle(title);
		}
	}

	public void close(GraphicGraph graph) {
		renderer.close();
		graph.addAttribute("ui.viewClosed", getId());

		removeKeyListener(shortcuts);
		shortcuts.release();
		mouseClicks.release();

		openInAFrame(false);
	}

	public void resizeFrame(int width, int height) {
		if (frame != null) {
			frame.setSize(width, height);
		}
	}

	public void openInAFrame(boolean on) {
		if (on) {
			if (frame == null) {
				frame = new ViewDialog("automaton");
				frame.setLayout(new BorderLayout());
				frame.add(this, BorderLayout.CENTER);
				frame.setSize(800, 600);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				frame.addWindowListener(this);
				frame.addComponentListener(this);
				frame.addKeyListener(shortcuts);
			}
		} else {
			if (frame != null) {
				frame.removeComponentListener(this);
				frame.removeWindowListener(this);
				frame.removeKeyListener(shortcuts);
				frame.remove(this);
				frame.setVisible(false);
				frame.dispose();
			}
		}
	}

	/**
	 * Render the graphic graph on this view.
	 * @param g. Graphics of this pane.
	 */
	public void render(Graphics2D g) {
		renderer.render(g, getX(), getY(), getWidth(), getHeight());

		String screenshot = (String) graph.getLabel("ui.screenshot");

		if (screenshot != null) {
			graph.removeAttribute("ui.screenshot");
			renderer.screenshot(screenshot, getWidth(), getHeight());
		}
	}

	public void beginSelectionAt(double x1, double y1) {
		renderer.beginSelectionAt(x1, y1);
		repaint();
	}

	public void selectionGrowsAt(double x, double y) {
		renderer.selectionGrowsAt(x, y);
		repaint();
	}

	public void endSelectionAt(double x2, double y2) {
		renderer.endSelectionAt(x2, y2);
		repaint();
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowClosing(WindowEvent e) {
		graph.addAttribute("ui.viewClosed", getId());

		switch (viewer.getCloseFramePolicy()) {
		case CLOSE_VIEWER:
			viewer.removeView(getId());
			break;
		case HIDE_ONLY:
			if (frame != null)
				frame.setVisible(false);
			break;
		case EXIT:
			frame.dispose();
		}

	}

	public void windowDeactivated(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowOpened(WindowEvent e) {
		graph.removeAttribute("ui.viewClosed");
	}

	public void componentHidden(ComponentEvent e) {
		repaint();
	}

	public void componentMoved(ComponentEvent e) {
		repaint();
	}

	public void componentResized(ComponentEvent e) {
		repaint();
	}

	public void componentShown(ComponentEvent e) {
		repaint();
	}

	// Methods deferred to the renderer

	public Collection<GraphicElement> allNodesOrSpritesIn(double x1, double y1, double x2, double y2) {
		return renderer.allNodesOrSpritesIn(x1, y1, x2, y2);
	}

	public GraphicElement findNodeOrSpriteAt(double x, double y) {
		return renderer.findNodeOrSpriteAt(x, y);
	}

	public void moveElementAtPx(GraphicElement element, double x, double y) {
		boolean on = graph.feedbackXYZ();
		graph.feedbackXYZ(true);
		renderer.moveElementAtPx(element, x, y);
		graph.feedbackXYZ(on);
	}

	public void freezeElement(GraphicElement element, boolean frozen) {
		if (frozen) {
			element.addAttribute("layout.frozen");
		} else {
			element.removeAttribute("layout.frozen");
		}
	}

	public void setBackLayerRenderer(LayerRenderer renderer) {
		this.renderer.setBackLayerRenderer(renderer);
		repaint();
	}

	public void setForeLayoutRenderer(LayerRenderer renderer) {
		this.renderer.setForeLayoutRenderer(renderer);
		repaint();
	}

	public void setMouseManager(MouseManager manager) {
		if (mouseClicks != null)
			mouseClicks.release();

		if (manager == null)
			manager = new MouseActions();


		manager.init(graph, this);

		mouseClicks = manager;
	}

	public void setShortcutManager(ShortcutManager manager) {
		if (shortcuts != null)
			shortcuts.release();

		if (manager == null)
			manager = new KeyAction();

		manager.init(graph, this);

		shortcuts = manager;
	}
	

	/**
	 * Return the frame Return the ViewDialog that contains this view (The graphic graph view)
	 * @return frame. frame with graphic graph view.
	 */
	public ViewDialog getFrame() {
		return frame;
	}
	
	/**
	 * Move Camera To left, is invoked by MouseDraggedEvent
	 */
	public void moveToLeft() {
		if (getCamera() != null) {
			double delta = 0;
			delta = getCamera().getGraphDimension() * 0.01f;

			delta *= getCamera().getViewPercent();

			Point3 p = getCamera().getViewCenter();
			getCamera().setViewCenter(p.x - delta, p.y, 0);
		}
	}
	/**
	 * Move Camera To right, is invoked by MouseDraggedEvent
	 */
	public void moveToRight() {
		if (getCamera() != null) {
			double delta = 0;

			delta = getCamera().getGraphDimension() * 0.01f;

			delta *= getCamera().getViewPercent();

			Point3 p = getCamera().getViewCenter();
			getCamera().setViewCenter(p.x + delta, p.y, 0);
		}
	}
	/**
	 * Move Camera To down, is invoked by MouseDraggedEvent
	 */
	public void moveToDown() {
		if (getCamera() != null) {
			double delta = 0;

			delta = getCamera().getGraphDimension() * 0.01f;

			delta *= getCamera().getViewPercent();

			Point3 p = getCamera().getViewCenter();
			getCamera().setViewCenter(p.x, p.y - delta, 0);
		}
	}
	/**
	 * Move Camera To up, is invoked by MouseDraggedEvent
	 */
	public void moveToUp() {
		if (getCamera() != null) {
			double delta = 0;

			delta = getCamera().getGraphDimension() * 0.01f;

			delta *= getCamera().getViewPercent();

			Point3 p = getCamera().getViewCenter();
			getCamera().setViewCenter(p.x, p.y + delta, 0);
		}
	}

	/**
	 * Set main listener for its comunications with main frame. 
	 * @param main. main!=null and main is instance of AutomataViewer
	 */
	public void setMain(AutomataViewer main) {
		this.main = main;
		if (mouseClicks != null) {
			((MouseActions) mouseClicks).setMain(main);
		}

		if (shortcuts != null) {
			((KeyAction) shortcuts).setMain(main);
		}
	}
	
	/**
	 * Return the graphic graph in this view.
	 * @return graph. The graphic graph.
	 */
	public GraphicGraph getGraphicGraph() {
		return graph;
	}

}
