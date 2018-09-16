package com.View;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import org.graphstream.ui.swingViewer.ViewPanel;

public class MainView extends JFrame implements ActionListener{

	private AutomataViewer viewer;
	public MainView() {
		this.setLayout(new BorderLayout());
		Button a = new Button("click");
		a.addActionListener(this);
		this.add(a, BorderLayout.WEST);
		
		this.pack();
		
		
	}
	public void actionPerformed(ActionEvent e) {
		viewer= new AutomataViewer();
		pack();
		viewer.setAutomata(null);
//		add(view,BorderLayout.CENTER);
		repaint();
		pack();
	}

	public static void main(String[] args) {
		new MainView().setVisible(true);
	}
}
