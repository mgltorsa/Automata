package com.graphicManagers;

import java.awt.event.KeyEvent;

import org.graphstream.ui.view.util.DefaultShortcutManager;

import com.view.MainView;


public class KeyAction extends DefaultShortcutManager {

	protected MainView main;

	public void keyTyped(KeyEvent event) {
		if (event.getKeyChar() == 'R') {
			view.getCamera().resetView();
		} else if (event.getKeyChar() == 'a' || event.getKeyChar() == 'A') {
			if (main != null) {
				main.toFront();
			}
		}
		// else if( event.getKeyChar() == 'B' )
		// {
		// view.setModeFPS( ! view.getModeFPS() );
		// }
	}

	public void setMain(MainView main) {
		this.main = main;
	}
}
