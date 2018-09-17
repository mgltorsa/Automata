package com.graphicManagers;

import java.awt.event.KeyEvent;

import org.graphstream.ui.view.util.DefaultShortcutManager;

import com.view.AutomataViewer;


public class KeyAction extends DefaultShortcutManager {

	protected AutomataViewer main;

	public void keyTyped(KeyEvent event) {
		if (event.getKeyChar() == 'R') {
			view.getCamera().resetView();
		} else if (event.getKeyChar() == 'a' || event.getKeyChar() == 'A') {
			
		}
		// else if( event.getKeyChar() == 'B' )
		// {
		// view.setModeFPS( ! view.getModeFPS() );
		// }
	}

	public void setMain(AutomataViewer main) {
		this.main = main;
	}
}
