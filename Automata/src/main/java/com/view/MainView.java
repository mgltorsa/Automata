package com.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import org.graphstream.ui.swingViewer.ViewPanel;

public class MainView extends JFrame implements ActionListener{
	private AutomataViewer viewer;
	public MainView() {
		viewer = new AutomataViewer();
		this.add(viewer,BorderLayout.CENTER);		
	}
	public void actionPerformed(ActionEvent e) {
		
		
		
		
	}

	public static void main(String[] args) {
		MainView main = new MainView();
		main.setVisible(true);
	}
	public void closeGraphDialog() {
		// TODO Auto-generated method stub
		
	}
}
