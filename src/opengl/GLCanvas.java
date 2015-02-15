package opengl;

import java.awt.event.ComponentEvent;
import java.util.HashSet;
import java.util.Set;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.AWTGLCanvas;
import org.lwjgl.opengl.GL11;

public class GLCanvas extends AWTGLCanvas {
	private static final long serialVersionUID = 7519333736764307525L;
	
	private Set<GLResource> uninitializedResources = new HashSet<GLResource>();
	private Set<GLObject> uninitializedObjects = new HashSet<GLObject>();
	private Set<GLObject> objects = new HashSet<GLObject>();

	public GLCanvas() throws LWJGLException {
		super();
	}
	
	@Override
	public void initGL() {
		this.initializeResources();
		this.initializeObjects();
		
		GL11.glViewport(0,0, getWidth(), getHeight());
	}
	
	@Override
	public void paintGL() {
		this.initializeResources();
		this.initializeObjects();
		
		GL11.glClearColor(0.4f, 0.6f, 0.9f, 1.0f);
    	GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    	
    	for (GLObject obj : this.objects) {
    		obj.render();
    	}
		
		try {
			super.swapBuffers();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		super.repaint();
	}
	
	@Override
	public void componentResized(ComponentEvent e) {
		try {
			super.makeCurrent();
			GL11.glViewport(0, 0, getWidth(), getHeight());
		} catch (LWJGLException e1) {
			e1.printStackTrace();
		} catch (IllegalStateException e1) {}
		
		super.componentResized(e);
	}
	
	public void add(GLObject obj) {
		this.uninitializedObjects.add(obj);
	}
	
	public void add(GLResource res) {
		this.uninitializedResources.add(res);
	}
	
	private void initializeObjects() {
		for (GLObject obj : this.uninitializedObjects) {
			obj.init();
			this.objects.add(obj);
		}
		this.uninitializedObjects.clear();
	}
	
	private void initializeResources() {
		for (GLResource res : this.uninitializedResources) {
			res.init();
		}
		this.uninitializedResources.clear();
	}
}
