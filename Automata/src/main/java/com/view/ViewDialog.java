package com.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;





@SuppressWarnings("serial")
public class ViewDialog extends JDialog {

	private JPanel contentPanel;

	public ViewDialog(String title) {
		contentPanel = new JPanel();
		setTitle(title);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		

	}

	public void showOnCenter() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		setBounds(new Rectangle(center.x, center.y, 640,480));
		setLocation(0, 0);
		setLocationRelativeTo(null);
		setVisible(true);
		this.toFront();
		
	}

	

}
