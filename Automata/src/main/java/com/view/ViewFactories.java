package com.view;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



public enum ViewFactories {

	FrameBackground(255, 160, 122),
	FrameForeground(89, 63, 98),
	PaneBackground(79, 81, 140),
	PaneForeground(255, 204, 102),
	ButtonPaneBackgorund(144,122,214),
	ButtonPanePressed(87,74,130);
	
	
	private final Color color;
	private ViewFactories(int r,int g, int b) {
		
		color = new Color(r,g,b);
	}
	
	
	public Color getColor() {
		return color;
	}
	
	public final static void createDefaultFrame(JFrame frame) {
		frame.setBackground(ViewFactories.FrameBackground.getColor());
		frame.setForeground(ViewFactories.FrameForeground.getColor());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 50, 540, 360);
		JPanel contentPane = new JPanel();
		contentPane.setBackground(ViewFactories.PaneBackground.getColor());
		contentPane.setForeground(ViewFactories.PaneForeground.getColor());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		
	}
	public final static void createDefaultPane(JPanel panel) {
		
	}
			
}
