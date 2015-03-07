package opengl.utils;

import org.lwjgl.util.vector.Vector3f;

public class GLRay {
	private Vector3f position;
	private Vector3f direction;
	
	public GLRay(Vector3f position, Vector3f direction) {
		this.position = position;
		this.direction = direction;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getDirection() {
		return direction;
	}

	public void setDirection(Vector3f direction) {
		this.direction = direction;
	}
	
	
}
