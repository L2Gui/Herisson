package opengl.resource.object;

import org.lwjgl.opengl.GL15;

public enum GLObjectUsage {
	STATIC(GL15.GL_STATIC_DRAW),
	DYNAMIC(GL15.GL_DYNAMIC_DRAW),
	STREAM(GL15.GL_STREAM_DRAW);
	
	private int usage;
	
	GLObjectUsage(int usage) {
		this.usage = usage;
	}
	
	public int getUsage() {
		return this.usage;
	}
}
