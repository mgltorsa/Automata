package com.view;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class ButtonPanel extends JPanel implements MouseListener{

	protected int strokeSize = 1;
	/** Color of shadow */
	protected Color shadowColor = Color.black;
	/** Sets if it drops shadow */
	protected boolean shady = false;
	/** Sets if it has an High Quality view */
	protected boolean highQuality = true;
	/** Double values for Horizontal and Vertical radius of corner arcs */
	protected Dimension arcs = new Dimension(20, 20);
	/** Distance between shadow border and opaque panel border */
	protected int shadowGap = 5;
	/** The offset of shadow. */
	protected int shadowOffset = 4;
	/** The transparency value of shadow. ( 0 - 255) */
	protected int shadowAlpha = 150;
	/** Color when pane is pressed */
	protected Color clickColor;
	/** Pane's background color */
	protected Color backgroundColor;
	/** Message of pane button */
	protected JLabel lblIcon;
	/**Action listeners*/
	protected ArrayList<ActionListener> listeners;
	/**button command*/
	protected String command;
	/**is pressed*/
	protected boolean isPressed=false;

	public ButtonPanel() {
		super();
		setOpaque(false);
		
		listeners = new ArrayList<ActionListener>();
		clickColor = ViewFactory.ButtonPanePressed;
		backgroundColor = ViewFactory.ButtonPaneBackgorund;
		setFocusable(true);
		setLayout(new BorderLayout(0, 0));
		lblIcon = new JLabel();
		lblIcon.setHorizontalAlignment(JLabel.CENTER);
		add(lblIcon, BorderLayout.CENTER);
		addMouseListener(this);
	}

	public void setCommand(String command) {
		this.command=command;
	}
	
	public void setMessage(String message) {
		this.setToolTipText(message);
	}
	
	public void setIcon(ImageIcon image) {
		
		Icon icon = new ImageIcon(image.getImage().getScaledInstance( (int) (this.getWidth()*0.9), (int)(this.getHeight()*0.9), Image.SCALE_DEFAULT));
		this.lblIcon.setIcon(icon);
		
	}
	
	public void changeBackgroundColor(Color color) {
		backgroundColor=color;
	}
	
	
	
	public void changeClickColor(Color color) {
		clickColor=color;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int width = getWidth();
		int height = getHeight();
		int shadowGap = this.shadowGap;
		Color shadowColorA = new Color(shadowColor.getRed(), shadowColor.getGreen(), shadowColor.getBlue(),
				shadowAlpha);
		Graphics2D graphics = (Graphics2D) g;
		graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

		// Sets antialiasing if HQ.
		if (highQuality) {
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}

		// Draws shadow borders if any.
		if (shady) {
			graphics.setColor(shadowColorA);
			graphics.fillRoundRect(shadowOffset, // X position
					shadowOffset, // Y position
					width - strokeSize - shadowOffset, // width
					height - strokeSize - shadowOffset, // height
					arcs.width, arcs.height);// arc Dimension
		} else {
			shadowGap = 1;
		}

		// Draws the rounded opaque panel with borders.
		
		graphics.setColor(getBackground());
		graphics.fillRect(0, 0, width - shadowGap, height - shadowGap);
		if(isPressed) {
		Color c = ViewFactory.PaneForeground;
		graphics.setColor(new Color(c.getRed(),c.getGreen(),c.getBlue(),128));
		graphics.setStroke(new BasicStroke(strokeSize));
		graphics.drawRect(0, 0, width - shadowGap, height - shadowGap);
		}
		// Sets strokes to default
		graphics.setStroke(new BasicStroke());
	}

	public void addContainerListener(ActionListener... listeners) {
		for(ActionListener listener: listeners) {
			this.listeners.add(listener);
		}		
	}
	
	public void callListeners() {
		for (ActionListener listener : listeners) {
			ActionEvent e = new ActionEvent(this, 0, command);
			listener.actionPerformed(e);
		}
	}

	public void mouseClicked(MouseEvent e) {};

	public void mouseEntered(MouseEvent e) {isPressed=true; repaint();};

	public void mouseExited(MouseEvent e) {isPressed=false; repaint();};

	public void mousePressed(MouseEvent e) {
		setBackground(clickColor);			
	}

	public void mouseReleased(MouseEvent e) {
		setBackground(backgroundColor);
		callListeners();		
	}

}
