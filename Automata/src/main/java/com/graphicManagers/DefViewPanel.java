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
import com.view.ViewDialog;




public class DefViewPanel extends ViewPanel implements WindowListener, ComponentListener {

	public DefViewPanel(String identifier) {
		super(identifier);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = -4489484861592064398L;

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

	private AutomataViewer main;

	/**
	 * Main class
	 */

	// Construction

	public DefViewPanel(Viewer viewer, String identifier, GraphRenderer renderer) {
		super(identifier);

		load(viewer, renderer);
	}

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
	// Access

	// Command

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
				frame = new ViewDialog("Routing");
				frame.setLayout(new BorderLayout());
				frame.add(this, BorderLayout.CENTER);
				frame.setSize(800, 600);
				frame.setLocationRelativeTo(null);
				// frame.setVisible(true);
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

	public void render(Graphics2D g) {
		renderer.render(g, getX(), getY(), getWidth(), getHeight());

		String screenshot = (String) graph.getLabel("ui.screenshot");

		if (screenshot != null) {
			graph.removeAttribute("ui.screenshot");
			renderer.screenshot(screenshot, getWidth(), getHeight());
		}
	}

	// Selection

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

	// Window Listener

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
			if (main != null) {
				main.closeGraphDialog();
			}
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
		// The feedback on the node positions is often off since not needed
		// and generating lots of events. We activate it here since the
		// movement of the node is decided by the viewer. This is one of the
		// only moment when the viewer really moves a node.
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

	public ViewDialog getFrame() {
		return frame;
	}

	public void moveToLeft() {
		if (getCamera() != null) {
			double delta = 0;
			delta = getCamera().getGraphDimension() * 0.01f;

			delta *= getCamera().getViewPercent();

			Point3 p = getCamera().getViewCenter();
			getCamera().setViewCenter(p.x - delta, p.y, 0);
		}
	}

	public void moveToRight() {
		if (getCamera() != null) {
			double delta = 0;

			delta = getCamera().getGraphDimension() * 0.01f;

			delta *= getCamera().getViewPercent();

			Point3 p = getCamera().getViewCenter();
			getCamera().setViewCenter(p.x + delta, p.y, 0);
		}
	}

	public void moveToDown() {
		if (getCamera() != null) {
			double delta = 0;

			delta = getCamera().getGraphDimension() * 0.01f;

			delta *= getCamera().getViewPercent();

			Point3 p = getCamera().getViewCenter();
			getCamera().setViewCenter(p.x, p.y - delta, 0);
		}
	}

	public void moveToUp() {
		if (getCamera() != null) {
			double delta = 0;

			delta = getCamera().getGraphDimension() * 0.01f;

			delta *= getCamera().getViewPercent();

			Point3 p = getCamera().getViewCenter();
			getCamera().setViewCenter(p.x, p.y + delta, 0);
		}
	}

	public void setMain(AutomataViewer main) {
		this.main = main;
		if (mouseClicks != null) {
			((MouseActions) mouseClicks).setMain(main);
		}

		if (shortcuts != null) {
			((KeyAction) shortcuts).setMain(main);
		}
	}

	public void setSelectedMode(boolean selected) {
		if (mouseClicks != null) {
			((MouseActions) mouseClicks).setSelectedMode(selected);
		}
	}

	public GraphicGraph getGraphicGraph() {
		return graph;
	}

}
