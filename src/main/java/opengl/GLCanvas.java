package opengl;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import opengl.resource.GLShader;
import opengl.resource.IGLResource;
import opengl.resource.object.camera.IGLCamera;
import opengl.resource.object.drawable.IGLDrawable;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.AWTGLCanvas;
import org.lwjgl.opengl.GL11;

import com.sun.corba.se.impl.orbutil.concurrent.Mutex;
import org.lwjgl.util.vector.Matrix4f;

public abstract class GLCanvas extends AWTGLCanvas {
	private static final long serialVersionUID = 7519333736764307525L;

	private IGLCamera camera;
	private Lock mutex;

    public abstract void init();
    public abstract void paint(Matrix4f projectionViewMatrix);

	public GLCanvas() throws LWJGLException {
		super();
		this.mutex = new ReentrantLock();
	}

    public void setCamera(IGLCamera camera) {
		this.camera = camera;
	}

    public void lockDraw() {
        this.mutex.lock();
    }

    public void unlockDraw() {
        this.mutex.unlock();
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

    @Override
    public void initGL() {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        this.init();
        this.resizeGLView();
    }

    @Override
    public void paintGL() {
        GL11.glClearColor(0.85f, 0.85f, 0.85f, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        this.mutex.lock();
        this.paint(this.camera.getProjectionViewMatrix());
        this.mutex.unlock();

        try {
            // Double buffering ici !
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

    @Override
    public Dimension getPreferredSize() {
        return super.getParent().getSize();
    }
}
