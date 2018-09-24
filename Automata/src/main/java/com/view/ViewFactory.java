package com.view;

import java.awt.Color;
import java.awt.Font;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.border.EmptyBorder;

public final class ViewFactory {

	/**
	 * Default frame background,
	 */
	public final static Color FrameBackground = new Color(255, 160, 122);
	/**
	 * Default frame foreground.
	 */
	public final static Color FrameForeground = new Color(89, 63, 98);
	/**
	 * Default panel background color.
	 */
	public final static Color PaneBackground = new Color(250, 250, 250);
	/**
	 * Default panel foreground color.
	 */
	public final static Color PaneForeground = Color.LIGHT_GRAY;
	/**
	 * Default ButtonPanel background colorr.
	 */
	public final static Color ButtonPanelBackgorund = new Color(250, 250, 250);
	/**
	 * Default ButtonPanel pressed color.
	 */
	public final static Color ButtonPanePressed = new Color(190, 190, 190);
	/**
	 * Default font to panels.
	 */
	public final static Font DEFAULT_FONT = new Font("Serif", Font.CENTER_BASELINE, 12);

	/**
	 * Default path of UploadIcon for panel buttons.
	 */
	public final static String UploadIcon = "/img/upload.png";
	/**
	 * Default path of CreateIcon for panel buttons.
	 */
	public final static String CreateIcon = "/img/machine.png";
	/**
	 * Default path of AboutIcon for panel buttons.
	 */
	public final static String AboutIcon = "/img/information.png";
	/**
	 * Main popup
	 */
	public static Popup popup;

	/**
	 * Create a default frame with default properties for this application.
	 * (Properties like PaneBackground constant)
	 * 
	 * @param frame,
	 *            JFrame where properties will be apply.
	 */
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
	/**
	 * Create a default Component with default properties for this application.
	 * 
	 * @param component,
	 *            component where properties will be apply.
	 */
	public final static void createDefaultComponentPane(JComponent component) {
		component.setBackground(PaneBackground);
		component.setForeground(PaneForeground);
	}

	/**
	 * Get an icon image of specific path.
	 * 
	 * @param path,
	 *            path without root directory of the icon to search in the root
	 *            application directory.
	 * @return icon, the icon find, in case of the path doesn't exist the method
	 *         will return null;
	 */
	public final static ImageIcon getIcon(String path) {

		String root = System.getProperty("user.dir");

		ImageIcon icon = null;
		if (new File(root + path).exists()) {
			icon = new ImageIcon(root + path);
		}
		return icon;
	}

	/**
	 * Show a popup message in a component for one second.
	 * 
	 * @param component,
	 *            component where the message will be show.
	 * @param message,
	 *            the message that will be show.
	 */
	public static void showPopupMessage(JComponent component, String message) {

		popup = PopupFactory.getSharedInstance().getPopup(component,
				new JLabel(message),
				component.getLocationOnScreen().x + component.getWidth() / 2,
				component.getLocationOnScreen().y + component.getHeight());

		new Thread(new Runnable() {

			public void run() {
				popup.show();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}
				popup.hide();
			}
		}).start();

	}
	
}
