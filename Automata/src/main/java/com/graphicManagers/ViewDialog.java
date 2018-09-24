package com.graphicManagers;

import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JDialog;



@SuppressWarnings("serial")
public class ViewDialog extends JDialog {

	/**
	 * A JDialog that must be contains a graphic graph and someview of it.
	 * @param title
	 */

	public ViewDialog(String title) { 
		setTitle(title);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
	}

	/**
	 * Show this dialog on center of the screen, with specific size (width:840,height:640)
	 */
	public void showOnCenter() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		setBounds(new Rectangle(center.x, center.y, 840,640));
		setLocation(0, 0);
		setLocationRelativeTo(null);
		setVisible(true);
		this.toFront();
		
	}

	

}
