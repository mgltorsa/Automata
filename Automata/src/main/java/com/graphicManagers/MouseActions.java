package com.graphicManagers;

import java.awt.Point;
import java.awt.event.MouseEvent;

import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.view.Camera;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.util.DefaultMouseManager;

import com.view.AutomataViewer;

public class MouseActions extends DefaultMouseManager {
	/**
	 * Init point for an specific mouse pressed event.
	 */
	protected Point initPoint;
	/**
	 * Init point for an specific mouse pressed event.
	 */
	protected Point lastPoint;
	/**
	 * Camera that is watching the graphic graph.
	 */
	protected Camera camera;	
	/**
	 * main listener or callback for main panels.
	 */
	private AutomataViewer main;

	@Override
	public void init(GraphicGraph graph, View view) {
		super.init(graph, view);
		
		camera = view.getCamera();
	}

	public void mousePressed(MouseEvent event) {
		curElement = view.findNodeOrSpriteAt(event.getX(), event.getY());
		if (curElement != null) {
			mouseButtonPressOnElement(curElement, event);
		}
		view.beginSelectionAt(x1, y1);

	}

	public void mouseDragged(MouseEvent event) {
		if (curElement != null) {
			elementMoving(curElement, event);
		} else {
			if (initPoint == null) {
				initPoint = event.getPoint();
			} else {
				lastPoint = event.getPoint();

				if (lastPoint.y < initPoint.y) {
					moveToDown();
				}

				if (lastPoint.x < initPoint.x) {
					moveToRight();
				}
				if (lastPoint.y > initPoint.y) {
					moveToUp();
				}
				if (lastPoint.x > initPoint.x) {
					moveToLeft();
				}
				initPoint = null;
				lastPoint = null;
			}

		}

	}

	/**
	 * Move view of the graphic graph to up.
	 */
	private void moveToUp() {
		DefViewPanel def = (DefViewPanel) view;
		def.moveToUp();

	}
	/**
	 * Move view of the graphic graph to down.
	 */
	private void moveToDown() {
		DefViewPanel def = (DefViewPanel) view;
		def.moveToDown();

	}
	/**
	 * Move view of the graphic graph to right.
	 */
	private void moveToRight() {
		DefViewPanel def = (DefViewPanel) view;
		def.moveToRight();

	}
	/**
	 * Move view of the graphic graph to left.
	 */
	private void moveToLeft() {
		DefViewPanel def = (DefViewPanel) view;
		def.moveToLeft();

	}

	public void mouseReleased(MouseEvent event) {
		if (curElement != null) {
			mouseButtonReleaseOffElement(curElement, event);
			curElement = null;
		} else {
			float x2 = event.getX();
			float y2 = event.getY();
			float t;

			if (x1 > x2) {
				t = x1;
				x1 = x2;
				x2 = t;
			}
			if (y1 > y2) {
				t = y1;
				y1 = y2;
				y2 = t;
			}

			mouseButtonRelease(event, view.allNodesOrSpritesIn(x1, y1, x2, y2));
			view.endSelectionAt(x2, y2);
		}

		initPoint = null;
		lastPoint = null;
	}
	/**
	 * For set principal lister or main callback.
	 * @param main, an AutomataViewer that contains Automaton views
	 */
	public void setMain(AutomataViewer main) {
		this.main = main;
	}

	
}
