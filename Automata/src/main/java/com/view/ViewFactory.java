package com.view;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public final class ViewFactory {
	
	public final static Color FrameBackground = new Color(255, 160, 122);
	public final static Color FrameForeground = new Color(89, 63, 98);
	public final static Color PaneBackground= new Color(79, 81, 140);
	public final static Color PaneForeground= new Color(255, 204, 102);
	public final static Color ButtonPaneBackgorund= new Color(144,122,214);
	public final static Color ButtonPanePressed= new Color(87,74,130);

	public final static void createDefaultFrame(JFrame frame) {
		frame.setBackground(FrameBackground);
		frame.setForeground(FrameForeground);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 50, 540, 360);
		JPanel contentPane = new JPanel();
		contentPane.setBackground(PaneBackground);
		contentPane.setForeground(PaneForeground);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		
	}
	public final static void createDefaultPane(JPanel panel) {
		
	}
}
