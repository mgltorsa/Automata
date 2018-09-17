package com.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

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

	

}
