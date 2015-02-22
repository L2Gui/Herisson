package main;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

import opengl.GLCanvas;

public class Main {
	public JFrame frame;
	
    public static void main(String args[]) {
        Main l10 = new Main();
        try {
        	l10.run();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public void run() throws Exception {
        createWindow();
		
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
				editElem.add(editVertex);
				editElem.add(editEdge);
				
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
    	
    	GLCanvas canvas = new GLCanvas();
		
		frame.getContentPane().add(canvas);
    }
}