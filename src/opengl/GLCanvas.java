package opengl;

import java.awt.event.ComponentEvent;
import java.util.HashSet;
import java.util.Set;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.AWTGLCanvas;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class GLCanvas extends AWTGLCanvas {
	private static final long serialVersionUID = 7519333736764307525L;
	
	private Set<GLResource> uninitializedResources = new HashSet<GLResource>();
	private Set<GLTexturedObject> uninitializedObjects = new HashSet<GLTexturedObject>();
	private Set<GLTexturedObject> objects = new HashSet<GLTexturedObject>();
	
	private Matrix4f viewMatrix;
	private IGLCamera camera;
	
	public GLCanvas() throws LWJGLException {
		super();
		
		this.viewMatrix = new Matrix4f();
        this.camera = new GLPerspectiveCamera(super.getWidth(), super.getHeight(), 60.0f, 0.1f, 100.0f);
        
        this.viewMatrix.rotate((float) Math.toRadians(0.0f), new Vector3f(0.0f, 1.0f, 0.0f));
        this.viewMatrix.translate(new Vector3f(0.0f, 0.0f, -0.5f));
	}
	
	@Override
	public void initGL() {
		this.initializeResources();
		this.initializeObjects();
		this.resizeGLView();
	}
	
	@Override
	public void paintGL() {
		this.initializeResources();
		this.initializeObjects();
		
		GL11.glClearColor(0.4f, 0.6f, 0.9f, 1.0f);
    	GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    	
    	for (GLTexturedObject obj : this.objects) {
    		obj.render(this.camera.getProjectionMatrix(), this.viewMatrix);
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
			this.resizeGLView();
		} catch (LWJGLException e1) {
			e1.printStackTrace();
		} catch (IllegalStateException e1) {}
		
		super.componentResized(e);
	}
	
	public void add(GLTexturedObject obj) {
		this.uninitializedObjects.add(obj);
	}
	
	public void add(GLResource res) {
		this.uninitializedResources.add(res);
	}
	
	private void initializeObjects() {
		for (GLTexturedObject obj : this.uninitializedObjects) {
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
	
	private void resizeGLView() {
		GL11.glViewport(0, 0, getWidth(), getHeight());
		this.computeProjectionMatrix();
	}
	
	private void computeProjectionMatrix() {
		this.camera.updateViewport((float) super.getWidth(), (float) super.getHeight());
	}
}
