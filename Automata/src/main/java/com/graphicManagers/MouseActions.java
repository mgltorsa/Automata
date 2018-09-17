package com.graphicManagers;

import java.awt.Point;
import java.awt.event.MouseEvent;


import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.view.Camera;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.util.DefaultMouseManager;

import com.view.MainView;

public class MouseActions extends DefaultMouseManager {
	protected Point initPoint;
	protected Point lastPoint;
	protected Camera camera;
	private boolean selectMode;
	private String init;
	private String last;
	private MainView main;

	@Override
	public void init(GraphicGraph graph, View view) {
		// TODO Auto-generated method stub
		super.init(graph, view);

		camera = view.getCamera();
		selectMode = false;
	}

	public void mousePressed(MouseEvent event) {
		curElement = view.findNodeOrSpriteAt(event.getX(), event.getY());

		mouseButtonPressOnElement(curElement, event);
		view.beginSelectionAt(x1, y1);

	}

	public void mouseDragged(MouseEvent event) {
		if (curElement != null) {
			elementMoving(curElement, event);
		} else {
			// view.selectionGrowsAt(event.getX(), event.getY());
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
				// }
				initPoint = null;
				lastPoint = null;
			}

		}

	}

	private void moveToUp() {
		DefViewPanel def = (DefViewPanel) view;
		def.moveToUp();

	}

	private void moveToDown() {
		DefViewPanel def = (DefViewPanel) view;
		def.moveToDown();

	}

	private void moveToRight() {
		DefViewPanel def = (DefViewPanel) view;
		def.moveToRight();

	}

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

	

	public void setMain(MainView main) {
		this.main = main;
	}

	public void setSelectedMode(boolean selected) {
		this.selectMode = selected;
		init = null;
		last = null;
	}

}
