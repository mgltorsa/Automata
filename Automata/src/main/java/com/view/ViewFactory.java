package com.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public final class ViewFactory {

	public final static Color FrameBackground = new Color(255, 160, 122);
	public final static Color FrameForeground = new Color(89, 63, 98);
	public final static Color PaneBackground = new Color(250, 250, 250);
	public final static Color PaneForeground = Color.LIGHT_GRAY;
	public final static Color ButtonPaneBackgorund = new Color(250, 250, 250);
	public final static Color ButtonPanePressed = new Color(190, 190, 190);
	public final static String UploadIcon = "/img/upload.png";
	public final static String CreateIcon = "/img/machine.png";
	public final static String AboutIcon = "/img/information.png";
	public static Popup popup;

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

	public final static void createDefaultComponentPane(JComponent component) {
		component.setBackground(PaneBackground);
		component.setForeground(PaneForeground);
	}

	/** Path without root directory */
	public final static ImageIcon getIcon(String path) {

		String root = System.getProperty("user.dir");
		return new ImageIcon(root + path);
	}

	public static void showPopupMessage(JComponent component, String message) {
		
		popup = PopupFactory.getSharedInstance().getPopup(component, new JLabel(message),
				component.getLocationOnScreen().x + component.getWidth() / 2,
				component.getLocationOnScreen().y + component.getHeight());

		popup.show();

		Timer t = new Timer(1000, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				popup.hide();

			}
		});
		t.setRepeats(false);
		t.start();
	}
}
