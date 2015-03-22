package opengl;

import opengl.resource.object.camera.IGLCamera;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.AWTGLCanvas;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.vector.Matrix4f;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class GLCanvas extends AWTGLCanvas {
	private static final long serialVersionUID = 7519333736764307525L;

	private IGLCamera camera;
	private Lock mutex;

    public abstract void init();
    public abstract void paint(Matrix4f transformationMatrix);

    private Color backgroundColor = new Color(230, 230, 230);

	public GLCanvas() throws LWJGLException {
		super(new PixelFormat(8, 0, 0, 4));
		this.mutex = new ReentrantLock();
	}

    public GLCanvas(GLCanvas canvas, PixelFormat pf) throws LWJGLException {
        super(pf);
        canvas.camera = camera;
    }

    public GLCanvas(PixelFormat pf) throws LWJGLException {
        super(pf);
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
		this.computeTransformationMatrix();
	}
	
	private void computeTransformationMatrix() {
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
        GL11.glClearColor((float)(1.*backgroundColor.getRed()/255), (float)(1.*backgroundColor.getGreen()/255), (float)(1.*backgroundColor.getBlue()/255), 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        this.mutex.lock();
        if (this.camera != null) {
            this.paint(this.camera.getTransformationMatrix());
        } else {
            this.paint(new Matrix4f());
        }
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

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
