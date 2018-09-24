package com.graphicManagers;

import java.awt.event.KeyEvent;

import org.graphstream.ui.view.util.DefaultShortcutManager;

import com.view.AutomataViewer;


public class KeyAction extends DefaultShortcutManager {

	/**
	 * Main listener for callback main panels.
	 */
	protected AutomataViewer main;

	/**
	 * Key events, if press R the view of Camera will reset.
	 */
	public void keyTyped(KeyEvent event) {
		if (event.getKeyChar() == 'R') {
			view.getCamera().resetView();
		}
	}

	/**
	 * For set principal lister or main callback.
	 * @param main, an AutomataViewer that contains Automaton views
	 */
	public void setMain(AutomataViewer main) {
		this.main = main;
	}
}
