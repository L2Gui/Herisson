package opengl;

import java.awt.event.ComponentEvent;
import java.util.HashSet;
import java.util.Set;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.AWTGLCanvas;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

public class GLCanvas extends AWTGLCanvas {
	private static final long serialVersionUID = 7519333736764307525L;
	
	private Set<GLResource> uninitializedResources = new HashSet<GLResource>();
	private Set<GLObject> uninitializedObjects = new HashSet<GLObject>();
	private Set<GLObject> objects = new HashSet<GLObject>();
	
	private Matrix4f projectionMatrix;

	public GLCanvas() throws LWJGLException {
		super();
		
		// Setup projection matrix
		this.projectionMatrix = new Matrix4f();
		float fieldOfView = 60f;
		float aspectRatio = (float) super.getWidth() / (float) super.getHeight();
		float near_plane = 0.1f;
		float far_plane = 100f;
		 
		float y_scale = (float) Math.tanh(Math.toRadians(fieldOfView / 2f));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = far_plane - near_plane;
		
		this.projectionMatrix.m00 = 1.0f;
		this.projectionMatrix.m11 = 1.0f;
		this.projectionMatrix.m22 = 1.0f;
		this.projectionMatrix.m33 = 1.0f;
		
		/*this.projectionMatrix.m00 = x_scale;
		this.projectionMatrix.m11 = y_scale;
		this.projectionMatrix.m22 = -((far_plane + near_plane) / frustum_length);
		this.projectionMatrix.m23 = -1;
		this.projectionMatrix.m32 = -((2 * near_plane * far_plane) / frustum_length);
		this.projectionMatrix.m33 = 0;*/
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
    		obj.render(this.projectionMatrix);
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
