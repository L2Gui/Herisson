package main;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

import opengl.GLCanvas;
import opengl.GLObject;
import opengl.GLShader;
import opengl.GLTexture;
import opengl.GLTexturedVertex;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GLContext;

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
		v0.setXYZ(-0.5f, 0.5f, -1.0f); v0.setRGB(1, 0, 0); v0.setST(0, 0);
		GLTexturedVertex v1 = new GLTexturedVertex(); 
		v1.setXYZ(-0.5f, -0.5f, -1.0f); v1.setRGB(0, 1, 0); v1.setST(0, 1);
		GLTexturedVertex v2 = new GLTexturedVertex(); 
		v2.setXYZ(0.5f, -0.5f, -1.0f); v2.setRGB(0, 0, 1); v2.setST(1, 1);
		GLTexturedVertex v3 = new GLTexturedVertex(); 
		v3.setXYZ(0.5f, 0.5f, -1.0f); v3.setRGB(1, 1, 1); v3.setST(1, 0);
		
		GLTexturedVertex[] vertices = new GLTexturedVertex[] {v0, v1, v2, v3};
		
		try {
			GLCanvas canvas = new GLCanvas();
			GLShader shader = new GLShader("texture.vert", "texture.frag");
			
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
    	frame = new JFrame("PT_Graphe_Hérisson");
    	frame.setSize(600, 600);
    	frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    	frame.setLocationByPlatform(true);
    	JMenuBar menu = new JMenuBar();
    		JMenu file = new JMenu("Fichier");
    			JMenuItem newFile = new JMenuItem("Nouveau");
    			JMenuItem open = new JMenuItem("Ouvrir");
    			JMenuItem save = new JMenuItem("Enregistrer");
    			JMenuItem saveAs = new JMenuItem("Enregistrer sous");
    			
    			file.add(newFile);
    			file.add(open);
    			file.add(save);
    			file.add(saveAs);
    		menu.add(file);
    		
    		JMenu edit = new JMenu("Edition");
    			JMenuItem copy = new JMenuItem("Copier");
    			JMenuItem cut = new JMenuItem("Couper");
    			JMenuItem paste = new JMenuItem("Coller");
    			
    			JMenu newElem = new JMenu("Nouveau");
    				JMenuItem newVertex = new JMenuItem("Noeud");
    				JMenuItem newEdge = new JMenuItem("Trait");
    			newElem.add(newVertex);
    			newElem.add(newEdge);
    			
    			JMenuItem delete = new JMenuItem("Supprimer");
    			
    			JMenu editElem = new JMenu("Modifier");
					JMenuItem editVertex = new JMenuItem("Noeud");
					JMenuItem editEdge = new JMenuItem("Trait");
				editElem.add(newVertex);
				editElem.add(newEdge);
				
				JMenuItem undo = new JMenuItem("Annuler");
				JMenuItem redo = new JMenuItem("Restaurer");
			
				edit.add(copy);
				edit.add(cut);
				edit.add(paste);
				edit.add(newElem);
				edit.add(delete);
				edit.add(editElem);
				edit.add(undo);
				edit.add(redo);
			
			menu.add(edit);
			
			JMenu display = new JMenu("Affichage");
			
			menu.add(display);
    		
    	frame.setJMenuBar(menu);
    }
}