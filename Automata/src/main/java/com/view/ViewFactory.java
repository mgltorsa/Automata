package com.view;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public final class ViewFactory {
	
	public final static Color FrameBackground = new Color(255, 160, 122);
	public final static Color FrameForeground = new Color(89, 63, 98);
	public final static Color PaneBackground= new Color(250, 250, 250);
	public final static Color PaneForeground= Color.LIGHT_GRAY;
	public final static Color ButtonPaneBackgorund= new Color(250,250,250);
	public final static Color ButtonPanePressed= new Color(190,190,190);
	public final static String UploadIcon = "/img/upload.png";
	public final static String CreateIcon = "/img/machine.png";
	public final static String AboutIcon = "/img/information.png";


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
	
	/**Path without root directory*/
	public final static ImageIcon getIcon(String path) {
		
		String root = System.getProperty("user.dir");
		return new ImageIcon(root+path);
	}
}
