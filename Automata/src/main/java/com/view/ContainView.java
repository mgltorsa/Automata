package com.view;

import java.awt.BorderLayout;

import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import org.graphstream.ui.swingViewer.GraphRenderer;
import org.graphstream.ui.view.Viewer;

import com.graphicManagers.DefViewPanel;


@SuppressWarnings("serial")
public class ContainView extends DefViewPanel {

	private ViewDialog viewDialog;

	public ContainView(Viewer viewer, String identifier, GraphRenderer renderer) {
		super(viewer, identifier, renderer);

	}

	public void load(Viewer viewer, GraphRenderer renderer) {

		super.load(viewer, renderer);

	}

	@Override
	public void openInAFrame(boolean on) {
		if (on) {
			if (viewDialog == null) {
				viewDialog = new ViewDialog("automaton");
				viewDialog.setLayout(new BorderLayout());
				viewDialog.add(this, BorderLayout.CENTER);

				viewDialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
				viewDialog.addWindowListener(this);
				viewDialog.addComponentListener(this);
				viewDialog.addKeyListener(shortcuts);
			}
		} else {
			if (viewDialog != null) {
				viewDialog.removeComponentListener(this);
				viewDialog.removeWindowListener(this);
				viewDialog.removeKeyListener(shortcuts);
				viewDialog.remove(this);
				viewDialog.setVisible(false);
				viewDialog.dispose();
			}
		}
	}

	public void windowClosing(WindowEvent e) {
		graph.addAttribute("ui.viewClosed", getId());

		switch (viewer.getCloseFramePolicy()) {
		case CLOSE_VIEWER:
			viewer.removeView(getId());
			break;
		case HIDE_ONLY:
			if (viewDialog != null)
				viewDialog.setVisible(false);
			break;
		case EXIT:
			viewer.removeView(getId());
			viewDialog.dispose();

			break;
		default:
			throw new RuntimeException(String.format("The %s view is not up to date, do not know %s CloseFramePolicy.",
					getClass().getName(), viewer.getCloseFramePolicy()));
		}
	}

	public ViewDialog getFrame() {
		return viewDialog;
	}

}