package com.view;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.Timer;
import javax.swing.ToolTipManager;
import javax.swing.border.TitledBorder;

public class AutomatonView extends JPanel implements ActionListener {
	JComboBox<Character> cbx;
	JComponent root;

	public AutomatonView(String title) {
		setBorder(new TitledBorder(null, title, TitledBorder.LEADING, TitledBorder.TOP,
				new Font("Tahoma", Font.BOLD, 16), null));
		setFocusable(true);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	public void setStateMachineView() {

		cbx = new JComboBox<Character>();
		cbx.setEditable(true);
		cbx.addItem('a');
		cbx.setFocusable(true);
		cbx.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					
					String text = (String) cbx.getEditor().getItem();
					final Popup p;
					if (text.length() > 1) {
						 p = PopupFactory.getSharedInstance().getPopup(cbx,
								new JLabel("¡Debe ingresar un caracter!"),
								cbx.getLocationOnScreen().x + cbx.getWidth() / 2,
								cbx.getLocationOnScreen().y + cbx.getHeight());
						 
						p.show();
						
					}else {
						cbx.addItem(text.charAt(0));
						p = PopupFactory.getSharedInstance().getPopup(cbx,
								new JLabel("¡Caracter agregado!"),
								cbx.getLocationOnScreen().x + cbx.getWidth() / 2,
								cbx.getLocationOnScreen().y + cbx.getHeight());
						p.show();
					}
					Timer t = new Timer(1000, new ActionListener() {

				        public void actionPerformed(ActionEvent e) {
				            p.hide();

				        }
				    });
				    t.setRepeats(false);
				    t.start();
				}
			}
		});

		add(cbx);
	}

	public void actionPerformed(ActionEvent e) {

	}

	public void setRoot(JComponent automataViewer) {
		this.root = automataViewer;

	}

}
