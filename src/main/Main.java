package main;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import opengl.GLCanvas;
import opengl.GLObject;
import opengl.GLShader;
import opengl.GLTexture;
import opengl.GLTexturedVertex;

import org.lwjgl.LWJGLException;
 
public class Main {
	public JFrame frame;
	
    public static void main(String args[]) {
        Main l10 = new Main();
        l10.run();
    }
    
    public void run() {
    	try {
        	createWindow();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    	
    	int[] indices = {
			0, 1, 2,
			2, 3, 0
		};
		
		GLTexturedVertex v0 = new GLTexturedVertex(); 
		v0.setXYZ(-0.5f, 0.5f, 0); v0.setRGB(1, 0, 0); v0.setST(0, 0);
		GLTexturedVertex v1 = new GLTexturedVertex(); 
		v1.setXYZ(-0.5f, -0.5f, 0); v1.setRGB(0, 1, 0); v1.setST(0, 1);
		GLTexturedVertex v2 = new GLTexturedVertex(); 
		v2.setXYZ(0.5f, -0.5f, 0); v2.setRGB(0, 0, 1); v2.setST(1, 1);
		GLTexturedVertex v3 = new GLTexturedVertex(); 
		v3.setXYZ(0.5f, 0.5f, 0); v3.setRGB(1, 1, 1); v3.setST(1, 0);
		
		GLTexturedVertex[] vertices = new GLTexturedVertex[] {v0, v1, v2, v3};
		
		try {
			GLCanvas canvas = new GLCanvas();
			
			GLShader shader = new GLShader("resource/shader/texture.vert", "resource/shader/texture.frag");
			GLTexture texture = new GLTexture("resource/png/ash_uvgrid04.png");
			GLObject obj = new GLObject(shader, texture, vertices, indices);
			
			frame.getContentPane().add(canvas);
			
			canvas.add(shader);
			canvas.add(texture);
			canvas.add(obj);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		frame.setVisible(true);
    }
    
    private void createWindow() throws Exception {
    	frame = new JFrame("Test");
    	frame.setSize(600, 600);
    	frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    	frame.setLocationByPlatform(true);
    }
}