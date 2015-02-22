package opengl;

import org.lwjgl.util.vector.Vector3f;

public interface IGLObject {
	void setPosition(Vector3f pos);
	void setRotation(float angle, Vector3f axis);
	void setScale(Vector3f scale);
}
