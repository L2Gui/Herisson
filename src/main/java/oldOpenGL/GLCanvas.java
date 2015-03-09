package oldOpenGL;

import java.awt.event.ComponentEvent;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeMap;

import oldOpenGL.resource.GLShader;
import oldOpenGL.resource.IGLResource;
import oldOpenGL.resource.object.camera.IGLCamera;
import oldOpenGL.resource.object.drawable.IGLDrawable;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.AWTGLCanvas;
import org.lwjgl.opengl.GL11;

import com.sun.corba.se.impl.orbutil.concurrent.Mutex;

public class GLCanvas extends AWTGLCanvas {
	private static final long serialVersionUID = 7519333736764307525L;
	
	private Set<IGLResource> uninitializedResources = new HashSet<IGLResource>();
	private Map<Integer, IGLDrawable> uninitializedDrawables = new TreeMap<Integer, IGLDrawable>();
	private Map<Integer, IGLDrawable> drawables = new TreeMap<Integer, IGLDrawable>();

	private IGLCamera camera;
	private Mutex mutex;
	
	public GLCanvas() throws LWJGLException {
		super();
		this.mutex = new Mutex();
	}
	
	@Override
	public void initGL() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA); 
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		GLShader.InitDefaultShaders();
		this.initializeResources();
		this.initializeObjects();
		this.resizeGLView();
	}
	
	@Override
	public void paintGL() {
		this.initializeResources();
		this.initializeObjects();
		
		GL11.glClearColor(0.85f, 0.85f, 0.85f, 1.0f);
    	GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    	
    	try {
			this.mutex.acquire();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
    	
    	if (this.camera != null) {
    		for (Map.Entry<Integer, IGLDrawable> obj : this.drawables.entrySet()) {
        		obj.getValue().render(this.camera.getProjectionViewMatrix());
        	}
    	}
    	
    	this.mutex.release();
    	
		try {
			super.swapBuffers();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		super.repaint();
	}
	
	public void lockDraw() {
		try {
			this.mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void unlockDraw() {
		this.mutex.release();
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
	
	public void setCamera(IGLCamera camera) {
		this.camera = camera;
	}
	
	public void add(int priority, IGLDrawable obj) {
		this.uninitializedDrawables.put(priority, obj);
	}
	
	public void add(IGLResource res) {
		this.uninitializedResources.add(res);
	}
	
	private void initializeObjects() {
		if (this.camera != null && !this.camera.isInitialized()) {
			this.camera.init();
		}
		
		for (Map.Entry<Integer, IGLDrawable> obj : this.uninitializedDrawables.entrySet()) {
			obj.getValue().init();
			this.drawables.put(obj.getKey(), obj.getValue());
		}
		this.uninitializedDrawables.clear();
	}
	
	private void initializeResources() {
		for (IGLResource res : this.uninitializedResources) {
			res.init();
		}
		this.uninitializedResources.clear();
	}
	
	private void resizeGLView() {
		GL11.glViewport(0, 0, getWidth(), getHeight());
		this.computeProjectionMatrix();
	}
	
	private void computeProjectionMatrix() {
		if (this.camera != null) {
			this.camera.updateViewport((float) super.getWidth(), (float) super.getHeight());
		}
	}
}
