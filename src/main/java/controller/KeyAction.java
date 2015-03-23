package controller;

import opengl.resource.object.IGLObject;
import org.lwjgl.util.vector.Vector3f;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class KeyAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private Vector3f cameraTarget;
    private Vector3f translation;
	
	public KeyAction(Vector3f cameraTarget, Vector3f translation) {
		this.cameraTarget = cameraTarget;
		this.translation = translation;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Vector3f.add(this.cameraTarget, this.translation, this.cameraTarget);
	}
}
