package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class KeyAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	
	private KeyBinding keyState;
	private boolean set;
	
	public KeyAction(KeyBinding keyState, boolean set) {
		this.keyState = keyState;
		this.set = set;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.keyState.setState(this.set);
	}
}
